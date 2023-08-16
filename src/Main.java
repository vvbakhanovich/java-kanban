import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import service.Manager;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        manager.getTaskManager().addTask(task1);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);
        manager.getTaskManager().addTask(task2);


        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.getEpicManager().addTask(epic1);

        Subtask subtask1 = new Subtask("Подазадача 1", "Описание подзадачи 1", Status.NEW, epic1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1);
        manager.getSubtaskManager().addTask(subtask1);
        manager.getSubtaskManager().addTask(subtask2);


        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.getEpicManager().addTask(epic2);

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, epic2);
        manager.getSubtaskManager().addTask(subtask3);

        System.out.println("Список всех задач:");
        System.out.println(manager.getTaskManager().getTaskList());
        System.out.println("Список всех подзадач:");
        System.out.println(manager.getSubtaskManager().getTaskList());
        System.out.println("Список всех 'эпиков:");
        System.out.println(manager.getEpicManager().getTaskList());
        System.out.println("Подзадачи первого эпика:");
        System.out.println(manager.getEpicManager().getEpicSubtaskList(epic1));
        System.out.println("Подзадачи второго эпика:");
        System.out.println(manager.getEpicManager().getEpicSubtaskList(epic2));

        System.out.println();
        System.out.println("Установка новых статусов");

        task1.setStatus(Status.DONE);
        manager.getTaskManager().updateTask(task1.getTaskId(), task1);

        task2.setStatus(Status.IN_PROGRESS);
        manager.getTaskManager().updateTask(task2.getTaskId(), task2);

        subtask1.setStatus(Status.IN_PROGRESS);
        manager.getSubtaskManager().updateTask(subtask1.getTaskId(), subtask1);

        subtask2.setStatus(Status.DONE);
        manager.getSubtaskManager().updateTask(subtask2.getTaskId(), subtask2);

        subtask3.setStatus(Status.DONE);
        manager.getSubtaskManager().updateTask(subtask3.getTaskId(), subtask3);

        System.out.println("Список всех задач:");
        System.out.println(manager.getTaskManager().getTaskList());
        System.out.println("Список всех подзадач:");
        System.out.println(manager.getSubtaskManager().getTaskList());
        System.out.println("Список всех 'эпиков:");
        System.out.println(manager.getEpicManager().getTaskList());
        System.out.println("Подзадачи первого эпика:");
        System.out.println(manager.getEpicManager().getEpicSubtaskList(epic1));
        System.out.println("Подзадачи второго эпика:");
        System.out.println(manager.getEpicManager().getEpicSubtaskList(epic2));

        System.out.println();
        // удаление одной задачи
        manager.getTaskManager().removeTaskById(task1.getTaskId());
        System.out.println("Список всех задач после удаления одной задачи:");
        System.out.println(manager.getTaskManager().getTaskList());
        // удаление подзадачи первого эпика
        manager.getSubtaskManager().removeTaskById(subtask1.getTaskId());

        System.out.println("Подзадачи первого эпика после удаления подзадачи");
        System.out.println(manager.getEpicManager().getEpicSubtaskList(epic1));

        System.out.println("Первый эпик после удаления подзадачи");
        System.out.println(manager.getEpicManager().getTaskById(epic1.getTaskId()));
        // удаление всех подзадач
        manager.removeAllSubtasks();
        System.out.println("Список всех эпиков после удаления подзадач:");
        System.out.println(manager.getEpicManager().getTaskList());




    }
}
