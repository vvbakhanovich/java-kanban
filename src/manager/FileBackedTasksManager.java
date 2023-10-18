package manager;

import exceptions.ManagerLoadException;
import exceptions.ManagerSaveException;
import utility.EpicService;
import utility.TasksSaveRestore;
import tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Данный класс после каждой операции автоматически сохраняет все задачи и их состояние в файл. В конструкторе менеджер
 * получает путь файла для автосохранения.
 */
public class FileBackedTasksManager extends InMemoryTaskManager {
    private final Path path;

    public FileBackedTasksManager(String path) {
        super();
        this.path = Paths.get(path);
    }

    @Override
    public void removeAllBasicTasks() {
        super.removeAllBasicTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public BasicTask getBasicTaskById(long basicTaskId) {
        if (basicTaskList.containsKey(basicTaskId)) {
            BasicTask basicTask = basicTaskList.get(basicTaskId);
            historyManager.add(basicTask);
            save();
            return basicTask;
        } else {
            throw new NoSuchElementException("Задачи с id " + basicTaskId + " не существует.");
        }
    }

    @Override
    public Epic getEpicById(long epicId) {
        if (epicList.containsKey(epicId)) {
            Epic epic = epicList.get(epicId);
            historyManager.add(epic);
            save();
            return epic;
        } else {
            throw new NoSuchElementException("Эпика с id " + epicId + " не существует.");
        }
    }

    @Override
    public Subtask getSubtaskById(long subtaskId) {
        if (subtaskList.containsKey(subtaskId)) {
            Subtask subtask = subtaskList.get(subtaskId);
            historyManager.add(subtask);
            save();
            return subtask;
        } else {
            throw new NoSuchElementException("Подзадачи с id " + subtaskId + " не существует.");
        }
    }

    @Override
    public void addBasicTask(BasicTask basicTask) {
        super.addBasicTask(basicTask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateBasicTask(BasicTask basicTask) {
        super.updateBasicTask(basicTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeBasicTaskById(long basicTaskId) {
        super.removeBasicTaskById(basicTaskId);
        save();
    }

    @Override
    public void removeEpicById(long epicId) {
        super.removeEpicById(epicId);
        save();
    }

    @Override
    public void removeSubtaskById(long subtaskId) {
        super.removeSubtaskById(subtaskId);
        save();
    }

    /**
     * Восстановление состояния FileBackedTasksManager из файла
     *
     * @param path файл, в котором хранятся сохраненные данные задач и истории просмотров
     * @return объект FileBackedTasksManager
     */
    public static FileBackedTasksManager loadFromFile(String path) {
        FileBackedTasksManager manager = new FileBackedTasksManager(path);
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            //считываем заголовок и не обрабатываем
            br.readLine();
            //строка с первой задачей
            String task = br.readLine();
            while (!task.isEmpty()) {
                Task restoredTask = Objects.requireNonNull(TasksSaveRestore.stringToTask(task)
                        , "Файл не содержит задач.");
                restoreTask(restoredTask, manager);
                task = br.readLine();
            }
            String history = br.readLine();
            List<Long> historyIds = TasksSaveRestore.historyFromString(history);
            restoreHistory(historyIds, manager);
        } catch (IOException e) {
            System.out.println("Ошибка при попытке считать данные из файла");
            throw new ManagerLoadException(e);
        }
        return manager;
    }

    /**
     * Восстановление задачи, считанной из файла. В зависимости от типа, задача помещается в соответсвующую мапу
     *
     * @param restoredTask задача, считанная из файла
     * @param manager      менеджер, в который требуется сохранить задачу
     */
    private static void restoreTask(Task restoredTask, FileBackedTasksManager manager) {
        long taskId = restoredTask.getTaskId();
        manager.taskId = Math.max(taskId, manager.taskId);
        if (restoredTask instanceof BasicTask) {
            manager.basicTaskList.put(taskId, ((BasicTask) restoredTask));
        } else if (restoredTask instanceof Epic) {
            manager.epicList.put(taskId, (Epic) restoredTask);
        } else if (restoredTask instanceof Subtask) {
            Subtask subtask = (Subtask) restoredTask;
            long subtaskId = subtask.getTaskId();
            manager.subtaskList.put(subtaskId, subtask);
            Epic epic = manager.epicList.get(subtask.getEpicId());
            EpicService.addEpicSubtask(epic, subtaskId);
        }
    }

    /**
     * Восстановление истории просмотров, считанной из файла.
     *
     * @param historyIds список id из истории просмотров
     * @param manager    менеджер, в котором требуется восстановить историю
     */
    private static void restoreHistory(List<Long> historyIds, FileBackedTasksManager manager) {
        for (Long taskId : historyIds) {

            if (manager.basicTaskList.containsKey(taskId)) {
                manager.historyManager.add(manager.basicTaskList.get(taskId));
            } else if (manager.epicList.containsKey(taskId)) {
                manager.historyManager.add(manager.epicList.get(taskId));
            } else {
                manager.historyManager.add(manager.subtaskList.get(taskId));
            }
        }
    }

    /**
     * Сохранение текущего состояния менеджера в файл. Сохраняются все созданные задачи и история просмотров.
     */
    private void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            String header = "id,type,name,description,status,epic\n";
            bw.write(header);
            //проходимся по всем типам задач, преобразуем в строку и записываем в файл
            for (BasicTask basicTask : basicTaskList.values()) {
                bw.write(TasksSaveRestore.taskToString(basicTask));
            }
            for (Epic epic : epicList.values()) {
                bw.write(TasksSaveRestore.taskToString(epic));
            }
            for (Subtask subtask : subtaskList.values()) {
                bw.write(TasksSaveRestore.taskToString(subtask));
            }
            //добавляем пустую строку между задачами и историей
            bw.write("\n");

            bw.write(TasksSaveRestore.historyToString(historyManager));
            // проходимся по истории, считываем id просмотров и записываем в файл

        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла");
            throw new ManagerSaveException(e);
        }
    }

    public static void main(String[] args) {

        FileBackedTasksManager manager = new FileBackedTasksManager("src/resources/test.csv");

        BasicTask task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        manager.addBasicTask(task1);

        Epic epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);

        Subtask subtask1 = Subtask.create("Подзадача 1", "Описание подзадачи 1",
                Status.DONE, epic1.getTaskId());
        manager.addSubtask(subtask1);

        BasicTask task2 = BasicTask.create("Задача 2", "Описание задачи 2", Status.DONE);
        manager.addBasicTask(task2);

        Epic epic2 = Epic.create("Эпик 2", "Описание эпика 2");
        manager.addEpic(epic2);

        Subtask subtask2 = Subtask.create("Подзадача 2", "Описание подзадачи 2",
                Status.NEW, epic2.getTaskId());
        manager.addSubtask(subtask2);
        Subtask subtask3 = Subtask.create("Подзадача 3", "Описание подзадачи 3",
                Status.DONE, epic2.getTaskId());
        manager.addSubtask(subtask3);

        manager.getBasicTaskById(task1.getTaskId());
        manager.getEpicById(epic1.getTaskId());
        manager.getSubtaskById(subtask1.getTaskId());

        // Данный закомментированный код считывает сохраненные данные и печатает восстановленные задачи и историю.
        FileBackedTasksManager manager1 = loadFromFile("src/resources/test.csv");
        System.out.println(manager1.getBasicTaskList());
        System.out.println(manager1.getEpicList());
        System.out.println(manager1.getSubtaskList());
        System.out.println(manager1.getHistory());
    }
}



