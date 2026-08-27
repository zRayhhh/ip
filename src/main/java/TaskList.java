import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> lst;

    public TaskList() {
        lst = new ArrayList<>();
    }

    public void addTask(Task tsk) {
        lst.add(tsk);
        Ayre.saveNewTask(tsk);
    }

    public void markTask(int i) {
        lst.get(i).markComplete();
        Ayre.updateExistingTask();
    }

    public void unmarkTask(int i) {
        lst.get(i).unmarkComplete();
        Ayre.updateExistingTask();
    }

    public void delTask(int i) {
        lst.remove(i);
        Ayre.updateExistingTask();
        System.out.print("~ The mission has been dropped. Let's... try it again next time.\n> ");
    }

    public int pendingTasks() {
        int count = 0;
        for (Task t : lst) {
            if (t.getComplete()) count++;
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
        StringBuilder tasks = new StringBuilder();
        for (int i = 1; i <= lst.size(); i++) {
            tasks.append(i).append(". ").append(lst.get(i - 1)).append("\n");
        }
        return "~ Current missions:\n" + tasks + "> ";
    }
}
