public class Task {
    private String name;
    private boolean isComplete;

    public Task(String name) {
        this.name = name;
        this.isComplete = false;
    }

    public boolean getComplete() {
        return this.isComplete;
    }

    public String markComplete() {
        this.isComplete = true;
        return "~ Mission complete. Good work, Raven.\n" + this ;
    }

    public String unmarkComplete() {
        this.isComplete = false;
        return "~ The mission is still pending, Raven. Let's get to it.\n" + this;
    }

    public String toLogString() {
        return (this.isComplete ? "1" : "0") + " " + this.name;
    }

    @Override
    public String toString() {
        return "[" + (this.isComplete ? "+" : " ") + "] " + this.name;
    }
}
