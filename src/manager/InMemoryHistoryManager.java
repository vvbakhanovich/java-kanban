package manager;

import service.CustomLinkedList;
import tasks.Task;

import java.util.*;

/**
 * Класс, ответственный за хранение списка просмотренных задач
 */
public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList historyList;

    public InMemoryHistoryManager() {
        historyList = new CustomLinkedList();
    }

    /**
     * Сохранение задачи в список истории просмотров
     *
     * @param task задача, которую необходимо сохранить
     */
    @Override
    public void add(Task task) {
        Objects.requireNonNull(task, "Невозможно добавить пустую задачу!");
        historyList.linkLast(task);
    }

    /**
     * Удаление задачи из истории просмотров
     *
     * @param id идентификатор задачи, которую требуется удалить
     * @return удалена задача или нет
     */
    @Override
    public boolean remove(long id) {
        return historyList.remove(id);
    }

    /**
     * Возвращает список просмотров
     *
     * @return список просмотров
     */
    @Override
    public List<Task> getHistory() {
        return historyList.getTasks();
    }
}
