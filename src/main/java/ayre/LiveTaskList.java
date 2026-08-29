package ayre;

import ayre.tasks.Task;

import java.util.List;

// Constructed partially with Claude Sonnet 5 medium
public class LiveTaskList {
    @FunctionalInterface
    interface TaskTransformer {
        String transform(TaskList tasks);
    }

    private final TaskList tasks;
    private final Storage store;

    private LiveTaskList(TaskList tasks, Storage store) {
        this.tasks = tasks;
        this.store = store;
    }

    public static LiveTaskList load(Storage store) {
        return new LiveTaskList(store.load(), store);
    }

    private String mutateList(TaskTransformer mut) {
        String msg = mut.transform(this.tasks);
        this.store.update(this.tasks);
        return msg;
    }

    public String add(Task tsk) {
        return this.mutateList(lst -> lst.addTask(tsk));
    }

    public String del(int i) {
        return this.mutateList(lst -> lst.delTask(i));
    }

    public String mark(int i) {
        return this.mutateList(lst -> lst.markTask(i));
    }

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
