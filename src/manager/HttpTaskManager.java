package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.KVTaskClient;
import http.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class HttpTaskManager extends FileBackedTasksManager {

    private final KVTaskClient taskClient;
    private final Gson gson;
    public HttpTaskManager(String uri) {
        super("resources/FBTM_test.csv");
        taskClient = new KVTaskClient(uri);
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    @Override
    protected void save() {
        taskClient.put("BasicTasks", gson.toJson(getBasicTaskList()));
        taskClient.put("Epics", gson.toJson(getEpicList()));
        taskClient.put("Subtasks", gson.toJson(getSubtaskList()));
        taskClient.put("History", gson.toJson(getHistory()));
        taskClient.put("PrioritizedList", gson.toJson(getPrioritizedTasks()));
    }

}
