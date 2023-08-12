import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task = null;
        Task task1 = new Task("Сдать проект", "Ботать и ботать", Status.NEW);

        Epic task2 = new Epic("Get a job", "Work hard and code", Status.NEW);
        Subtask task3 = new Subtask("Сдать спринт 3", "Боатем", Status.DONE, task2);
        Subtask task4 = new Subtask("RODO", "TODO", Status.NEW, task2);


        manager.addTaskOrEpic(task);
        manager.addTaskOrEpic(task1);
        manager.addTaskOrEpic(task2);
        manager.addSubtask(task3);
        manager.addSubtask(task4);


        System.out.println(manager.getTaskList());
        System.out.println(manager.getEpicList());
        System.out.println(manager.getEpicSubtaskList(task2));

        System.out.println(manager.getTaskById(1));
    }
}
