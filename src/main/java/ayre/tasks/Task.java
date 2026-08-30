package ayre.tasks;

/**
 * Parent class for all tasks.
 * Holds the common methods and information that all tasks share.
 * Tasks are not complete at creation by default.
 */
public abstract class Task {
    private final String NAME;
    private boolean isComplete;

    public Task(String name) {
        this.NAME = name;
        this.isComplete = false;
    }

    public boolean matchesName(String key) {
        return this.NAME.contains(key);
    }

    public boolean getComplete() {
        return this.isComplete;
    }

    /**
     * Marks the Task as complete.
     *
     * @return Context String
     */
    public String markComplete() {
        this.isComplete = true;
        return "~ Mission complete. Good work, Raven.\n" + this ;
    }

    /**
     * Unmarks the Task as complete.
     *
     * @return Context String
     */
    public String unmarkComplete() {
        this.isComplete = false;
        return "~ The mission is still pending, Raven. Let's get to it.\n" + this;
    }

    /**
     * Converts the Task into a String that is easily parsable to store into the save file.
     *
     * @return String containing all necessary information about the Task to reconstruct it later
     */
    public String toLogString() {
        return (this.isComplete ? "1" : "0") + " " + this.NAME;
    }

    @Override
    public String toString() {
        return "[" + (this.isComplete ? "+" : " ") + "] " + this.NAME;
    }
}
