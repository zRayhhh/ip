public class Task {
    private String name;
    private boolean isComplete;

    public Task(String name) {
        this.name = name;
        this.isComplete = false;
    }

    public void markComplete() {
        this.isComplete = true;
    }

    public void unmarkComplete() {
        this.isComplete = false;
    }

    @Override
    public String toString() {
        return "[" + (this.isComplete ? "+" : " ") + "] " + name;
    }
}
