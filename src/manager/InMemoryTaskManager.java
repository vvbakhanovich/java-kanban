package manager;

import tasks.BasicTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.Map;

import utility.EpicService;

import java.util.*;

/**
 * Класс Manager отвечает за управление и хранение каждого типа задач
 */
public class InMemoryTaskManager implements TaskManager {

    protected long taskId;
    protected final Map<Long, BasicTask> basicTaskList;
    protected final Map<Long, Subtask> subtaskList;
    protected final Map<Long, Epic> epicList;
    protected final HistoryManager historyManager;

    /**
     * Конструктор класса Manager. Принимает в качестве параметра объект, реализующий интерфейс HistoryManager.
     *
     *
     */
    public InMemoryTaskManager() {
        basicTaskList = new HashMap<>();
        subtaskList = new HashMap<>();
        epicList = new HashMap<>();
        taskId = 1;
        historyManager = Managers.getDefaultHistory();
    }

    /**
     * @return возвращает список задач из мапы taskList
     */
    @Override
    public List<BasicTask> getBasicTaskList() {
        return new ArrayList<>(basicTaskList.values());
    }

    /**
     * @return возвращает список задач из мапы epicList
     */
    @Override
    public List<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }


    /**
     * @return возвращает список задач из мапы subtaskList
     */
    @Override
    public List<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }


    /**
     * @param epic в качестве параметра используется объект класса Epic
     * @return возвращает список идентификаторов подзадач для переданного эпика
     */
    @Override
    public List<Long> getEpicSubtaskList(Epic epic) {
        return new ArrayList<>(epic.getSubtaskList());
    }

    /**
     * Очищает всю мапу, хранящую задачи
     */
    @Override
    public void removeAllBasicTasks() {
        removeTasksFromHistory(basicTaskList.keySet());
        basicTaskList.clear();

    }


    /**
     * Очищает всю мапу, хранящую эпики
     */
    @Override
    public void removeAllEpics() {
        removeTasksFromHistory(epicList.keySet());
        epicList.clear();
        removeTasksFromHistory(subtaskList.keySet());
        subtaskList.clear();
    }

    /**
     * Очищает всю мапу, хранящую подзадачи
     */
    @Override
    public void removeAllSubtasks() {
        removeTasksFromHistory(subtaskList.keySet());

        subtaskList.clear();

        for (Epic epic : epicList.values()) {
            EpicService.removeAllEpicSubtasks(epic);
            EpicService.checkEpicStatus(epic, subtaskList);
        }
    }

    /**
     * @param basicTaskId в качестве параметра используется идентификатор задачи
     * @return возвращает задачу, полученную по taskId из taskList
     */
    @Override
    public BasicTask getBasicTaskById(long basicTaskId) {
        if (basicTaskList.containsKey(basicTaskId)) {
            BasicTask basicTask = basicTaskList.get(basicTaskId);
            historyManager.add(basicTask);
            return basicTask;
        } else {
            throw new NoSuchElementException("Задачи с id " + basicTaskId + " не существует.");
        }
    }

    /**
     * @param epicId в качестве параметра используется идентификатор эпика
     * @return возвращает эпик, полученный по epicId из epicList
     */
    @Override
    public Epic getEpicById(long epicId) {
        if (epicList.containsKey(epicId)) {
            Epic epic = epicList.get(epicId);
            historyManager.add(epic);
            return epic;
        } else {
            throw new NoSuchElementException("Эпика с id " + epicId + " не существует.");
        }
    }


    /**
     * @param subtaskId в качестве параметра используется идентификатор подзадачи
     * @return возвращает подзадачу, полученную по subtaskId из subtaskList
     */
    @Override
    public Subtask getSubtaskById(long subtaskId) {
        if (subtaskList.containsKey(subtaskId)) {
            Subtask subtask = subtaskList.get(subtaskId);
            historyManager.add(subtask);
            return subtask;
        } else {
            throw new NoSuchElementException("Подзадачи с id " + subtaskId + " не существует.");
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
    public void addBasicTask(BasicTask basicTask) {
        Objects.requireNonNull(basicTask, "Попытка добавить пустую задачу.");
        long id = generateId();
        basicTask.setTaskId(id);
        basicTaskList.put(id, basicTask);

    }

    /**
     * Добавление эпика в мапу epicList. В начале генерируется уникальный идентификатор, затем происходит
     * добавление эпика с данным идентификатором в epicList. Изначально эпик создается с пустым списком подзадач и
     * статусом NEW.
     *
     * @param epic эпик, который необходимо добавить в мапу для хранения
     * @return id добавленного эпика
     */
    @Override
    public void addEpic(Epic epic) {
        Objects.requireNonNull(epic, "Попытка добавить пустой эпик.");
        long id = generateId();
        epic.setTaskId(id);
        epicList.put(id, epic);

    }

    /**
     * Добавление подзадачи в мапу subtaskList. Подзадача создается после создания эпика. В начале генерируется
     * уникальный идентификатор, затем происходит добавление подзадачи с данным идентификатором в subtaskList. Так как
     * подзадача напрямую связана со своим эпиком, необходимо добавить ее идентификатор в список подзадач связанного
     * эпика. После чего требуется произвести обновление статуса эпика в соответствии со статусом новой подзадачи.
     *
     * @param subtask подзадача, которую необходимо добавить в мапу для хранения
     * @throws NoSuchElementException если не существует эпика с epicId
     * @return id добавленной подзадачи
     */
    @Override
    public void addSubtask(Subtask subtask) throws NoSuchElementException{
        Objects.requireNonNull(subtask, "Попытка добавить пустую подзадачу");
        if (epicList.containsKey(subtask.getEpicId())) {
            long id = generateId();
            subtask.setTaskId(id);
            subtaskList.put(id, subtask);
            long epicId = subtask.getEpicId();
            Epic epic = epicList.get(epicId);
            EpicService.addEpicSubtask(epic, id);
            EpicService.checkEpicStatus(epic, subtaskList);
        } else {
            throw new NoSuchElementException("Эпика с id " + subtask.getEpicId() + " не существует.");
        }
    }

    /**
     * Обновление задачи
     *
     * @param basicTask новая версия объекта с верным идентификатором передается в виде параметра
     * @throws NoSuchElementException если задачи с таким id нет в basicTaskList
     */
    @Override
    public void updateBasicTask(BasicTask basicTask) throws NoSuchElementException{
        Objects.requireNonNull(basicTask, "Попытка обновить пустую задачу.");
        long basicTaskId = basicTask.getTaskId();
        if (basicTaskList.containsKey(basicTaskId)) {
            basicTaskList.put(basicTaskId, basicTask);
        } else {
            throw new NoSuchElementException("Задачи с id " + basicTaskId + " не существует.");
        }
    }

    /**
     * Обновление задачи
     *
     * @param epic новая версия объекта с верным идентификатором передается в виде параметра
     * @throws NoSuchElementException если эпика с таким id нет в epicList
     */
    @Override
    public void updateEpic(Epic epic) throws NoSuchElementException{
        Objects.requireNonNull(epic, "Попытка обновить пустой эпик.");
        long epicId = epic.getTaskId();
        if (epicList.containsKey(epicId)) {
            epicList.put(epicId, epic);
        } else {
            throw new NoSuchElementException("Эпика с id " + epicId + " не существует.");
        }
    }

    /**
     * Обновление задачи
     *
     * @param subtask новая версия объекта с верным идентификатором передается в виде параметра
     * @throws NoSuchElementException если подзадачи с таким id нет в subtaskList
     */
    @Override
    public void updateSubtask(Subtask subtask) throws NoSuchElementException{
        Objects.requireNonNull(subtask, "Попытка обновить пустую задачу.");
        long subtaskId = subtask.getTaskId();
        if (subtaskList.containsKey(subtaskId)) {
            subtaskList.put(subtaskId, subtask);
            long epicId = subtask.getEpicId();
            Epic epic = epicList.get(epicId);
            EpicService.checkEpicStatus(epic, subtaskList);
        } else {
            throw new NoSuchElementException("Подзадачи с id " + subtaskId + " не существует.");
        }
    }

    /**
     * Удаление по идентификатору
     *
     * @param basicTaskId идентификатор задачи, которую необходимо удалить
     * @throws NoSuchElementException если задачи с таким id нет в basicTaskList
     */
    @Override
    public void removeBasicTaskById(long basicTaskId) throws NoSuchElementException{
        if (basicTaskList.containsKey(basicTaskId)) {
            basicTaskList.remove(basicTaskId);
            historyManager.remove(basicTaskId);
        } else {
            throw new NoSuchElementException("Задачи с id " + basicTaskId + " не существует.");
        }
    }

    /**
     * Удаление по идентификатору. После удаления эпика, список его подзадач также удаляется
     *
     * @param epicId идентификатор эпика, который необходимо удалить
     * @throws NoSuchElementException если эпика с таким id нет в epicList
     */
    @Override
    public void removeEpicById(long epicId) throws NoSuchElementException{
        if (epicList.containsKey(epicId)) {
            Epic epic = epicList.get(epicId);
            //удаление подзадач эпика из истории просмотров
            removeTasksFromHistory(epic.getSubtaskList());
            // очистка списка подзадач удаляемого эпика
            EpicService.removeAllEpicSubtasks(epic);
            basicTaskList.remove(epicId);
            historyManager.remove(epicId);
        } else {
            throw new NoSuchElementException("Эпика с id " + epicId + " не существует.");
        }
    }

    /**
     * Удаление по идентификатору. После удаления подзадачи необходимо также удалить ее из списка подзадач эпика. После
     * чего обновить статус эпика.
     *
     * @param subtaskId идентификатор подзадачи, которую необходимо удалить
     * @throws NoSuchElementException если подзадачи с таким id нет в subtaskList
     */
    @Override
    public void removeSubtaskById(long subtaskId) throws NoSuchElementException{
        if (subtaskList.containsKey(subtaskId)) {
            Subtask subtask = subtaskList.get(subtaskId);
            subtaskList.remove(subtaskId);
            long epicId = subtask.getEpicId();
            Epic epic = epicList.get(epicId);
            EpicService.removeEpicSubtask(epic, subtaskId);
            EpicService.checkEpicStatus(epic, subtaskList);
            historyManager.remove(subtaskId);
        } else {
            throw new NoSuchElementException("Подзадачи с id " + subtaskId + " не существует.");
        }
    }

    /**
     * Получение списка просмотров через historyManager
     *
     * @return список просмотренных задач
     */
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private long generateId() {
        return taskId++;
    }

    private void removeTasksFromHistory(Collection<Long> keySet) {
        for (Long taskId : keySet) {
            historyManager.remove(taskId);
        }
    }
}
