package tasks;

public class BasicTask extends Task{

    protected BasicTask(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    protected BasicTask(BasicTask basicTask, long id) {
        super(basicTask, id);
    }

    public static BasicTask create(String taskName, String description, Status status) {
        return new BasicTask(taskName, description, status);
    }

    public static BasicTask createFromWithId(BasicTask basicTask, long basicTaskId) {
        return new BasicTask(basicTask, basicTaskId);
    }
}
