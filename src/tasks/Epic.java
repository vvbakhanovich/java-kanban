package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    // у каждого эпика есть свой список подзадач
    private final List<Long> subtaskList;

    private Epic(String taskName, String description) {
        super(taskName, description, Status.NEW);
        subtaskList = new ArrayList<>();
    }

    public static Epic create(String taskName, String description) {
        return new Epic(taskName, description);
    }

    // возвращает копию списка подзадач
    public List<Long> getSubtaskList() {
        return subtaskList;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "numberOfSubtasks=" + subtaskList.size() +
                ", taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
