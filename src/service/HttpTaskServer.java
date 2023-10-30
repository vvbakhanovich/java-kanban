package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tasks.BasicTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

public class HttpTaskServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpTaskServer.class);
    private static final int PORT = 8080;
    private HttpServer server;
    private Gson gson;
    private TaskManager manager;
    public HttpTaskServer() throws IOException {
        this.manager = Managers.getFileManager();
        gson = new Gson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handleTasks);
    }

    private void handleTasks(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            switch (method) {
                case "GET":
                    if (Pattern.matches("^/tasks/task$", path)) {
                        logger.info("Получен запрос всех задач по пути: " + path);
                        String response = gson.toJson(manager.getBasicTaskList());
                        logger.info("Задачи в JSON: " + response);
                        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
                        exchange.sendResponseHeaders(200, 0);
                        exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
                        break;
                    }
                    break;
                case "POST":
                    if (Pattern.matches("^/tasks/task$", path)) {
                        logger.info("Получен запрос на добавление задачи по пути: " + path);
                        String taskInRequest = new String(exchange.getRequestBody().readAllBytes());
                        logger.info("Задача в формате JSON " + taskInRequest);
                        BasicTask restoredTask = gson.fromJson(taskInRequest, BasicTask.class);
                        manager.addBasicTask(restoredTask);
                        logger.info("Добавлена восстановленная задача: " + restoredTask);
                        exchange.sendResponseHeaders(200, 0);
                }

                    break;
                case "DELETE":
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        String pathId = path.replaceFirst("/api/v1/tasks/task/", "");
                        int taskId = parsePathId(pathId);
                        if (taskId != -1) {
                            manager.removeBasicTaskById(taskId);
                            logger.info("Удалена задача с id " + taskId);
                            exchange.sendResponseHeaders(200, 0);
                        } else {
                            logger.info("Некорректный id задачи: " + pathId);
                            exchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    }
                    break;

                default:
                    logger.info("Использован недоступный метод: " + method + "по пути: " + path);
                    exchange.sendResponseHeaders(405, 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private int parsePathId(String pathId) {
        try {
            return Integer.parseInt(pathId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void start() {
        logger.info("Запускаем HttpTaskServer на порту " + PORT);
        server.start();
    }

    public void stop() {
        server.stop(0);
        logger.info("HttpTaskServer остановлен на порту " + PORT);

    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
    }
}
