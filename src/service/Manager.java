package service;

import model.Epic;

public class Manager {
    private final TaskManager taskManager;
    private final EpicManager epicManager;
    private final SubtaskManager subtaskManager;

    public Manager() {
        taskManager = new TaskManager();
        epicManager = new EpicManager();
        subtaskManager = new SubtaskManager();
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public EpicManager getEpicManager() {
        return epicManager;
    }

    public SubtaskManager getSubtaskManager() {
        return subtaskManager;
    }

    /*
     при удалении всех эпиков удаляются и все подзадачи, поскольку они связаны между собой.
     было бы логично вынести этот метод в класс, у которого есть доступ к менеджерам всех типов задач
     Из минусов, как мне кажется, то, что данная функциональность по сути должна быть внутри
     своих менеджеров. А в этом случае надо изначально создавать общий менеджер с тремя хэшмапами. Но мне такой вариант
     не понравился, поскольку будет много дублирования кода, так как методы для менеджеров эпиков
     и обычных задач в большинстве идентичны. Либо делать длинные методы, в который будет передаваться объект
     родительсокого класса в качестве параметра, внутри определять, через instanceof, к какому классу
     относится этот объект, что негативно влияет на объем и читаемость кода.
     Наставник в чате предложил хранить в эпиках не список подазадач, а список id, но такой вариант хорошо работает,
     когда в проекте один общий менеджер. Либо выносить эти методы в этот класс, как вариант. Но эпику, помимо id, надо
     знать и статус подзадач, поэтому мой вариант мне показался более удобным.
     */
    public void removeAllTasks() {
        taskManager.removeAllTasks();
    }

    public void removeAllEpics() {
        epicManager.removeAllTasks();
        subtaskManager.removeAllTasks();
    }

    public void removeAllSubtasks() {
        subtaskManager.removeAllTasks();

//        в отличие от реализации в менежере подазадач, в данном варианте проходимся по всем эпикам,
//        а не всем подзадачам
        for (Epic epic : epicManager.getTaskList()) {
            epic.removeAllEpicSubtasks();
            epic.checkEpicStatus();
        }
    }


}
