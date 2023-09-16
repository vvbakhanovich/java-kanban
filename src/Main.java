import manager.Managers;
import manager.TaskManager;
import tasks.BasicTask;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // создаем и добавляем две простые задачи
        BasicTask task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        long task1Id = manager.addBasicTask(task1);
        BasicTask task2 = BasicTask.create("Задача 2", "Описание задачи 2", Status.NEW);
        long task2Id = manager.addBasicTask(task2);

        //создаем и добавляем эпик с тремя подзадачами
        Epic epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        long epic1Id = manager.addEpic(epic1);

        Subtask subtask1 = Subtask.create("Подазадача 1", "Описание подзадачи 1",
                Status.NEW, epic1Id);
        Subtask subtask2 = Subtask.create("Подзадача 2", "Описание подзадачи 2",
                Status.NEW, epic1Id);
        Subtask subtask3 = Subtask.create("Подзадача 3", "Описание подзадачи 3",
                Status.NEW, epic1Id);
        long subtask1Id = manager.addSubtask(subtask1);
        long subtask2Id = manager.addSubtask(subtask2);
        long subtask3Id = manager.addSubtask(subtask3);

        //создаем и добавляем эпик без подзадач
        Epic epic2 = Epic.create("Эпик 2", "Описание эпика 2");
        long epic2Id = manager.addEpic(epic2);

        // вызываем метод getBasicTaskById, чтобы задача task1 внеслась в историю просмотров
        manager.getBasicTaskById(task1Id);
        //печатаем список просмотров
        System.out.println("\n===================\n");
        System.out.println("После просмотра task1, история просмотров:");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        //просматриваем 2й эпик
        manager.getEpicById(epic2Id);

        //печатаем историю просмотров
        System.out.println("\n===================\n");
        System.out.println("После просмотра epic2, история просмотров:");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        //просматриваем оставшиеся задачи
        manager.getEpicById(epic1Id);
        manager.getSubtaskById(subtask1Id);
        manager.getSubtaskById(subtask2Id);
        manager.getSubtaskById(subtask3Id);
        manager.getBasicTaskById(task2Id);
        manager.getEpicById(epic2Id);

        System.out.println("\n===================\n");
        System.out.println("После однократного просмотра оставшихся задач:");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        manager.getBasicTaskById(task1Id);
        System.out.println("\n===================\n");
        System.out.println("После повторного просмотра task1 (из начала списка):");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        manager.getSubtaskById(subtask3Id);
        System.out.println("\n===================\n");
        System.out.println("После повторного просмотра subtask3 (из середины списка):");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        manager.getSubtaskById(subtask3Id);
        System.out.println("\n===================\n");
        System.out.println("После еще одного просмотра subtask3 (из конца списка):");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        //тесты по удалению задач
        manager.removeEpicById(epic1Id);
        System.out.println("\n===================\n");
        System.out.println("После удаления epic1 (из начала списка):");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        manager.removeSubtaskById(subtask3Id);
        System.out.println("\n===================\n");
        System.out.println("После удаления subtask3 (из конца списка):");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        manager.removeBasicTaskById(task2Id);
        System.out.println("\n===================\n");
        System.out.println("После удаления task2 (из середины списка):");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());

        manager.removeAllEpics();
        System.out.println("\n===================\n");
        System.out.println("После удаления всех эпиков:");
        System.out.println(manager.getHistory());
        System.out.println("Количество просмотренных задач:" + manager.getHistory().size());
    }
}
