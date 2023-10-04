package manager;

import tasks.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
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
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
    }

    @Override
    public BasicTask getBasicTaskById(long basicTaskId) {
        return super.getBasicTaskById(basicTaskId);
    }

    @Override
    public Epic getEpicById(long epicId) {
        return super.getEpicById(epicId);
    }

    @Override
    public Subtask getSubtaskById(long subtaskId) {
        return super.getSubtaskById(subtaskId);
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
    }

    @Override
    public void updateEpic(Epic epic) throws NoSuchElementException {
        super.updateEpic(epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) throws NoSuchElementException {
        super.updateSubtask(subtask);
    }

    @Override
    public void removeBasicTaskById(long basicTaskId) throws NoSuchElementException {
        super.removeBasicTaskById(basicTaskId);
    }

    @Override
    public void removeEpicById(long epicId) throws NoSuchElementException {
        super.removeEpicById(epicId);
    }

    @Override
    public void removeSubtaskById(long subtaskId) throws NoSuchElementException {
        super.removeSubtaskById(subtaskId);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            String header = "id,type,name,description,status,epic\n";
            bw.write(header);
            //проходимся по всем типам задач, преобразуем в строку и записываем в файл
            for (BasicTask basicTask : basicTaskList.values()) {
                bw.write(toString(basicTask));
            }
            for (Epic epic : epicList.values()) {
                bw.write(toString(epic));
            }
            for (Subtask subtask : subtaskList.values()) {
                bw.write(toString(subtask));
            }
            //добавляем пустую строку между задачами и историей
            bw.write("\n");

            // проходимся по истории, считываем айдишники просмотров и записываем в файл

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected long generateId() {
        return super.generateId();
    }

    @Override
    protected void removeTasksFromHistory(Collection<Long> keySet) {
        super.removeTasksFromHistory(keySet);
    }

    private String toString(Task task) {

        if (task instanceof BasicTask) {
            return String.format("%d,%s,%s,%s,%s\n", task.getTaskId(), task.getTaskType(), task.getTaskName(),
                    task.getDescription(), task.getStatus());
        } else if (task instanceof Epic) {
            return String.format("%d,%s,%s,%s,%s\n", task.getTaskId(), task.getTaskType(), task.getTaskName(),
                    task.getDescription(), task.getStatus());
        } else if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            return String.format("%d,%s,%s,%s,%s,%s\n", subtask.getTaskId(), subtask.getTaskType(), subtask.getTaskName(),
                    subtask.getDescription(), subtask.getStatus(), subtask.getEpicId());
        }
        return null;
    }
    public static void main(String[] args) {

        FileBackedTasksManager manager = new FileBackedTasksManager("test.csv");
        BasicTask task1 = BasicTask.create("Задача 1", "Описание задачи 1", Status.NEW);
        manager.addBasicTask(task1);

        Epic epic1 = Epic.create("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);

        Subtask subtask1 = Subtask.create("Подзадача 1", "Описание подзадачи 1",
                Status.NEW, epic1.getTaskId());
        manager.addSubtask(subtask1);
    }
}



