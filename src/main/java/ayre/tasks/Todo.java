package ayre.tasks;

public class Todo extends Task {
    public Todo(String name) {
        super(name);
    }

    @Override
    public String toLogString() {
        return "T " + super.toLogString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
