package manager;

import tasks.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    boolean remove(long id);
    List<Task> getHistory();

}
