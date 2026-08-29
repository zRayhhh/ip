package ayre;

import ayre.exceptions.TaskLogCorruptedException;
import ayre.tasks.*;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.stream.Stream;

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

    public TaskList load() {
        TaskList lst = new TaskList();
        if (!Files.exists(LOG_PATH)) {
            System.out.println("~ First contact with Coral Collective established");
            this.createTaskLog();
        }
        try (Stream<String> lines = Files.lines(LOG_PATH)) {
            lines.forEach(line -> {
                String[] args = line.split(" ");
                Task tsk;
                try {
                    tsk = switch (args[0]) {
                        case "T" -> new Todo(args[2]);
                        case "D" -> new Deadline(args[2], args[3]);
                        case "E" -> new Event(args[2], args[3], args[4]);
                        default ->
                                throw new TaskLogCorruptedException("Unexpected value encountered in file read");
                    };
                    if (args[1].equals("1")) {
                        tsk.markComplete();
                    }
                    lst.addTask(tsk);
                } catch (TaskLogCorruptedException e) {
                    // smth wrong with the data file, try to salvage or skip to next line (create error dump maybe)
                }
            });
        } catch (IOException e) {
            // fatal error reading file, either perms changed or disk failure
        }
        return lst;
    }

    public void update(TaskList lst) {
        try {
            Path tmpFile = Files.createTempFile(PARENT_DIR, "~$ayre", ".tmp");
            Files.write(tmpFile, lst.toLog());
            try {
                Files.move(tmpFile, LOG_PATH, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Files.deleteIfExists(tmpFile);
                throw e;
            }
        } catch (IOException e) {
            // handle it here (failed to save to hard disk), allows remaining list operation to return result
        }
    }
}
