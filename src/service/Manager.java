package service;

public class Manager {
    private final TaskManager taskManager;
    private final EpicManager epicManager;
    private final SubtaskManager subtaskManager;

    public Manager() {
        taskManager = new TaskManager();
        epicManager = new EpicManager();
        subtaskManager = new SubtaskManager();
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public EpicManager getEpicManager() {
        return epicManager;
    }

    public SubtaskManager getSubtaskManager() {
        return subtaskManager;
    }
}
