import manager.InMemoryTaskManager;
import tasks.*;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager(new HashMap<>(), new HashMap<>(), new HashMap<>());

        BasicTask task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        manager.addBasicTask(task1);
        BasicTask task2 = BasicTask.create("Задача 2", "Описание задачи 2", Status.NEW);
        manager.addBasicTask(task2);


        Epic epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);

        Subtask subtask1 = Subtask.create("Подазадача 1", "Описание подзадачи 1",
                Status.NEW, epic1.getTaskId());
        Subtask subtask2 = Subtask.create("Подзадача 2", "Описание подзадачи 2",
                Status.NEW, epic1.getTaskId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);


        Epic epic2 = Epic.create("Эпик 2", "Описание эпика 2");
        manager.addEpic(epic2);

        Subtask subtask3 = Subtask.create("Подзадача 3", "Описание подзадачи 3",
                Status.NEW, epic2.getTaskId());
        manager.addSubtask(subtask3);

        System.out.println("Список всех задач:");
        System.out.println(manager.getBasicTaskList());
        System.out.println("Список всех подзадач:");
        System.out.println(manager.getSubtaskList());
        System.out.println("Список всех 'эпиков:");
        System.out.println(manager.getEpicList());
        System.out.println("Подзадачи первого эпика:");
        System.out.println(manager.getEpicSubtaskList(epic1));
        System.out.println("Подзадачи второго эпика:");
        System.out.println(manager.getEpicSubtaskList(epic2));

        System.out.println();
        System.out.println("Установка новых статусов");

        task1.setStatus(Status.DONE);
        manager.updateBasicTask(task1);

        task2.setStatus(Status.IN_PROGRESS);
        manager.updateBasicTask(task2);

        subtask1.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask( subtask1);

        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);

        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask3);

        System.out.println("Список всех задач:");
        System.out.println(manager.getBasicTaskList());
        System.out.println("Список всех подзадач:");
        System.out.println(manager.getSubtaskList());
        System.out.println("Список всех 'эпиков:");
        System.out.println(manager.getEpicList());
        System.out.println("Подзадачи первого эпика:");
        System.out.println(manager.getEpicSubtaskList(epic1));
        System.out.println("Подзадачи второго эпика:");
        System.out.println(manager.getEpicSubtaskList(epic2));

        System.out.println();
        // удаление одной задачи
        manager.removeBasicTaskById(task1.getTaskId());
        System.out.println("Список всех задач после удаления одной задачи:");
        System.out.println(manager.getBasicTaskList());
        // удаление подзадачи первого эпика
        manager.removeSubtaskById(subtask1.getTaskId());

        System.out.println("Подзадачи первого эпика после удаления подзадачи");
        System.out.println(manager.getEpicSubtaskList(epic1));

        System.out.println("Первый эпик после удаления подзадачи");
        System.out.println(manager.getEpicById(epic1.getTaskId()));
        // удаление всех подзадач
        manager.removeAllSubtasks();
        System.out.println("Список всех эпиков после удаления подзадач:");
        System.out.println(manager.getEpicList());
    }
}
