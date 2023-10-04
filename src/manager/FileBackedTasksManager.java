package manager;

import service.TasksSaveRestore;
import tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final String path;

    public FileBackedTasksManager(String path) {
        super();
        this.path = path;
    }

    @Override
    public List<BasicTask> getBasicTaskList() {
        return super.getBasicTaskList();
    }

    @Override
    public List<Epic> getEpicList() {
        return super.getEpicList();
    }

    @Override
    public List<Subtask> getSubtaskList() {
        return super.getSubtaskList();
    }

    @Override
    public List<Long> getEpicSubtaskList(Epic epic) {
        return super.getEpicSubtaskList(epic);
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
    public BasicTask getBasicTaskById(long basicTaskId) throws NoSuchElementException{
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
    public Epic getEpicById(long epicId) throws NoSuchElementException{
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
    public Subtask getSubtaskById(long subtaskId) throws NoSuchElementException{
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
    public void addSubtask(Subtask subtask) throws NoSuchElementException {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateBasicTask(BasicTask basicTask) throws NoSuchElementException {
        super.updateBasicTask(basicTask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws NoSuchElementException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) throws NoSuchElementException {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeBasicTaskById(long basicTaskId) throws NoSuchElementException {
        super.removeBasicTaskById(basicTaskId);
        save();
    }

    @Override
    public void removeEpicById(long epicId) throws NoSuchElementException {
        super.removeEpicById(epicId);
        save();
    }

    @Override
    public void removeSubtaskById(long subtaskId) throws NoSuchElementException {
        super.removeSubtaskById(subtaskId);
        save();
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    @Override
    protected long generateId() {
        return super.generateId();
    }

    @Override
    protected void removeTasksFromHistory(Collection<Long> keySet) {
        super.removeTasksFromHistory(keySet);
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
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
            // проходимся по истории, считываем айдишники просмотров и записываем в файл

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileBackedTasksManager restoreFromFile(File file) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager(file.getPath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            //считываем заголовок и не обрабатываем
            br.readLine();
            String task = br.readLine();
            while(!task.isEmpty()) {
                try {
                    Task restoredTask = TasksSaveRestore.stringToTask(task);
                    if (restoredTask instanceof BasicTask) {
                        manager.addBasicTask((BasicTask) restoredTask);
                    } else if (restoredTask instanceof Epic) {
                        manager.addEpic((Epic) restoredTask);
                    } else if (restoredTask instanceof Subtask) {
                        manager.addSubtask((Subtask) restoredTask);
                    }
                } catch (NoSuchFieldException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return manager;
    }

    public String getPath() {
        return path;
    }

    public static void main(String[] args) throws IOException {

        FileBackedTasksManager manager = new FileBackedTasksManager("test.csv");

//            FileBackedTasksManager manager = restoreFromFile(new File("test.csv"));
//        System.out.println(manager.getBasicTaskList());
//        System.out.println(manager.getBasicTaskList());
//        System.out.println(manager.getBasicTaskList());
        BasicTask task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        manager.addBasicTask(task1);

        Epic epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);

        Subtask subtask1 = Subtask.create("Подзадача 1", "Описание подзадачи 1",
                Status.NEW, epic1.getTaskId());
        manager.addSubtask(subtask1);

        manager.getBasicTaskById(task1.getTaskId());
        manager.getEpicById(epic1.getTaskId());
        manager.getSubtaskById(subtask1.getTaskId());
//
//        try {
//            System.out.println(Files.readString(Path.of(manager.getPath())));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}



