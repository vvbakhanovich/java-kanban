import manager.Managers;
import manager.TaskManager;
import tasks.BasicTask;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // создаем две простые задачи
        BasicTask task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        long task1Id = manager.addBasicTask(task1);
        BasicTask task2 = BasicTask.create("Задача 2", "Описание задачи 2", Status.NEW);
        long task2Id = manager.addBasicTask(task2);

        // вызываем метод getBasicTaskById, чтобы задача внеслись в историю просмотров
        manager.getBasicTaskById(task1Id);
        manager.getBasicTaskById(task2Id);

        //печатаем историю просмотров
        System.out.println("\nИстория просмотров после прочтения двух простых задач");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        //создаем эпик и две его подзадачи
        Epic epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        long epic1Id = manager.addEpic(epic1);

        Subtask subtask1 = Subtask.create("Подазадача 1", "Описание подзадачи 1",
                Status.NEW, epic1Id);
        Subtask subtask2 = Subtask.create("Подзадача 2", "Описание подзадачи 2",
                Status.NEW, epic1Id);
        long subtask1Id = manager.addSubtask(subtask1);
        long subtask2Id = manager.addSubtask(subtask2);

        //получаем созданные задачи
        manager.getEpicById(epic1Id);
        manager.getSubtaskById(subtask1Id);
        manager.getSubtaskById(subtask2Id);

        System.out.println("\n===================\n");
        System.out.println("После просмотра эпика и двух подзадач, история просмотров:");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        //создаем еще один эпик с одной подзадачей
        Epic epic2 = Epic.create("Эпик 2", "Описание эпика 2");
        long epic2Id = manager.addEpic(epic2);

        Subtask subtask3 = Subtask.create("Подзадача 3", "Описание подзадачи 3",
                Status.NEW, epic2Id);
        long subtask3Id = manager.addSubtask(subtask3);

        //считываем задачи
        manager.getEpicById(epic2Id);
        manager.getSubtaskById(subtask3Id);

        System.out.println("\n===================\n");
        System.out.println("После просмотра эпика и одной подзадачи, история просмотров:");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());


        //прочитаем еще пять любых задач, чтобы проверить, что размер списка равен 10 и задачи, добавленные первыми,
        //были вытеснены последующими задачами
        manager.getBasicTaskById(task1Id);
        manager.getBasicTaskById(task2Id);
        manager.getSubtaskById(subtask1Id);
        manager.getSubtaskById(subtask2Id);
        manager.getSubtaskById(subtask3Id);
        System.out.println("\n=============\n");
        System.out.println("После считывания еще 5 задач, история просмотров:");
        System.out.println(manager.getHistory());
        System.out.println(manager.getHistory().size());
    }
}
