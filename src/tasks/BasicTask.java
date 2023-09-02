package tasks;

public class BasicTask extends Task{


    private BasicTask(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    private BasicTask(BasicTask basicTask, long id) {
        super(basicTask, id);
    }

    /**
     * Статическая фабрика, создает новую задачу
     * @param taskName имя задачи
     * @param description описание задачи
     * @param status статус задачи
     * @return новый объект класса BasicTask с переданными параметрами
     */
    public static BasicTask create(String taskName, String description, Status status) {
        return new BasicTask(taskName, description, status);
    }

    /**
     *
     * @param basicTask
     * @param basicTaskId
     * @return
     */
    public static BasicTask createFromWithId(BasicTask basicTask, long basicTaskId) {
        return new BasicTask(basicTask, basicTaskId);
    }
}
