package manager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Со временем в приложении трекера появится несколько реализаций интерфейса TaskManager. На данном классе будет лежать
 * вся ответственность за создание менеджера задач.
 */
public class Managers {

    /**
     * Данный метод возвращает объект-менеджер
     * @return объект-менеджер
     */
    public static TaskManager getDefault() {
        return new InMemoryTaskManager(new HashMap<>(), new HashMap<>(), new HashMap<>(), getDefaultHistory());
    }

    /**
     * Данный метод возвращает менеджер истории
     * @return менеджер истории
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager(new ArrayList<>());
    }


}
