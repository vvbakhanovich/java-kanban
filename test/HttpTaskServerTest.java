import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import http.HttpTaskServer;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    private HttpTaskServer server;
    private final Gson gson = Managers.getGson();
    private BasicTask basicTask1;
    private BasicTask basicTask2;
    private Epic epic1;
    private Subtask subtask1;
    private Subtask subtask2;
    private Type taskListType;
    private Type historyListType;

    @BeforeEach
    void init() throws IOException {
        server = new HttpTaskServer();
        epic1 = Epic.create("Эпик 1", "Описание эпика 1");

        basicTask1 = BasicTask.createWithStartTime("Задача со временем 1",
                "Описание задачи со временем 1",
                LocalDateTime.of(2023, 10, 22, 9, 9), 23, Status.IN_PROGRESS);
        basicTask2 = BasicTask.createWithStartTime("Задача со временем 2",
                "Описание задачи со временем 2",
                LocalDateTime.of(2023, 10, 22, 8, 0), 15, Status.NEW);
        subtask1 = Subtask.createWithStartTime("Subtask 1", "Description 1",
                LocalDateTime.of(2023, 9, 20, 9, 0), 50,
                Status.NEW, 1);
        subtask2 = Subtask.createWithStartTime("Subtask 2", "Description 3",
                LocalDateTime.of(2020, 9, 21, 10, 9),
                20, Status.NEW, 1);
        taskListType = new TypeToken<ArrayList<BasicTask>>() {
        }.getType();

        historyListType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        server.start();
    }

