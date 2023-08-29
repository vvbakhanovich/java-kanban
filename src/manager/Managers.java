package manager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Со временем в приложении трекера появится несколько реализаций интерфейса TaskManager. На данном классе будет лежать
 * вся ответственность за создание менеджера задач.
 */
public class Managers {

    /**
     * Данный метод возвращает объект-менеджер. При вызове конструктора есть возможность выбрать реализацию
     * HistoryManager. Так как данный менеджер default, он также использует в качестве HistoryManager
     * defaultHistoryManager.
     *
     * @return объект-менеджер
     */
    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    /**
     * Данный метод возвращает менеджер истории
     * @return менеджер истории
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}
