package manager;

import tasks.Task;

import java.util.ArrayList;
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

    public InMemoryHistoryManager(List<Task> historyList) {
        this.historyList = historyList;
    }

    /**
     * сохранение задачи в список истории просмотров
     * @param task задача, которую необходимо сохранить
     */
    @Override
    public void add(Task task) {
        if (historyList.size() == MAX_HISTORY_SIZE) {
            historyList.remove(0);
        }
        historyList.add(task);
    }

    /**
     * Метод, возвращающий список просмотров
     * @return список просмотров
     */
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyList);
    }

}
