package tasks;

public class BasicTask extends Task{

    protected BasicTask(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    public static BasicTask create(String taskName, String description, Status status) {
        return new BasicTask(taskName, description, status);
    }
}
