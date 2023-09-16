package manager;

import tasks.Task;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс, ответственный за хранение списка просмотренных задач
 */
public class InMemoryHistoryManager implements HistoryManager{
    /**
     * Максимальный размер списка просмотров
     */
    private final int MAX_HISTORY_SIZE = 10;

    private final List<Task> historyList;

    public InMemoryHistoryManager() {
        historyList = new LinkedList<>();
    }

    /**
     * Сохранение задачи в список истории просмотров
     * @param task задача, которую необходимо сохранить
     */
    @Override
    public void add(Task task) {
        if (historyList.size() == MAX_HISTORY_SIZE) {
            historyList.remove(0);
        }
        historyList.add(task);
    }

    @Override
    public void remove(long id) {
        for(Task task : historyList) {
            if(task.getTaskId() == id) {
                historyList.remove(task);
            }
        }
    }

    /**
     * Метод, возвращающий список просмотров
     * @return список просмотров
     */
    @Override
    public List<Task> getHistory() {
        return new LinkedList<>(historyList);
    }

}
