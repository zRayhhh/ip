package ayre;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import ayre.commands.Command;
import ayre.commands.DeadlineCommand;
import ayre.commands.EventCommand;
import ayre.commands.TodoCommand;
import ayre.enums.CommandType;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.exceptions.TaskLogCorruptedException;
import ayre.tasks.Deadline;
import ayre.tasks.Event;
import ayre.tasks.Task;
import ayre.tasks.Todo;

/**
 * Handles file IO, including reading the save file at the start of the program and loading that
 * information into the TaskList, as well as updating the file.
 * Relies on LiveTaskList to bind the file update to a TaskList mutation.
 */
public class Storage {

    // Named constants representing their index position in a well-formatted save file log
    private static final int COMMAND_FLAG_INDEX = 0;
    private static final int IS_COMPLETE_FLAG_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int FIRST_DATE_INDEX = 3;
    private static final int SECOND_DATE_INDEX = 4;
    private static final int MIN_TOKENS = 3;

    private final Path logPath;
    private final Path parentDirectory;

    /**
     * Initializes Path fields in Storage.
     *
     * @param path Relative path to where the save file should be created/accessed.
     */
    public Storage(String path) {
        this.logPath = Path.of(path);
        this.parentDirectory = logPath.getParent();
    }

    /**
     * Creates a new save file if not present.
     *
     * @throws IOException If unable to create the parent folder or file for some reason.
     */
    private void createTaskLog() throws IOException {
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }
        Files.createFile(logPath);
    }

    /**
     * Loads the save file from disk into a TaskList.
     * If the file does not exist, creates it and returns early.
     * Reads and processes the file line by line.
     *
     * @return Record holding the resultant TaskList and the printout of any accumulated messages.
     */
    public LoadResult load() {
        TaskList tasks = new TaskList();
        List<String> warnings = new ArrayList<>();
        if (!Files.exists(logPath)) {
            warnings.add("~ First contact with Coral Collective established");
            try {
                this.createTaskLog();
            } catch (IOException e) {
                warnings.add(e.getMessage() + "\n<<Main System>> WARNING: MISSION LOG CREATION FAILED; "
                        + "YOUR MISSION LIST WILL NOT BE SAVED TO DISK");
            }
            return new LoadResult(tasks, this.getWarningsAsString(warnings));
        }
        try (Stream<String> lines = Files.lines(logPath)) {
            List<String> linesList = lines.toList(); // unable to use stream as we track the index for warning printouts
            for (int i = 0; i < linesList.size(); i++) {
                String[] logArgs = linesList.get(i).split(" ");
                try { // quietly handles errors to try to salvage anything that is intact
                    Task task = this.constructNewTask(i, logArgs); // goes through parsing and validation
                    if (logArgs[IS_COMPLETE_FLAG_INDEX].equals("1")) {
                        task.markComplete();
                    }
                    tasks.addTask(task);
                } catch (TaskLogCorruptedException e) {
                    warnings.add(e.getMessage()); // add a warning but continue parsing the rest of the file
                }
            }
        } catch (IOException e) { // fatal error reading file, either perms changed or disk failure
            warnings.add(e.getMessage() + "\n<<Main System>> WARNING: MISSION LOG FAILED TO LOAD; "
                    + "YOUR MISSION LIST WILL NOT BE SAVED TO DISK");
            return new LoadResult(tasks, this.getWarningsAsString(warnings));
        }
        if (!warnings.isEmpty()) {
            this.update(tasks); // force an update to clear corrupted data straight away
        }
        return new LoadResult(tasks, this.getWarningsAsString(warnings));
    }

    /**
     * Creates and returns a new Task based on input arguments.
     *
     * @param index Identifies the line of the save file in the exception in case log corruption is detected.
     * @param logArgs Tokenized arguments taken from parsing the line.
     * @return A new Task reconstructed via the logArgs.
     * @throws TaskLogCorruptedException If save file data does not match parsing expectations.
     */
    private Task constructNewTask(int index, String ...logArgs) throws TaskLogCorruptedException {
        return switch (logArgs[COMMAND_FLAG_INDEX]) {
            case "T" -> {
                this.validateLog(CommandType.TODO, new TodoCommand(null), logArgs, index);
                assert logArgs.length == 3 : "Number of log arguments should match log specifications (3)";
                yield new Todo(logArgs[NAME_INDEX]);
            }
            case "D" -> {
                this.validateLog(CommandType.DEADLINE, new DeadlineCommand(null), logArgs, index);
                assert logArgs.length == 4 : "Number of log arguments should match log specifications (4)";
                yield new Deadline(logArgs[NAME_INDEX], logArgs[FIRST_DATE_INDEX]);
            }
            case "E" -> {
                this.validateLog(CommandType.EVENT, new EventCommand(null), logArgs, index);
                assert logArgs.length == 5 : "Number of log arguments should match log specifications (5)";
                yield new Event(logArgs[NAME_INDEX], logArgs[FIRST_DATE_INDEX], logArgs[SECOND_DATE_INDEX]);
            }
            default -> {
                throw new TaskLogCorruptedException("Dropped log line at index " + index + ": Data corrupted");
            }
        };
    }

    /**
     * Checks the correctness of the arguments in a line of the save file.
     *
     * @param cmdType The CommandType to check the number of required arguments.
     * @param command A temporary Command used for argument validation.
     * @param logArgs A tokenized array holding the arguments present in the save file line.
     * @param index The index of the line in its file.
     * @throws TaskLogCorruptedException If validation checks for number of arguments or correctness fail.
     */
    private void validateLog(CommandType cmdType, Command command, String[] logArgs, int index)
            throws TaskLogCorruptedException {
        if (logArgs.length < MIN_TOKENS) { // expected save file log format has minimum 3 tokens for a todo
            throw new TaskLogCorruptedException("Dropped log line at index " + index + ": Missing data detected");
        }
        // turn the arguments from NAME_INDEX onwards into a list
        List<String> cmdArgs = Arrays.asList(Arrays.copyOfRange(logArgs, NAME_INDEX, logArgs.length));
        if (cmdArgs.size() != cmdType.getNumArgs()) {
            throw new TaskLogCorruptedException("Dropped log line at index " + index + ": Missing data detected");
        }
        try {
            command.validate(cmdArgs); // correctness check for the arguments
        } catch (InvalidCommandArgumentsException e) {
            throw new TaskLogCorruptedException("Dropped log line at index " + index + ": Data corrupted");
        }
    }

    /**
     * Converts the list of error messages into a readable, printable format.
     * Essentially a toString() for the list.
     *
     * @return A more visually readable printout of the list of warnings.
     */
    private String getWarningsAsString(List<String> warnings) {
        if (warnings.isEmpty()) {
            return "<<Main System: Mission Log Loaded Successfully>>";
        }
        StringBuilder warnString = new StringBuilder();
        for (String warning : warnings) {
            warnString.append(warning).append("\n");
        }
        return "<<Main System: Mission Log Corrupted>>\n" + warnString;
    }

    /**
     * Updates the save file whenever it is mutated.
     *
     * @param tasks The post-update TaskList to be written into the save file.
     * @return An empty String if successful, or an error message to be appended to the result String of the command.
     */
    public String update(TaskList tasks) {
        try {
            Path tmpFile = Files.createTempFile(parentDirectory, "~$ayre", ".tmp");
            Files.write(tmpFile, tasks.toLog());
            try {
                // atomic rewrite of file to prevent save file tampering while process is running
                Files.move(tmpFile, logPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Files.deleteIfExists(tmpFile); // prevent Temp files from piling up if atomic move fails
                throw e;
            }
            return ""; // no issues, nothing to append
        } catch (IOException e) {
            // additional message appended to result message of executing the command
            return "\n<<Main System>> WARNING: MISSION LOG UPDATE FAILED; YOUR CHANGE WILL NOT BE SAVED TO DISK";
        }
    }
}
