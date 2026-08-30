package ayre;

import ayre.tasks.Task;

/**
 * Intermediary class that handles mutation of TaskList and corresponding file IO.
 * This allows TaskList to stay separate from file read/write, maintaining the
 * abstraction wall.
 *
 * Constructed partially with Claude Sonnet 5 medium
 */
public class LiveTaskList {
    @FunctionalInterface
    interface TaskTransformer {
        String transform(TaskList tasks);
    }

    private final TaskList tasks;
    private final Storage store;

    public LiveTaskList(TaskList tasks, Storage store) {
        this.tasks = tasks;
        this.store = store;
    }

    private String mutateList(TaskTransformer mut) {
        return mut.transform(this.tasks)
                + this.store.update(this.tasks);
    }

    /**
     * Add a new Task to the TaskList while updating the save file.
     *
     * @param tsk Task to be added
     * @return Context String of adding a Task
     */
    public String add(Task tsk) {
        return this.mutateList(lst -> lst.addTask(tsk));
    }

    /**
     * Remove a Task to the TaskList while updating the save file.
     *
     * @param i Index of Task to be removed
     * @return Context String of removing a Task
     */
    public String del(int i) {
        return this.mutateList(lst -> lst.delTask(i));
    }

    /**
     * Mark a Task as complete while updating the save file.
     *
     * @param i Index of Task to be marked
     * @return Context String of marking a Task
     */
    public String mark(int i) {
        return this.mutateList(lst -> lst.markTask(i));
    }

    /**
     * Unmark a Task as complete while updating the save file.
     *
     * @param i Index of Task to be unmarked
     * @return Context String of unmarking a Task
     */
    public String unmark(int i) {
        return this.mutateList(lst -> lst.unmarkTask(i));
    }

    public int getNumTasks() {
        return this.tasks.getNumTasks();
    }

    public String findTasks(String name) {
        return this.tasks.findTasks(name);
    }

    @Override
    public String toString() {
        return this.tasks.toString();
    }
}
