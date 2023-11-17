package manager.new_manager;

import tasks.Task;

import java.util.List;

public interface GenericTaskManager<T extends Task> {
    List<T> getAllTasks();
    T getTaskById(long taskId);


    long addTask(T task);
    void updateTask(T task);

    void removeAllTasks();
    void removeTaskById(long id);
}
