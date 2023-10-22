package manager;

import tasks.BasicTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {
    List<BasicTask> getBasicTaskList();
    List<Epic> getEpicList();
    List<Subtask> getSubtaskList();
    List<Long> getEpicSubtaskList(Epic epic);

    void removeAllBasicTasks();
    void removeAllEpics();
    void removeAllSubtasks();

    BasicTask getBasicTaskById(long basicTaskId);
    Epic getEpicById(long epicId);
    Subtask getSubtaskById(long subtaskId);

    long addBasicTask(BasicTask basicTask);
    long addEpic(Epic epic);
    long addSubtask(Subtask subtask);

    void updateBasicTask(BasicTask basicTask);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    void removeBasicTaskById(long basicTaskId);
    void removeEpicById(long epicId);
    void removeSubtaskById(long subtaskId);

    List<Task> getHistory();
    List<Task> getPrioritizedTasks();

}
