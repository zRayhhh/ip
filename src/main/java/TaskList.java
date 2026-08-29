import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> lst;

    public TaskList() {
        lst = new ArrayList<>();
    }

    public String addTask(Task tsk) {
        lst.add(tsk);
        return "~ New mission added:\n" + tsk.toString();
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
        return "~ The mission has been dropped.\nDeleted: " + tsk;
    }

    private int getUnmarkedTasks() {
        int count = 0;
        for (Task t : lst) {
            if (!t.getComplete()) count++;
        }
        return count;
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
        if (lst.isEmpty()) {
            return "~ The mission log is empty, Raven.";
        }
        StringBuilder tasks = new StringBuilder();
        for (int i = 1; i <= lst.size(); i++) {
            tasks.append(i).append(". ").append(lst.get(i - 1)).append("\n");
        }
        int numUnmarked = this.getUnmarkedTasks();
        String unmarkedInfo = (numUnmarked == 0)
                ? "~ There are no unmarked missions. Well done, Raven."
                : "~ You have " + numUnmarked + " pending mission"
                        + (numUnmarked == 1 ? "" : "s") + ". Let's do this.";
        return "~ Current missions:\n" + tasks + unmarkedInfo;
    }
}
