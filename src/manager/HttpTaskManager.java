package manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import http.KVTaskClient;
import tasks.BasicTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    //список ключей для хранения и запроса данных с KVServer
    private static final String basicTaskKey = "BASIC_TASK";
    private static final String epicKey = "EPIC";
    private static final String subtaskKey = "SUBTASK";
    private static final String historyKey = "HISTORY";
    private static final String sortedKey = "PRIORITY";
    private static final String idKey = "ID";
    private static KVTaskClient taskClient;
    private static Gson gson;

    public HttpTaskManager(String uri) {
        //Так как происходит наследование от FBTM, то необходимо вызвать конструктор родительского класса. Чтобы не
        //создавать FBTM в его конструктор передается null
       this(uri, false);
    }

    public HttpTaskManager(String uri, boolean isRestored) {
        super(null);
        taskClient = new KVTaskClient(uri);
        gson = Managers.getGson();
        if (isRestored) {
            load(this);
        }
    }

    /**
     * Сохраняет статус менеджера путем отправки текущего состояния на сервер. Выполняется после каждого изменения в
     * менеджере
     */
    @Override
    protected void save() {
        taskClient.put(basicTaskKey, gson.toJson(getBasicTaskList()));
        taskClient.put(epicKey, gson.toJson(getEpicList()));
        taskClient.put(subtaskKey, gson.toJson(getSubtaskList()));
        taskClient.put(historyKey, gson.toJson(getHistory()));
        taskClient.put(sortedKey, gson.toJson(getPrioritizedTasks()));
        taskClient.put(idKey, gson.toJson(taskId));
    }

    /**
     * Создание нового менеджера из базы данных, хранящейся на сервере
     * @param manager который требуется восстановить
     */
    public static void load(HttpTaskManager manager) {

        manager.taskId = gson.fromJson(taskClient.load(idKey), Long.class);

        Type taskListType = new TypeToken<ArrayList<BasicTask>>() {}.getType();
        ArrayList<BasicTask> basicTaskList = gson.fromJson(taskClient.load(basicTaskKey), taskListType);
        for (BasicTask basicTask : basicTaskList) {
            manager.basicTaskList.put(basicTask.getTaskId(), basicTask);
        }

        Type epicListType = new TypeToken<ArrayList<Epic>>() {}.getType();
        ArrayList<Epic> epicList = gson.fromJson(taskClient.load(epicKey), epicListType);
        for (Epic epic : epicList) {
            manager.epicList.put(epic.getTaskId(), epic);
        }

        Type subtaskListType = new TypeToken<ArrayList<Subtask>>() {}.getType();
        ArrayList<Subtask> subtaskList = gson.fromJson(taskClient.load(subtaskKey), subtaskListType);
        for (Subtask subtask : subtaskList) {
            manager.subtaskList.put(subtask.getTaskId(), subtask);
        }

        Type historyIdType = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> historyIdList = gson.fromJson(taskClient.load(historyKey), historyIdType);
        restoreHistory(historyIdList, manager);

        Type sortedSetType = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> sortedTaskSet = gson.fromJson(taskClient.load(sortedKey), sortedSetType);
        restorePrioritizedList(sortedTaskSet, manager);
    }

    private static void restoreHistory(List<Task> historyIds, HttpTaskManager manager) {
        for (Task task : historyIds) {
            manager.historyManager.add(task);
        }
    }

    private static void restorePrioritizedList(List<Task> sortedTasks, HttpTaskManager manager) {
        manager.sortedTasks.addAll(sortedTasks);
    }

}