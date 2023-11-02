package manager;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.LocalDateTimeAdapter;
import http.TaskTypeAdapter;
import tasks.Task;

import java.time.LocalDateTime;

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
        return new FileBackedTasksManager("resources/FBTM.csv");
    }

    public static TaskManager getHttpTaskManager() {
        return new HttpTaskManager("http://localhost:8078/");
    }

    /**
     * Данный метод возвращает менеджер истории
     * @return менеджер истории
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    /**
     * Получение объекта gson с требуемой конфигурацией
     * @return объект класса Gson
     */
    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Task.class, new TaskTypeAdapter())
                .create();
    }


}
