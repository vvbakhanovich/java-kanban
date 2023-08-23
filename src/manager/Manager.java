package manager;

import tasks.*;

import java.util.Map;

import java.util.*;

/**
 * Класс Manager отвечает за управление и хранение каждого типа задач
 */
public class Manager {

    private long taskId;
    private final Map<Long, BasicTask> basicTaskList;
    private final Map<Long, Subtask> subtaskList;
    private final Map<Long, Epic> epicList;

    /**
     * Конструктор класса Manager. Принимает в качестве параметра три мапы, хранящие различные типы задач.
     * Инициализирует уникальный идентификатор, начинающийся с нуля.
     *
     * @param basicTaskList    мапа, хранящая в качестве ключа идентификатор, а в качестве значения задачу
     * @param subtaskList мапа, хранящая в качестве ключа идентификатор, а в качестве значения подзадачу
     * @param epicList    мапа, хранящая в качестве ключа идентификатор, а в качестве значения эпик
     */
    public Manager(
            Map<Long, BasicTask> basicTaskList,
            Map<Long, Subtask> subtaskList,
            Map<Long, Epic> epicList
    ) {
        this.basicTaskList = basicTaskList;
        this.subtaskList = subtaskList;
        this.epicList = epicList;
        taskId = 0;
    }

    /**
     * @return возвращает список задач из мапы taskList
     */
    public List<BasicTask> getBasicTaskList() {
        return new ArrayList<>(basicTaskList.values());
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
     * @return возвращает список идентификаторов подзадач для переданного эпика
     */
    public List<Long> getEpicSubtaskList(Epic epic) {
        return new ArrayList<>(epic.getSubtaskList());
    }

    /**
     * Очищает всю мапу, хранящую задачи
     */
    public void removeAllBasicTasks() {
        basicTaskList.clear();
    }


    /**
     * Очищает всю мапу, хранящую эпики
     */
    public void removeAllEpics() {
        epicList.clear();
        subtaskList.clear();
    }

    /**
     * Очищает всю мапу, хранящую подзадачи
     */
    public void removeAllSubtasks() {
        subtaskList.clear();

        for (Epic epic : epicList.values()) {
            removeAllEpicSubtasks(epic);
            checkEpicStatus(epic);
        }
    }

    /**
     * @param basicTaskId в качестве параметра используется идентификатор задачи
     * @return возвращает задачу, полученную по taskId из taskList
     */
    public BasicTask getBasicTaskById(long basicTaskId) {
        return basicTaskList.getOrDefault(basicTaskId, null);
    }

    /**
     * @param epicId в качестве параметра используется идентификатор эпика
     * @return возвращает эпик, полученный по epicId из epicList
     */
    public Epic getEpicById(long epicId) {
        return epicList.getOrDefault(epicId, null);
    }


    /**
     * @param subtaskId в качестве параметра используется идентификатор подзадачи
     * @return возвращает подзадачу, полученную по subtaskId из subtaskList
     */
    public Subtask getSubtaskById(long subtaskId) {
        return subtaskList.getOrDefault(subtaskId, null);
    }


    /**
     * Добавление задачи в мапу taskList. В начале генерируется уникальный идентификатор, затем происходит
     * добавление задачи с данным идентификатором в taskList.
     * @param basicTask задача, которую необходимо добавить в мапу для хранения
     */
    public void addBasicTask(BasicTask basicTask) {
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
    public void addEpic(Epic epic) {
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
    public void addSubtask(Subtask subtask) {
        long id = generateId();
        subtask.setTaskId(id);
        subtaskList.put(id, subtask);
        long epicId = subtask.getEpicId();
        Epic epic = getEpicById(epicId);
        addEpicSubtask(epic, id);
        checkEpicStatus(epic);
    }

    /**
     * Обновление задачи
     * @param basicTask новая версия объекта с верным идентификатором передается в виде параметра
     */
    public void updateBasicTask(BasicTask basicTask) {
        long basicTaskId = basicTask.getTaskId();
        Task currentTask = basicTaskList.getOrDefault(basicTaskId, null);
        if (isNullTask(currentTask)) {
            return;
        }
        basicTaskList.put(basicTaskId, basicTask);
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
        checkEpicStatus(epic);
    }

    /**
     * Удаление по идентификатору
     * @param basicTaskId идентификатор задачи, которую необходимо удалить
     */
    public void removeBasicTaskById(long basicTaskId) {
        Task task = basicTaskList.getOrDefault(basicTaskId, null);
        if (isNullTask(task)) {
            return;
        }
        basicTaskList.remove(basicTaskId);
    }

    /**
     * Удаление по идентификатору. После удаления эпика, список его подзадач также удаляется
     * @param epicId идентификатор эпика, который необходимо удалить
     */
    public void removeEpicById(long epicId) {
        Epic epic = epicList.getOrDefault(epicId, null);
        if (isNullTask(epic)) {
            return;
        }
        // очистка списка подзадач удаляемого эпика
        removeAllEpicSubtasks(epic);
        basicTaskList.remove(epicId);
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
        removeEpicSubtask(epic, subtaskId);
        checkEpicStatus(epic);
    }

    private long generateId() {
        return taskId++;
    }

    private boolean isNullTask(Task task) {
        return Objects.isNull(task);
    }

    /**
     * При добавлении подзадачи указывается эпик, к которому она относится. Данный метод добавляет идентификатор
     * подзадачи в список идентификаторов для конкретного эпика.
     * @param epic эпик, для которого требуется добавить подзадачу
     * @param subtaskId идентификатор добавляемой подзадачи
     */
    private void addEpicSubtask(Epic epic, Long subtaskId) {
        List<Long> subtaskList = epic.getSubtaskList();
        subtaskList.add(subtaskId);
    }

    /**
     * При удалении подзадачи требуется также удалить ее и из списка подзадач ее эпика.
     * @param epic эпик, у которого требуется удалить подзадачу
     * @param subtaskId идентификатор подзадачи для удаления
     */
    private void removeEpicSubtask(Epic epic, Long subtaskId) {
        epic.getSubtaskList().remove(subtaskId);
    }

    /**
     * Удаление списка подзадач эпика
     * @param epic эпик, у которого необходимо очистить список подзадач
     */
    private void removeAllEpicSubtasks(Epic epic) {
        epic.getSubtaskList().clear();
    }

    /**
     * Метод для проверки статуса эпика после операций с подзадачами. В случае добавления/изменения/удаления подзадачи
     * статус эпика должен быть пересчитан по следующей логике: если список подзадач пуст или все подзадачи имеют
     * статус NEW, то статус эпика также должен быть NEW. Если все подзадачи эпика имеют статус DONE, эпик также должен
     * иметь статус DONE. В противном случае статус эпика должен быть IN_PROGRESS.
     * @param epic эпик, для которого необходимо произвести проверку статуса
     */
    private void checkEpicStatus(Epic epic) {
        List<Long> subtasks = epic.getSubtaskList();

        if (subtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        int statusDone = 0;
        int statusNew = 0;

        for (Long subtaskId : subtasks) {
            Subtask subtask = subtaskList.get(subtaskId);
            switch (subtask.getStatus()) {
                case IN_PROGRESS:
                    epic.setStatus(Status.IN_PROGRESS);
                    return;
                case DONE:
                    statusDone++;
                    break;
                case NEW:
                    statusNew++;
            }
        }

        if (statusDone == subtaskList.size()) {
            epic.setStatus(Status.DONE);
        } else if (statusNew == subtaskList.size()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
