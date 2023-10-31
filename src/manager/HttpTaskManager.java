package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import http.KVTaskClient;
import http.LocalDateTimeAdapter;
import tasks.BasicTask;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HttpTaskManager extends FileBackedTasksManager {

    private final KVTaskClient taskClient;
    private final Gson gson;
    public HttpTaskManager(String uri) {
        super(null);
        taskClient = new KVTaskClient(uri);
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @Override
    public void save() {
        taskClient.put("BasicTasks", gson.toJson(getBasicTaskList()));
        taskClient.put("Epics", gson.toJson(getEpicList()));
        taskClient.put("Subtasks", gson.toJson(getSubtaskList()));
        taskClient.put("History", gson.toJson(getHistory()));
        taskClient.put("PrioritizedList", gson.toJson(getPrioritizedTasks()));
    }

    public void load() {
        Type taskListType = new TypeToken<ArrayList<BasicTask>>(){}.getType();
        ArrayList<BasicTask> basicTaskList = gson.fromJson(taskClient.load("BasicTasks"), taskListType);
    }

}
