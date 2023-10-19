package manager;


/**
 * Со временем в приложении трекера появится несколько реализаций интерфейса TaskManager. На данном классе будет лежать
 * вся ответственность за создание менеджера задач. Класс не предназначен для наследования
 */
public final class Managers {

    /**
     * Запрет на создание объектов утилитарного класса
      */
    private Managers() {}

    /**
     * Данный метод возвращает объект-менеджер. При вызове конструктора есть возможность выбрать реализацию
     * HistoryManager. Так как данный менеджер default, он также использует в качестве HistoryManager
     * defaultHistoryManager.
     *
     * @return объект-менеджер
     */
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static TaskManager getFileManager() {
        return new FileBackedTasksManager("src/resources/test2.csv");
    }

    /**
     * Данный метод возвращает менеджер истории
     * @return менеджер истории
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}
