package tasks;

import java.time.LocalDateTime;

public class BasicTask extends Task {

    private final TaskTypes taskType = TaskTypes.BASIC_TASK;

    /*
     если Вы советуете убрать фабрики и сделать конструкторы public, то поменять несложно. Я читал, что когда в классе
     несколько конструкторов, то для читаемости, наоборот, лучше фабрики использовать, так как порядок параметров и их
     количество в конструкторе может привести к ошибкам из-за невнимательности. А у фабрик можно задать имя, которое
     дает подсказку какой объект должен создаться. Может как-то можно уменьшить количество кода? Но на данный момент
     все конструкторы нужны: конструктор для задачи без указания даты (теоретически можно от него избавиться, но тогда
     придется как-то явно указывать на отсутствие даты старта), конструктор с датой старта и два конструктора, которые
     используются при восстановлении задачи из строки.
     */
    private BasicTask(String taskName, String description, Status status) {
        super(taskName, description, status);
    }

    private BasicTask(String taskName, String description, String startTime, long duration, Status status) {
        super(taskName, description, startTime, duration, status);
    }

    private BasicTask(String taskName, String description, LocalDateTime startTime, long duration, Status status) {
        super(taskName, description, startTime, duration, status);
    }

    private BasicTask(long taskId, String taskName, String description, String startTime, long duration, Status status) {
        super(taskId, taskName, description, startTime, duration, status);
    }

    private BasicTask(long taskId, String taskName, String description, LocalDateTime startTime, long duration, Status status) {
        super(taskId, taskName, description, startTime, duration, status);
    }

    public static BasicTask create(String taskName, String description, Status status) {
        return new BasicTask(taskName, description, status);
    }

    public static BasicTask createWithStartTime(String taskName, String description, LocalDateTime startTime, long duration,
                                                Status status) {
        return new BasicTask(taskName, description, startTime, duration, status);
    }

    public static BasicTask createFromFileWithStartTime(long taskId, String taskName, String description,
                                                        LocalDateTime startTime, long duration, Status status) {
        return new BasicTask(taskId, taskName, description, startTime, duration, status);
    }

    @Override
    public TaskTypes getTaskType() {
        return taskType;
    }

    @Override
    public String toString() {
        return "BasicTask{" +
                "taskId='" + taskId + '\'' +
                ", taskId=" + taskName +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", status=" + status +
                '}';
    }
}
