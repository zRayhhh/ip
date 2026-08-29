package ayre;

import ayre.tasks.Task;

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

    public String findTasks(String name) {
        List<String> list = this.lst.stream()
                                    .filter(task -> task.matchesName(name))
                                    .map(Task::toString)
                                    .toList();
        StringBuilder tasks = new StringBuilder();
        for (int i = 1; i <= list.size(); i++) {
            tasks.append(i).append(". ").append(list.get(i - 1)).append("\n");
        }
        return list.isEmpty()
                ? "~ Sorry, Raven. There were no hits for that search."
                : "~ These are the matching missions:\n"
                    + tasks + "Did you find what you were looking for, Raven?";
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
        if (this.lst.isEmpty()) {
            return "~ The mission log is empty, Raven.";
        }
        StringBuilder tasks = new StringBuilder();
        for (int i = 1; i <= this.lst.size(); i++) {
            tasks.append(i).append(". ").append(this.lst.get(i - 1)).append("\n");
        }
        int numUnmarked = this.getUnmarkedTasks();
        String unmarkedInfo = (numUnmarked == 0)
                ? "~ There are no unmarked missions. Well done, Raven."
                : "~ You have " + numUnmarked + " pending mission"
                        + (numUnmarked == 1 ? "" : "s") + ". Let's do this.";
        return "~ Current missions:\n" + tasks + unmarkedInfo;
    }
}
