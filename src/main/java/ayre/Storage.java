package ayre;

import ayre.enums.CommandType;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.exceptions.TaskLogCorruptedException;

import ayre.tasks.*;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Handles file IO, including reading the save file at the start of the program and loading that
 * information into the TaskList, as well as updating the file.
 * Relies on LiveTaskList to bind the file update to a TaskList mutation.
 */
public class Storage {
    private final Path LOG_PATH;
    private final Path PARENT_DIR;

    public Storage(String path) {
        this.LOG_PATH = Path.of(path);
        this.PARENT_DIR = LOG_PATH.getParent();
    }

    private void createTaskLog() {
        try {
            if (PARENT_DIR != null) {
                Files.createDirectories(PARENT_DIR);
            }
            Files.createFile(LOG_PATH);
        } catch (IOException e) {
            System.out.println("Failed to create file or directory");
        }
    }

    public LoadResult load() {
        TaskList lst = new TaskList();
        List<String> warnings = new ArrayList<>();
        if (!Files.exists(LOG_PATH)) {
            System.out.println("~ First contact with Coral Collective established");
            this.createTaskLog();
        }
        try (Stream<String> lines = Files.lines(LOG_PATH)) {
            List<String> linesList = lines.toList();
            for (int i = 0; i < linesList.size(); i++) {
                String[] logArgs = linesList.get(i).split(" ");
                try {
                    Task tsk = switch (logArgs[0]) {
                        case "T" -> {
                            this.validateLog(CommandType.TODO, logArgs, i);
                            yield new Todo(logArgs[2]);
                        }
                        case "D" -> {
                            this.validateLog(CommandType.DEADLINE, logArgs, i);
                            yield new Deadline(logArgs[2], logArgs[3]);
                        }
                        case "E" -> {
                            this.validateLog(CommandType.EVENT, logArgs, i);
                            yield new Event(logArgs[2], logArgs[3], logArgs[4]);
                        }
                        default -> throw new TaskLogCorruptedException("Dropped log line " + i + ": Data corrupted");
                    };
                    if (logArgs[1].equals("1")) {
                        tsk.markComplete();
                    }
                    lst.addTask(tsk);
                } catch (TaskLogCorruptedException e) {
                    warnings.add(e.getMessage());
                }
            }
        } catch (IOException e) {
            // fatal error reading file, either perms changed or disk failure
            warnings.add(e.getMessage() + "\n<<Main System>> WARNING: MISSION LOG FAILED TO LOAD; " +
                    "YOUR MISSION LIST WILL NOT BE SAVED TO DISK");
            return new LoadResult(lst, warnings);
        }
        if (!warnings.isEmpty()) {
            this.update(lst);       // force an update to clear corrupted data straight away
        }
        return new LoadResult(lst, warnings);
    }

    private void validateLog(CommandType cmd, String[] logArgs, int index) throws TaskLogCorruptedException {
        if (logArgs.length < 3) {
            throw new TaskLogCorruptedException("Dropped log line " + index + ": Missing data detected");
        }
        List<String> cmdArgs = Arrays.asList(Arrays.copyOfRange(logArgs, 2, logArgs.length));
        if (cmdArgs.size() != cmd.getNumArgs()) {
            throw new TaskLogCorruptedException("Dropped log line " + index + ": Missing data detected");
        }
        try {
            cmd.validate(cmdArgs);
        } catch (InvalidCommandArgumentsException e) {
            System.out.print(cmdArgs.get(1));
            throw new TaskLogCorruptedException("Dropped log line " + index + ": Data corrupted");
        }
    }

    public String update(TaskList lst) {
        try {
            Path tmpFile = Files.createTempFile(PARENT_DIR, "~$ayre", ".tmp");
            Files.write(tmpFile, lst.toLog());
            try {
                Files.move(tmpFile, LOG_PATH, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Files.deleteIfExists(tmpFile);
                throw e;
            }
            return "";
        } catch (IOException e) {
            return "\n<<Main System>> WARNING: MISSION LOG UPDATE FAILED; YOUR CHANGE WILL NOT BE SAVED TO DISK";
        }
    }
}
