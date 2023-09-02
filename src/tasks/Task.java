package tasks;

import java.util.Objects;

public abstract class Task{
    protected String taskName;
    protected long taskId;
    protected String description;
    protected Status status;

    /**
     * Создает новую задачу
     * @param taskName имя задачи
     * @param description описание задачи
     * @param status статус задачи
     */
    protected Task(String taskName, String description, Status status) {
        this.taskName = taskName;
        this.description = description;
        this.status = status;
    }

    /**
     * Создает новую задачу на основе переданной в качестве параметра. Также присваивает новой задаче переданный id
     * @param task задача, на основе которой создается новая
     * @param taskId идентификатор, который необходимо присвоить новой задаче
     */
    protected Task(Task task, long taskId) {
        this.taskName = task.getTaskName();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.taskId =  taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskId=" + taskId +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
