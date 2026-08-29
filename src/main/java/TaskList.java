import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> lst;

    public TaskList() {
        lst = new ArrayList<>();
    }

    public String addTask(Task tsk) {
        lst.add(tsk);
        return "~ New mission added:\n" + tsk.toString() + "\n> ";
    }

    public String markTask(int i) {
        return lst.get(i).markComplete();
    }

    public String unmarkTask(int i) {
        return lst.get(i).unmarkComplete();
    }

    public String delTask(int i) {
        String tsk = lst.get(i).toString();
        lst.remove(i);
        return "~ The mission has been dropped.\n~~Deleted: " + tsk + "\n> ";
    }

    public int getNumTasks() {
        return lst.size();
    }

    public List<String> toLog() {
        return this.lst.stream()
                .map(Task::toLogString)
                .toList();
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
