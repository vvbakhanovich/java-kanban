package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.Map;

import service.EpicService;

import java.util.*;

/**
 * Класс Manager отвчает за управление и хранение каждого типа задач
 */
public class Manager {

    private long taskId;
    private final Map<Long, Task> taskList;
    private final Map<Long, Subtask> subtaskList;
    private final Map<Long, Epic> epicList;

    /**
     * Конструктор класса Manager. Принимает в качетсве параметро три мапы, хранящие разлтичные типы задач.
     * Иницализирует уникальный идентификатор, начинающийся с нуля.
     *
     * @param taskList    мапа, хранящая в качестве ключа идентификатор, а в качестве значения задачу
     * @param subtaskList мапа, хранящая в качестве ключа идентификатор, а в качестве значения подзадачу
     * @param epicList    мапа, хранящая в качестве ключа идентификатор, а в качестве значения эпик
     */
    public Manager(
            Map<Long, Task> taskList,
            Map<Long, Subtask> subtaskList,
            Map<Long, Epic> epicList
    ) {
        this.taskList = taskList;
        this.subtaskList = subtaskList;
        this.epicList = epicList;
        taskId = 0;
    }

    /**
     * @return возвращает список задач из мапы taskList
     */
    public List<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    /**
     * @return возвращает список задач из мапы epicList
     */
    public List<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }


    /**
     * @return возвращает список задач из мапы subtaskList
     */
    public List<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }


    /**
     * @param epic в качестве параметра используется объект класса Epic
     * @return возвращает спиок идентификаторов подзадач для переданного эпика
     */
    public List<Long> getEpicSubtaskList(Epic epic) {
        return new ArrayList<>(epic.getSubtaskList());
    }

    /**
     * очищает всю мапу, хранящую задачи
     */
    public void removeAllTasks() {
        taskList.clear();
    }


    /**
     * очищает всю мапу, хранящую эпики
     */
    public void removeAllEpics() {
        epicList.clear();
        subtaskList.clear();
    }

    /**
     * очищает всю мапу, хранящую подазадачи
     */
    public void removeAllSubtasks() {
        subtaskList.clear();

        for (Epic epic : epicList.values()) {
            EpicService.removeAllEpicSubtasks(epic);
            EpicService.checkEpicStatus(epic, subtaskList);
        }
    }

    /**
     * @param taskId в качестве параметра используется идентификатор задачи
     * @return возвраащет задачу, полученную по taskId из taskList
     */
    public Task getTaskById(long taskId) {
        return taskList.getOrDefault(taskId, null);
    }

    /**
     * @param epicId в качестве параметра используется идентификатор эпика
     * @return возвраащет эпик, полученный по epicId из epicList
     */
    public Epic getEpicById(long epicId) {
        return epicList.getOrDefault(epicId, null);
    }


    /**
     * @param subtaskId в качестве параметра используется идентификатор подзадачи
     * @return возвраащет подзадачу, полученную по subtaskId из subtaskList
     */
    public Subtask getSubtaskById(long subtaskId) {
        return subtaskList.getOrDefault(subtaskId, null);
    }


    /**
     * Добавление задачи в мапу taskList. В начале генерируется уникальный идентификатор, затем происходит
     * добавление задачи с данным идентификатором в taskList.
     * @param task задача, которую необходимо добавить в мапу для хранения
     */
    public void addTask(Task task) {
        long id = generateId();
        task.setTaskId(id);
        taskList.put(id, task);

    }

    /**
     * Добавление эпика в мапу epicList. В начале генерируется уникальный идентификатор, затем происходит
     * добавление эпика с данным идентификатором в epicList. Изначально эпик создается с пустым списком подзадач и
     * статусом NEW.
     * @param epic эпик, который необходимо добавить в мапу для хранения
     */
    public void addEpic(Epic epic) {
        long id = generateId();
        epic.setTaskId(id);
        epicList.put(id, epic);
    }

    /**
     * Добавление подзадачи в мапу subtaskList. Подзадача создается после создания эпкаю В начале генерируется
     * уникальный идентификатор, затем происходит добавление подзадачи с данным идентификатором в subtaskList. Так как
     * подзадача напрямую связана со своим эпиком,необходимо добавить ее идентификатор в список подзадач связанного
     * эпика. После чего требуется произвести обновление статуса эпика в соотвествии со статусом новой подзадачи.
     * @param subtask подзадача, которую необходимо добавить в мапу для хранения
     */
    public void addSubtask(Subtask subtask) {
        long id = generateId();
        subtask.setTaskId(id);
        subtaskList.put(id, subtask);
        long epicId = subtask.getEpicId();
        Epic epic = getEpicById(epicId);
        EpicService.addEpicSubtask(epic, id);
        EpicService.checkEpicStatus(epic, subtaskList);
    }

    /**
     * Обновление задачи
     * @param task новая версия объекта с верным идентификатором передается в виде параметра
     */
    public void updateTask(Task task) {
        long taskId = task.getTaskId();
        Task currentTask = taskList.getOrDefault(taskId, null);
        if (isNullTask(currentTask)) {
            return;
        }
        taskList.put(taskId, task);
    }

    /**
     * Обновление задачи
     * @param epic новая версия объекта с верным идентификатором передается в виде параметра
     */
    public void updateEpic(Epic epic) {
        long epicId = epic.getTaskId();
        Epic currentEpic = epicList.getOrDefault(epicId, null);
        if (isNullTask(currentEpic)) {
            return;
        }
        epicList.put(epicId, epic);
    }

    /**
     * Обновление задачи
     * @param subtask новая версия объекта с верным идентификатором передается в виде параметра
     */
    public void updateSubtask(Subtask subtask) {
        long subtaskId = subtask.getTaskId();
        Subtask currentSubtask = subtaskList.getOrDefault(subtaskId, null);
        if (isNullTask(currentSubtask)) {
            return;
        }
        subtaskList.put(subtaskId, subtask);
        long epicId = subtask.getEpicId();
        Epic epic = getEpicById(epicId);
        EpicService.checkEpicStatus(epic, subtaskList);
    }

    /**
     * Удаление по идентификатору
     * @param taskId идентификатор задачи, которую необходимо удалить
     */
    public void removeTaskById(long taskId) {
        Task task = taskList.getOrDefault(taskId, null);
        if (isNullTask(task)) {
            return;
        }
        taskList.remove(taskId);
    }

    /**
     * Удаление по идентификатору. После удаления эпика, список его подзадач также удаляется
     * @param taskId идентификатор эпика, который необходимо удалить
     */
    public void removeEpicById(long taskId) {
        Epic epic = epicList.getOrDefault(taskId, null);
        if (isNullTask(epic)) {
            return;
        }
        // очистка списка подзадач удаляемого эпика
        EpicService.removeAllEpicSubtasks(epic);
        taskList.remove(taskId);
    }

    /**
     * Удаление по идентификатору. После удаления подзадачи необходимо также удалить ее из списка подзадач эпика. После
     * чего обновить статус эпика.
     * @param subtaskId идентификатор подзадачи, которую необходимо удалить
     */
    public void removeSubtaskById(long subtaskId) {
        Subtask subtask = subtaskList.getOrDefault(subtaskId, null);
        if (isNullTask(subtask)) {
            return;
        }
        subtaskList.remove(subtaskId);
        long epicId = subtask.getEpicId();
        Epic epic = getEpicById(epicId);
        EpicService.removeEpicSubtask(epic, subtaskId);
        EpicService.checkEpicStatus(epic, subtaskList);
    }

    private long generateId() {
        return taskId++;
    }

    private boolean isNullTask(Task task) {
        return Objects.isNull(task);
    }


}
