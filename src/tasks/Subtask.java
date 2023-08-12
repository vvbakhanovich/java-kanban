package tasks;

public class Subtask extends Task{
    private Epic epic;

    public Subtask(String taskName, String description, Status status, Epic epic) {
        super(taskName, description, status);
        this.epic = epic;
    }
}
