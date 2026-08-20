import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> lst;

    public TaskList() {
        lst = new ArrayList<>();
    }

    public void addTask(Task tsk) {
        lst.add(tsk);
    }

    public void markTask(int i) {
        lst.get(i).markComplete();
    }

    public void unmarkTask(int i) {
        lst.get(i).unmarkComplete();
    }

    public int pendingTasks() {
        int count = 0;
        for (Task t : lst) {
            if (t.getComplete()) count++;
        }
        return count;
    }

    public int numTasks() {
        return lst.size();
    }

    @Override
    public String toString() {
        StringBuilder tasks = new StringBuilder();
        for (int i = 1; i <= lst.size(); i++) {
            tasks.append(i).append(". ").append(lst.get(i - 1)).append("\n");
        }
        return "~ Current missions:\n" + tasks + "> ";
    }
}
