package ayre.commands;

import java.nio.file.Path;

import ayre.LiveTaskList;
import ayre.Storage;
import ayre.TaskList;
import ayre.tasks.Todo;

/** Provides shared setup methods for command tests. */
final class CommandTestSupport {
    private CommandTestSupport() {
    }

    /** Creates an empty task list backed by a temporary save file. */
    static LiveTaskList emptyTasks(Path directory) {
        return new LiveTaskList(new TaskList(), new Storage(directory.resolve("test.txt").toString()));
    }

    /** Creates a task list containing one Todo. */
    static LiveTaskList tasksWithTodo(Path directory, String name) {
        LiveTaskList tasks = emptyTasks(directory);
        tasks.add(new Todo(name));
        return tasks;
    }
}
