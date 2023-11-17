package manager.new_manager;

import exceptions.InvalidTimeException;
import manager.HistoryManager;
import manager.Managers;
import tasks.BasicTask;
import tasks.Task;

import java.util.*;

import static manager.new_manager.TaskTimeValidator.isValidStartTime;

public class BasicTaskManager implements GenericTaskManager<BasicTask>, HistoryHolder, PrioritizedTasks {

    private long taskId;
    protected final Map<Long, BasicTask> basicTasks;
    protected final HistoryManager historyManager;
    protected final TreeSet<Task> sortedTasks;

    public BasicTaskManager() {
        taskId = 0;
        this.basicTasks = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
        sortedTasks = new TreeSet<>();
    }

    /**
     * @return возвращает список задач из мапы taskList
     */
    @Override
    public List<BasicTask> getAllTasks() {
        return new ArrayList<>(basicTasks.values());
    }

    /**
     * @param basicTaskId в качестве параметра используется идентификатор задачи
     * @return возвращает задачу, полученную по taskId из taskList
     */
    @Override
    public BasicTask getTaskById(long basicTaskId) {
        if (basicTasks.containsKey(basicTaskId)) {
            BasicTask basicTask = basicTasks.get(basicTaskId);
            historyManager.add(basicTask);
            return basicTask;
        } else {
            throw new NoSuchElementException("Задачи с id " + basicTaskId + " не существует.");
        }
    }

    /**
     * Добавление задачи в мапу taskList. В начале генерируется уникальный идентификатор, затем происходит
     * добавление задачи с данным идентификатором в taskList.
     *
     * @param basicTask задача, которую необходимо добавить в мапу для хранения
     * @return id добавленной задачи
     */
    @Override
    public long addTask(BasicTask basicTask) {
        Objects.requireNonNull(basicTask, "Попытка добавить пустую задачу.");
        if (isValidStartTime(basicTask, sortedTasks)) {
            long id = generateId();
            basicTask.setTaskId(id);
            basicTasks.put(id, basicTask);
            sortedTasks.add(basicTask);
            return id;
        } else {
            throw new InvalidTimeException("Задачи не должны пересекаться по времени!");
        }
    }

    /**
     * Обновление задачи
     *
     * @param basicTask новая версия объекта с верным идентификатором передается в виде параметра
     * @throws NoSuchElementException если задачи с таким id нет в basicTaskList
     */
    @Override
    public void updateTask(BasicTask basicTask) {
        Objects.requireNonNull(basicTask, "Попытка обновить пустую задачу.");
        long basicTaskId = basicTask.getTaskId();
        if (basicTasks.containsKey(basicTaskId)) {
            BasicTask currentTask = basicTasks.get(basicTaskId);
            basicTasks.remove(basicTaskId);
            sortedTasks.remove(currentTask);
            if (isValidStartTime(basicTask, sortedTasks)) {
                basicTasks.put(basicTaskId, basicTask);
                sortedTasks.add(basicTask);
            } else {
                basicTasks.put(basicTaskId, currentTask);
                sortedTasks.add(currentTask);
                throw new InvalidTimeException("Задачи не должны пересекаться по времени!");
            }
        } else {
            throw new NoSuchElementException("Задачи с id " + basicTaskId + " не существует.");
        }
    }

    /**
     * Очищает всю мапу, хранящую задачи
     */
    @Override
    public void removeAllTasks() {
        removeTasksFromHistory(basicTasks.keySet());
        for (BasicTask task : basicTasks.values()) {
            sortedTasks.remove(task);
        }
        basicTasks.clear();
    }

    /**
     * Удаление по идентификатору
     *
     * @param basicTaskId идентификатор задачи, которую необходимо удалить
     * @throws NoSuchElementException если задачи с таким id нет в basicTaskList
     */
    @Override
    public void removeTaskById(long basicTaskId) {
        if (basicTasks.containsKey(basicTaskId)) {
            sortedTasks.remove(basicTasks.get(basicTaskId));
            basicTasks.remove(basicTaskId);
            historyManager.remove(basicTaskId);
        } else {
            throw new NoSuchElementException("Задачи с id " + basicTaskId + " не существует.");
        }
    }

    private long generateId() {
        return taskId++;
    }

    private void removeTasksFromHistory(Collection<Long> keySet) {
        for (Long taskId : keySet) {
            historyManager.remove(taskId);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistory());
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(sortedTasks);
    }
}
