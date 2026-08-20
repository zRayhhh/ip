public class Deadline extends Task {
    private String dueBy;

    public Deadline(String name, String dueBy) {
        super(name);
        this.dueBy = dueBy;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " <by: " + dueBy + ">";
    }
}
