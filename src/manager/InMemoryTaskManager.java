package manager;

import tasks.BasicTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.Map;

import service.EpicService;

import java.util.*;

/**
 * Класс Manager отвечает за управление и хранение каждого типа задач
 */
public class InMemoryTaskManager implements TaskManager{

    private long taskId;
    private final Map<Long, BasicTask> basicTaskList;
    private final Map<Long, Subtask> subtaskList;
    private final Map<Long, Epic> epicList;
    private final HistoryManager historyManager;

    /**
     * Конструктор класса Manager. Принимает в качестве параметра три мапы, хранящие различные типы задач.
     * Инициализирует уникальный идентификатор, начинающийся с нуля.
     *
     * @param basicTaskList    мапа, хранящая в качестве ключа идентификатор, а в качестве значения задачу
     * @param subtaskList мапа, хранящая в качестве ключа идентификатор, а в качестве значения подзадачу
     * @param epicList    мапа, хранящая в качестве ключа идентификатор, а в качестве значения эпик
     * @param historyManager    объект, реализующий интерфейс HistoryManager, для хранения истории просмотров
     */
    public InMemoryTaskManager(
            Map<Long, BasicTask> basicTaskList,
            Map<Long, Subtask> subtaskList,
            Map<Long, Epic> epicList,
            HistoryManager historyManager
    ) {
        this.basicTaskList = basicTaskList;
        this.subtaskList = subtaskList;
        this.epicList = epicList;
        this.historyManager = historyManager;
        taskId = 0;
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
        basicTaskList.clear();
    }


    /**
     * Очищает всю мапу, хранящую эпики
     */
    @Override
    public void removeAllEpics() {
        epicList.clear();
        subtaskList.clear();
    }

    /**
     * Очищает всю мапу, хранящую подзадачи
     */
    @Override
    public void removeAllSubtasks() {
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
        BasicTask basicTask = basicTaskList.get(basicTaskId);
        historyManager.add(basicTask);
        return basicTask;
    }

    /**
     * @param epicId в качестве параметра используется идентификатор эпика
     * @return возвращает эпик, полученный по epicId из epicList
     */
    @Override
    public Epic getEpicById(long epicId) {
        Epic epic = epicList.get(epicId);
        historyManager.add(epic);
        return epic;
    }


    /**
     * @param subtaskId в качестве параметра используется идентификатор подзадачи
     * @return возвращает подзадачу, полученную по subtaskId из subtaskList
     */
    @Override
    public Subtask getSubtaskById(long subtaskId) {
        Subtask subtask = subtaskList.get(subtaskId);
        historyManager.add(subtask);
        return subtask;
    }


    /**
     * Добавление задачи в мапу taskList. В начале генерируется уникальный идентификатор, затем происходит
     * добавление задачи с данным идентификатором в taskList.
     * @param basicTask задача, которую необходимо добавить в мапу для хранения
     */
    @Override
    public void add(BasicTask basicTask) {
        long id = generateId();
        basicTask.setTaskId(id);
        basicTaskList.put(id, basicTask);

    }

    /**
     * Добавление эпика в мапу epicList. В начале генерируется уникальный идентификатор, затем происходит
     * добавление эпика с данным идентификатором в epicList. Изначально эпик создается с пустым списком подзадач и
     * статусом NEW.
     * @param epic эпик, который необходимо добавить в мапу для хранения
     */
    @Override
    public void add(Epic epic) {
        long id = generateId();
        epic.setTaskId(id);
        epicList.put(id, epic);
    }

    /**
     * Добавление подзадачи в мапу subtaskList. Подзадача создается после создания эпика. В начале генерируется
     * уникальный идентификатор, затем происходит добавление подзадачи с данным идентификатором в subtaskList. Так как
     * подзадача напрямую связана со своим эпиком, необходимо добавить ее идентификатор в список подзадач связанного
     * эпика. После чего требуется произвести обновление статуса эпика в соответствии со статусом новой подзадачи.
     * @param subtask подзадача, которую необходимо добавить в мапу для хранения
     */
    @Override
    public void add(Subtask subtask) {
        long id = generateId();
        subtask.setTaskId(id);
        subtaskList.put(id, subtask);
        long epicId = subtask.getEpicId();
        Epic epic = epicList.get(epicId);
        EpicService.addEpicSubtask(epic, id);
        EpicService.checkEpicStatus(epic, subtaskList);
    }

    /**
     * Обновление задачи
     * @param basicTask новая версия объекта с верным идентификатором передается в виде параметра
     */
    @Override
    public void update(BasicTask basicTask) {
        long basicTaskId = basicTask.getTaskId();
        Task currentTask = basicTaskList.get(basicTaskId);
        if (isNullTask(currentTask)) {
            return;
        }
        basicTaskList.put(basicTaskId, basicTask);
    }

    /**
     * Обновление задачи
     * @param epic новая версия объекта с верным идентификатором передается в виде параметра
     */
    @Override
    public void update(Epic epic) {
        long epicId = epic.getTaskId();
        Epic currentEpic = epicList.get(epicId);
        if (isNullTask(currentEpic)) {
            return;
        }
        epicList.put(epicId, epic);
    }

    /**
     * Обновление задачи
     * @param subtask новая версия объекта с верным идентификатором передается в виде параметра
     */
    @Override
    public void update(Subtask subtask) {
        long subtaskId = subtask.getTaskId();
        Subtask currentSubtask = subtaskList.get(subtaskId);
        if (isNullTask(currentSubtask)) {
            return;
        }
        subtaskList.put(subtaskId, subtask);
        long epicId = subtask.getEpicId();
        Epic epic = epicList.get(epicId);
        EpicService.checkEpicStatus(epic, subtaskList);
    }

    /**
     * Удаление по идентификатору
     * @param basicTaskId идентификатор задачи, которую необходимо удалить
     */
    @Override
    public void removeBasicTaskById(long basicTaskId) {
        Task task = basicTaskList.get(basicTaskId);
        if (isNullTask(task)) {
            return;
        }
        basicTaskList.remove(basicTaskId);
    }

    /**
     * Удаление по идентификатору. После удаления эпика, список его подзадач также удаляется
     * @param epicId идентификатор эпика, который необходимо удалить
     */
    @Override
    public void removeEpicById(long epicId) {
        Epic epic = epicList.get(epicId);
        if (isNullTask(epic)) {
            return;
        }
        // очистка списка подзадач удаляемого эпика
        EpicService.removeAllEpicSubtasks(epic);
        basicTaskList.remove(epicId);
    }

    /**
     * Удаление по идентификатору. После удаления подзадачи необходимо также удалить ее из списка подзадач эпика. После
     * чего обновить статус эпика.
     * @param subtaskId идентификатор подзадачи, которую необходимо удалить
     */
    @Override
    public void removeSubtaskById(long subtaskId) {
        Subtask subtask = subtaskList.get(subtaskId);
        if (isNullTask(subtask)) {
            return;
        }
        subtaskList.remove(subtaskId);
        long epicId = subtask.getEpicId();
        Epic epic = epicList.get(epicId);
        EpicService.removeEpicSubtask(epic, subtaskId);
        EpicService.checkEpicStatus(epic, subtaskList);
    }

    /**
     * Получение списка просмотров через historyManager
     * @return список просмотренных задач
     */
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private long generateId() {
        return taskId++;
    }

    private boolean isNullTask(Task task) {
        return Objects.isNull(task);
    }


}
