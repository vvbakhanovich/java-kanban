import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager manager = new InMemoryTaskManager(
                new HashMap<>(), new HashMap<>(),
                new HashMap<>(), historyManager
        );

        BasicTask task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        manager.add(task1);
        BasicTask task2 = BasicTask.create("Задача 2", "Описание задачи 2", Status.NEW);
        manager.add(task2);


        Epic epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        manager.add(epic1);

        Subtask subtask1 = Subtask.create("Подазадача 1", "Описание подзадачи 1",
                Status.NEW, epic1.getTaskId());
        Subtask subtask2 = Subtask.create("Подзадача 2", "Описание подзадачи 2",
                Status.NEW, epic1.getTaskId());
        manager.add(subtask1);
        manager.add(subtask2);


        Epic epic2 = Epic.create("Эпик 2", "Описание эпика 2");
        manager.add(epic2);

        Subtask subtask3 = Subtask.create("Подзадача 3", "Описание подзадачи 3",
                Status.NEW, epic2.getTaskId());
        manager.add(subtask3);

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

//        System.out.println();
//        System.out.println("Установка новых статусов");
//
//        task1.setStatus(Status.DONE);
//        manager.update(task1);
//
//        task2.setStatus(Status.IN_PROGRESS);
//        manager.update(task2);
//
//        subtask1.setStatus(Status.IN_PROGRESS);
//        manager.update( subtask1);
//
//        subtask2.setStatus(Status.DONE);
//        manager.update(subtask2);
//
//        subtask3.setStatus(Status.DONE);
//        manager.update(subtask3);
//
//        System.out.println("Список всех задач:");
//        System.out.println(manager.getBasicTaskList());
//        System.out.println("Список всех подзадач:");
//        System.out.println(manager.getSubtaskList());
//        System.out.println("Список всех 'эпиков:");
//        System.out.println(manager.getEpicList());
//        System.out.println("Подзадачи первого эпика:");
//        System.out.println(manager.getEpicSubtaskList(epic1));
//        System.out.println("Подзадачи второго эпика:");
//        System.out.println(manager.getEpicSubtaskList(epic2));
//
//        System.out.println();
//        // удаление одной задачи
//        manager.removeBasicTaskById(task1.getTaskId());
//        System.out.println("Список всех задач после удаления одной задачи:");
//        System.out.println(manager.getBasicTaskList());
//        // удаление подзадачи первого эпика
//        manager.removeSubtaskById(subtask1.getTaskId());
//
//        System.out.println("Подзадачи первого эпика после удаления подзадачи");
//        System.out.println(manager.getEpicSubtaskList(epic1));
//
//        System.out.println("Первый эпик после удаления подзадачи");
//        System.out.println(manager.getEpicById(epic1.getTaskId()));
//        // удаление всех подзадач
//        manager.removeAllSubtasks();
//        System.out.println("Список всех эпиков после удаления подзадач:");
//        System.out.println(manager.getEpicList());

        System.out.println("\n=============\n");
        System.out.println("Получаем задачи по id.");
        manager.getBasicTaskById(task1.getTaskId());
        manager.getBasicTaskById(task2.getTaskId());
        manager.getSubtaskById(subtask1.getTaskId());
        manager.getSubtaskById(subtask2.getTaskId());
        manager.getSubtaskById(subtask3.getTaskId());
        manager.getEpicById(epic1.getTaskId());
        manager.getEpicById(epic2.getTaskId());
        manager.getBasicTaskById(task1.getTaskId());
        manager.getBasicTaskById(task2.getTaskId());
        manager.getSubtaskById(subtask1.getTaskId());
        manager.getSubtaskById(subtask2.getTaskId());
        manager.getSubtaskById(subtask2.getTaskId());
        manager.getSubtaskById(subtask2.getTaskId());
        System.out.println("История просмотров:");
        System.out.println(historyManager.getHistory());
        System.out.println(historyManager.getHistory().size());
    }
}