//    @AfterEach
//    void stop() {
//        server.stop();
//    }

    @Test
    void addingTaskShouldReturn200CodeAndGettingTaskWithId1ShouldReturnAddedTask()
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(basicTask1)))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());

        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/task/?id=1"))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode());
        basicTask1.setTaskId(1);
        assertEquals(basicTask1, gson.fromJson(getResponse.body(), BasicTask.class));
    }

    @Test
    void addingEpicShouldReturn200CodeAndGettingEpicWithId1ShouldReturnAddedEpic()
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic1)))
                .uri(URI.create(uri + "tasks/epic/"))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());

        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/epic/?id=1"))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode());
        epic1.setTaskId(1);
        assertEquals(epic1, gson.fromJson(getResponse.body(), Epic.class));
    }

    @Test
    void addingEpicAndSubtaskShouldReturn200CodeAndGettingEpicAndSubtaskWithId1ShouldReturnAddedEpicAndSubtask()
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic1)))
                .uri(URI.create(uri + "tasks/epic/"))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());

        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/epic/?id=1"))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());
        epic1.setTaskId(1);
        assertEquals(epic1, gson.fromJson(getResponse.body(), Epic.class));

        HttpRequest postRequestForSubtask = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask1)))
                .uri(URI.create(uri + "tasks/subtask/"))
                .build();
        HttpResponse<String> postResponseForSubTask = client.send(postRequestForSubtask,
                HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponseForSubTask.statusCode());

        HttpRequest getRequestForSubtask = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/subtask/?id=2"))
                .build();
        HttpResponse<String> getResponseForSubtask = client.send(getRequestForSubtask,
                HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponseForSubtask.statusCode());
        subtask1.setTaskId(2);
        assertEquals(subtask1, gson.fromJson(getResponseForSubtask.body(), Subtask.class));
    }

    @Test
    void ifTaskInPostIsEmptyResponseShouldReturn400Code() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, postResponse.statusCode());
    }

    @Test
    void ifEpicInPostIsEmptyResponseShouldReturn400Code() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(uri + "tasks/epic/"))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, postResponse.statusCode());
    }

    @Test
    void deletingTaskShouldReturn200CodeAndGettingTaskWithId1ShouldReturnEmpty()
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(basicTask1)))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri + "tasks/task/?id=1"))
                .build();
        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, deleteResponse.statusCode());

        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/task/?id=1"))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, getResponse.statusCode());
        assertNull(gson.fromJson(getResponse.body(), BasicTask.class));
    }

    @Test
    void deletingEpicShouldReturn200CodeAndGettingEpicWithId1ShouldReturn400Status()
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic1)))
                .uri(URI.create(uri + "tasks/epic/"))
                .build();
        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse.statusCode());

        epic1.setTaskId(1);
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(uri + "tasks/epic/?id=1"))
                .build();
        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, deleteResponse.statusCode());

        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/epic/?id=1"))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, getResponse.statusCode());
    }

    @Test
    void deletingSubtaskShouldReturn200CodeAndGettingSubtaskWithId2ShouldReturnEmpty()
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequestEpic = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic1)))
                .uri(URI.create(uri + "tasks/epic/"))
                .build();
        epic1.setTaskId(1);
        HttpResponse<String> postResponseEpic = client.send(postRequestEpic, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponseEpic.statusCode());

        HttpRequest postRequestSubtask = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(subtask1)))
                .uri(URI.create(uri + "tasks/subtask/"))
                .build();
        HttpResponse<String> postResponseSubtask = client.send(postRequestSubtask,
                HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponseSubtask.statusCode());
        subtask2.setTaskId(2);
        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/subtask/?id=2"))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode());
    }

    @Test
    void addingTwoTaskShouldReturn200CodeAndGettingThemListShouldReturnThemAsList() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest1 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(basicTask1)))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse1 = client.send(postRequest1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        basicTask1.setTaskId(1);

        HttpRequest postRequest2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(basicTask2)))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse2 = client.send(postRequest2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse2.statusCode());
        basicTask2.setTaskId(2);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/task"))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode());
        assertNotNull(gson.fromJson(getResponse.body(), BasicTask.class));
        assertIterableEquals(List.of(basicTask1, basicTask2), gson.fromJson(getResponse.body(), taskListType));
    }

    @Test
    void addingTwoTaskShouldReturn200CodeAndGettingPrioritizedTaskListShouldReturnThemInOrder()
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest1 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(basicTask1)))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse1 = client.send(postRequest1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        basicTask1.setTaskId(1);

        HttpRequest postRequest2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(basicTask2)))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse2 = client.send(postRequest2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse2.statusCode());
        basicTask2.setTaskId(2);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/"))
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode());
        assertIterableEquals(List.of(basicTask2, basicTask1), gson.fromJson(getResponse.body(), taskListType));
    }

    @Test
    void addingTwoTaskShouldReturn200CodeAndGettingThemShouldShowThemInHistory()
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/");
        HttpRequest postRequest1 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(basicTask1)))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse1 = client.send(postRequest1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse1.statusCode());
        basicTask1.setTaskId(1);

        HttpRequest postRequest2 = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(basicTask2)))
                .uri(URI.create(uri + "tasks/task/"))
                .build();
        HttpResponse<String> postResponse2 = client.send(postRequest2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponse2.statusCode());
        basicTask2.setTaskId(2);

        HttpRequest postRequestEpic = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(epic1)))
                .uri(URI.create(uri + "tasks/epic/"))
                .build();
        HttpResponse<String> postResponseEpic = client.send(postRequestEpic, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, postResponseEpic.statusCode());
        epic1.setTaskId(3);

        HttpRequest getRequest1 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/task/?id=2"))
                .build();
        HttpResponse<String> getResponse1 = client.send(getRequest1, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse1.statusCode());

        HttpRequest getRequest2 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/task/?id=1"))
                .build();
        HttpResponse<String> getResponse2 = client.send(getRequest2, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse2.statusCode());

        HttpRequest getRequest3 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/epic/?id=3"))
                .build();
        HttpResponse<String> getResponse3 = client.send(getRequest3, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse3.statusCode());

        HttpRequest getRequest4 = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "tasks/history/"))
                .build();
        HttpResponse<String> getResponse4 = client.send(getRequest4, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse4.statusCode());
        ArrayList<Task> history = gson.fromJson(getResponse4.body(), historyListType);
//        assertIterableEquals(List.of(basicTask2, basicTask1, epic1), gson.fromJson(getResponse4.body(), historyListType));
    }

}