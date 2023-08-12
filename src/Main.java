import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task = new Task("Написать прогу", "Ботать", Status.NEW);
        Task task1 = new Task("Сдать проект", "Ботать и ботать", Status.NEW);

        Epic task2 = new Epic("Get a job", "Work hard and code", Status.NEW);
        Subtask task3 = new Subtask("Сдать спринт 3", "Боатем", Status.NEW, task2);


        manager.addTask(task);
        manager.addTask(task1);
        manager.addTask(task2);

        System.out.println(manager.getTaskList());
        System.out.println(manager.getEpicList());
    }
}
