import java.util.function.Consumer;

public class LiveTaskList {     // made with Claude Sonnet 5 as baseline
    private final TaskList tasks;
    private final Storage store;

    private LiveTaskList(TaskList tasks, Storage store) {
        this.tasks = tasks;
        this.store = store;
    }

    public static LiveTaskList load(Storage store) {
        return new LiveTaskList(store.load(), store);
    }

    private void mutateList(Consumer<TaskList> mut) {
        mut.accept(this.tasks);
        this.store.update(this.tasks);
    }

    public void add(Task tsk) {
        this.mutateList(lst -> lst.addTask(tsk));
    }

    public void del(int i) {
        this.mutateList(lst -> lst.delTask(i));
    }

    public void mark(int i) {
        this.mutateList(lst -> lst.markTask(i));
    }

    public void unmark(int i) {
        this.mutateList(lst -> lst.unmarkTask(i));
    }

    @Override
    public String toString() {
        return this.tasks.toString();
    }
}
