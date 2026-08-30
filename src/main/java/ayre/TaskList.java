package ayre;

import ayre.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper of an ArrayList of Tasks that acts as an intermediary in communication between
 * the other methods and the Tasks.
 */
public class TaskList {
    private ArrayList<Task> lst;

    public TaskList() {
        lst = new ArrayList<>();
    }

    /**
     * Add a Task to the ArrayList.
     *
     * @param tsk Task to be added
     * @return Context String for adding a Task
     */
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
                    + tasks + "~ Did you find what you were looking for, Raven?";
    }

    /**
     * Mark a Task in the ArrayList as complete.
     *
     * @param i Index of the Task
     * @return Context String of marking a Task
     */
    public String markTask(int i) {
        return lst.get(i).markComplete();
    }

    /**
     * Unmark a Task in the ArrayList as complete.
     *
     * @param i Index of the Task
     * @return Context String of unmarking a Task
     */
    public String unmarkTask(int i) {
        return lst.get(i).unmarkComplete();
    }

    /**
     * Remove a Task from the ArrayList.
     *
     * @param i Index of the Task
     * @return Context String of removing a Task
     */
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

    /**
     * Convert the Tasks in the ArrayList into save file parse-friendly Strings.
     *
     * @return A List of the toLogString() of each Task in order.
     */
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
