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

    public void markComplete() {
        this.isComplete = true;
        System.out.print("~ Mission complete. Good work, Raven.\n" + this + "\n> ");
    }

    public void unmarkComplete() {
        this.isComplete = false;
        System.out.print("~ The mission is still pending, Raven. Let's get to it.\n" + this + "\n> ");
    }

    public String toLogString() {
        return (this.isComplete ? "1" : "0") + " " + this.name;
    }

    @Override
    public String toString() {
        return "[" + (this.isComplete ? "+" : " ") + "] " + this.name;
    }
}
