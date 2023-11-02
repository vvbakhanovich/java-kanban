package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import exceptions.BadRequestException;
import exceptions.InvalidTimeException;
import manager.Managers;
import manager.TaskManager;
import tasks.BasicTask;
import tasks.Epic;
import tasks.Subtask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer server;
    private final Gson gson;
    private final TaskManager manager;

    public HttpTaskServer() throws IOException {
        this.manager = Managers.getHttpTaskManager();
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks/", this::handlePrioritizedTaskList);
        server.createContext("/tasks/history/", this::handleHistoryList);
        server.createContext("/tasks/task/", this::handleTasks);
        server.createContext("/tasks/epic/", this::handleEpics);
        server.createContext("/tasks/subtask/", this::handleSubtasks);
    }

    private void handleTasks(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String rawQuery = exchange.getRequestURI().getRawQuery();
        try {
            if (Pattern.matches("^/tasks/task/$", path)) {
                if ("GET".equals(method)) {
                    if (rawQuery != null) {
                        String pathId = rawQuery.replaceFirst("id=", "");
                        System.out.println("Получен запрос на получение задачи c id " + pathId);
                        long taskId = parsePathId(pathId);
                        if (taskId != -1) {
                            try {
                                BasicTask task = manager.getBasicTaskById(taskId);
                                String response = gson.toJson(task);
                                System.out.println("Задача в JSON: " + response);
                                sendJsonResponse(exchange, response);
                            } catch (NoSuchElementException e) {
                                System.out.println(e.getMessage());
                                exchange.sendResponseHeaders(400, 0);
                            }
                        } else {
                            System.out.println("Некорректный id задачи: " + pathId);
                            sendErrorResponse(exchange, "Некорректный id задачи: " + pathId);
                        }
                    } else {
                        System.out.println("Получен запрос на получение всех задач");
                        String response = gson.toJson(manager.getBasicTaskList());
                        System.out.println("Задачи в JSON: " + response);
                        sendJsonResponse(exchange, response);
                    }

                } else if ("POST".equals(method)) {
                    System.out.println("Получен запрос на добавление/обновление задачи");
                    String taskInRequest = getTaskFromRequest(exchange);
                    System.out.println("Задача в формате JSON " + taskInRequest);
                    BasicTask restoredTask = gson.fromJson(taskInRequest, BasicTask.class);
                    try {
                        if (restoredTask.getTaskId() == 0) {
                            System.out.println("Получен запрос на добавление задачи");
                            manager.addBasicTask(restoredTask);
                            System.out.println("Добавлена задача: " + restoredTask);
                        } else {
                            System.out.println("Получен запрос на обновление задачи с id " + restoredTask.getTaskId());
                            manager.updateBasicTask(restoredTask);
                            System.out.println("Обновлена задача: " + restoredTask);
                        }
                        exchange.sendResponseHeaders(200, 0);
                    } catch (InvalidTimeException | NoSuchElementException e) {
                        System.out.println(e.getMessage());
                        sendErrorResponse(exchange, e.getMessage());
                    }
                } else if ("DELETE".equals(method)) {
                    if (rawQuery != null) {
                        String pathId = rawQuery.replaceFirst("id=", "");
                        System.out.println("Получен запрос на удаление задачи c id " + pathId);
                        long taskId = parsePathId(pathId);
                        if (taskId != -1) {
                            try {
                                manager.removeBasicTaskById(taskId);
                                System.out.println("Удалена задача с id " + taskId);
                                exchange.sendResponseHeaders(200, 0);
                            } catch (NoSuchElementException e) {
                                System.out.println(e.getMessage());
                                exchange.sendResponseHeaders(400, 0);
                            }
                        } else {
                            System.out.println("Некорректный id задачи: " + pathId);
                            sendErrorResponse(exchange, "Некорректный id задачи: " + pathId);
                        }
                    } else {
                        System.out.println("Получен запрос на удаление всех задач");
                        manager.removeAllBasicTasks();
                        System.out.println("Все задачи удалены");
                        exchange.sendResponseHeaders(200, 0);
                    }
                } else {
                    System.out.println("Метод не поддерживается");
                    exchange.sendResponseHeaders(405,0);
                }
            }
        } finally {
            exchange.close();
        }
    }

    private void handleEpics(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String rawQuery = exchange.getRequestURI().getRawQuery();
        try {
            if (Pattern.matches("^/tasks/epic/$", path)) {
                if ("GET".equals(method)) {
                    if (rawQuery != null) {
                        String pathId = rawQuery.replaceFirst("id=", "");
                        System.out.println("Получен запрос на получение эпика c id " + pathId);
                        long taskId = parsePathId(pathId);
                        if (taskId != -1) {
                            try {
                                Epic epic = manager.getEpicById(taskId);
                                String response = gson.toJson(epic);
                                System.out.println("Эпик в JSON: " + response);
                                sendJsonResponse(exchange, response);
                            } catch (NoSuchElementException e) {
                                System.out.println(e.getMessage());
                                sendErrorResponse(exchange, e.getMessage());
                            }
                        } else {
                            System.out.println("Некорректный id эпика: " + pathId);
                            sendErrorResponse(exchange, "Некорректный id эпика: " + pathId);
                        }
                    } else {
                        System.out.println("Получен запрос на получение всех эпиков");
                        String response = gson.toJson(manager.getEpicList());
                        System.out.println("Задачи в JSON: " + response);
                        sendJsonResponse(exchange, response);
                    }
                } else if ("POST".equals(method)) {
                    if (Pattern.matches("^/tasks/epic/$", path)) {
                        System.out.println("Получен запрос на добавление/обновление эпика");
                        String taskInRequest = getTaskFromRequest(exchange);
                        System.out.println("Эпик в формате JSON " + taskInRequest);
                        Epic restoredEpic = gson.fromJson(taskInRequest, Epic.class);
                        try {
                            if (restoredEpic.getTaskId() == 0) {
                                System.out.println("Получен запрос на добавление эпика");
                                manager.addEpic(restoredEpic);
                                System.out.println("Добавлен эпик: " + restoredEpic);
                            } else {
                                System.out.println("Получен запрос на обновление эпика с id " +
                                        restoredEpic.getTaskId());
                                manager.updateEpic(restoredEpic);
                                System.out.println("Обновлен эпик: " + restoredEpic);
                            }
                            exchange.sendResponseHeaders(200, 0);
                        } catch (InvalidTimeException | NoSuchElementException e) {
                            System.out.println(e.getMessage());
                            sendErrorResponse(exchange, e.getMessage());
                        }
                    }
                } else if ("DELETE".equals(method)) {
                    System.out.println("Получен запрос на удаление эпика");

                    if (rawQuery != null) {
                        String pathId = rawQuery.replaceFirst("id=", "");
                        System.out.println("Получен запрос на удаление эпика c id " + pathId);
                        long taskId = parsePathId(pathId);
                        if (taskId != -1) {
                            try {
                                manager.removeEpicById(taskId);
                                System.out.println("Удален эпик с id " + taskId);
                                exchange.sendResponseHeaders(200, 0);
                            } catch (NoSuchElementException e) {
                                System.out.println(e.getMessage());
                                exchange.sendResponseHeaders(405, 0);
                            }

                        } else {
                            System.out.println("Некорректный id эпика: " + pathId);
                            sendErrorResponse(exchange, "Некорректный id эпика: " + pathId);
                        }
                    } else {
                        System.out.println("Получен запрос на удаление всех эпиков");
                        manager.removeAllEpics();
                        System.out.println("Все эпики удалены");
                        exchange.sendResponseHeaders(200, 0);
                    }
                } else {
                    System.out.println("Метод не поддерживается");
                    exchange.sendResponseHeaders(405, 0);
                }
            }
        } finally {
            exchange.close();
        }
    }

    private void handleSubtasks(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String rawQuery = exchange.getRequestURI().getRawQuery();
        try {
            if ("GET".equals(method)) {
                if (Pattern.matches("^/tasks/subtask/$", path)) {
                    if (rawQuery != null) {
                        String pathId = rawQuery.replaceFirst("id=", "");
                        System.out.println("Получен запрос на получение подзадачи c id " + pathId);
                        long taskId = parsePathId(pathId);
                        if (taskId != -1) {
                            try {
                                Subtask subtask = manager.getSubtaskById(taskId);
                                String response = gson.toJson(subtask);
                                System.out.println("Подзадача в JSON: " + response);
                                sendJsonResponse(exchange, response);
                            } catch (NoSuchElementException e) {
                                System.out.println(e.getMessage());
                                sendErrorResponse(exchange, e.getMessage());
                            }
                        } else {
                            System.out.println("Некорректный id подзадачи: " + pathId);
                            sendErrorResponse(exchange, "Некорректный id подзадачи: " + pathId);
                        }
                    } else {
                        System.out.println("Получен запрос на получение всех подзадач");
                        String response = gson.toJson(manager.getSubtaskList());
                        System.out.println("Задачи в JSON: " + response);
                        sendJsonResponse(exchange, response);
                    }
                } else if (Pattern.matches("^/tasks/subtask/epic/$", path)) {
                    if (rawQuery != null) {
                        String pathId = rawQuery.replaceFirst("id=", "");
                        System.out.println("Получен запрос на получение списка подзадач эпика c id " + pathId);
                        long taskId = parsePathId(pathId);
                        if (taskId != -1) {
                            String response = gson.toJson(manager.getEpicSubtaskList(taskId));
                            System.out.println("Список подзадач эпика в JSON: " + response);
                            sendJsonResponse(exchange, response);
                        } else {
                            System.out.println("Некорректный id эпика: " + pathId);
                            sendErrorResponse(exchange, "Некорректный id эпика: " + pathId);
                        }
                    } else {
                        System.out.println("Получен некорректный запрос на получение всех задач эпика. " +
                                "Не предоставлен идентификатор");
                        sendErrorResponse(exchange, "Получен некорректный запрос на получение всех задач " +
                                "эпика. Не предоставлен идентификатор");
                    }
                }
            } else if ("POST".equals(method)) {
                System.out.println("Получен запрос на добавление/обновление подзадачи");
                String taskInRequest = getTaskFromRequest(exchange);
                System.out.println("Подзадача в формате JSON " + taskInRequest);
                Subtask restoredSubtask = gson.fromJson(taskInRequest, Subtask.class);
                try {
                    if (restoredSubtask.getTaskId() == 0) {
                        System.out.println("Получен запрос на добавление подзадачи");
                        manager.addSubtask(restoredSubtask);
                        System.out.println("Добавлен эпик: " + restoredSubtask);
                    } else {
                        System.out.println("Получен запрос на обновление эпика с id " + restoredSubtask.getTaskId());
                        manager.updateSubtask(restoredSubtask);
                        System.out.println("Обновлена подзадача: " + restoredSubtask);
                    }
                    exchange.sendResponseHeaders(200, 0);
                } catch (InvalidTimeException | NoSuchElementException e) {
                    System.out.println(e.getMessage());
                    sendErrorResponse(exchange, e.getMessage());
                }
            } else if ("DELETE".equals(method)) {
                System.out.println("Получен запрос на удаление подзадачи");

                if (rawQuery != null) {
                    String pathId = rawQuery.replaceFirst("id=", "");
                    System.out.println("Получен запрос на удаление подзадачи c id " + pathId);
                    long taskId = parsePathId(pathId);
                    if (taskId != -1) {
                        try {
                            manager.removeSubtaskById(taskId);
                            System.out.println("Удалена подзадача с id " + taskId);
                            exchange.sendResponseHeaders(200, 0);
                        } catch (NoSuchElementException e) {
                            System.out.println(e.getMessage());
                            exchange.sendResponseHeaders(405, 0);
                        }
                    } else {
                        System.out.println("Некорректный id подзадачи: " + pathId);
                        sendErrorResponse(exchange, "Некорректный id подзадачи: " + pathId);
                    }
                } else {
                    System.out.println("Получен запрос на удаление всех подзадач");
                    manager.removeAllSubtasks();
                    System.out.println("Все подзадачи удалены");
                    exchange.sendResponseHeaders(200, 0);
                }
            } else {
                System.out.println("Метод не поддерживается");
                exchange.sendResponseHeaders(405, 0);
            }
        } finally {
            exchange.close();
        }
    }

    private void handlePrioritizedTaskList(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if ("GET".equals(method)) {
                if (Pattern.matches("^/tasks/$", path)) {
                    System.out.println("Получен запрос на получение списка отсортированных задач");
                    String response = gson.toJson(manager.getPrioritizedTasks());
                    System.out.println("Отсортированные задачи в JSON: " + response);
                    sendJsonResponse(exchange, response);
                } else if (Pattern.matches("^/tasks/history$", path)) {
                    System.out.println("Получен запрос на получение списка просмотренных задач");
                    String response = gson.toJson(manager.getHistory());
                    System.out.println("Список просмотренных задач в JSON: " + response);
                    sendJsonResponse(exchange, response);
                }
            } else {
                System.out.println("Использован недоступный метод: " + method + "по пути: " + path);
                exchange.sendResponseHeaders(405, 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private void handleHistoryList(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if ("GET".equals(method)) {
                System.out.println("Получен запрос на получение списка просмотренных задач");
                String response = gson.toJson(manager.getHistory());
                System.out.println("Список просмотренных задач в JSON: " + response);
                sendJsonResponse(exchange, response);
            } else {
                System.out.println("Использован недоступный метод: " + method + "по пути: " + path);
                exchange.sendResponseHeaders(405, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private long parsePathId(String pathId) {
        try {
            return Long.parseLong(pathId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Запущен HttpTaskServer на порту " + PORT);
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("HttpTaskServer остановлен на порту " + PORT);

    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer server1 = new HttpTaskServer();
        server1.start();
    }

    private void sendJsonResponse(HttpExchange exchange, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(200, 0);
        exchange.getResponseBody().write(response.getBytes(UTF_8));
    }

    private String getTaskFromRequest(HttpExchange exchange) throws IOException {
        String taskInRequest = new String(exchange.getRequestBody().readAllBytes(), UTF_8);
        if (taskInRequest.isEmpty()) {
            System.out.println("Тело запроса пустое! В теле запроса не была передана задача!");
            sendErrorResponse(exchange,
                    "Тело запроса пустое! В теле запроса не была передана задача!");
            throw new BadRequestException("Тело запроса пустое! В теле запроса не была передана задача!");
        } else {
            return taskInRequest;
        }
    }
    private void sendErrorResponse(HttpExchange exchange, String errorMessage) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(400, 0);
        exchange.getResponseBody().write(errorMessage.getBytes(UTF_8));
    }


}


