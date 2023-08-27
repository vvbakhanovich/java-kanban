package manager;

import tasks.Task;

import java.util.List;

/**
 *В этом спринте возможности трекера ограничены — в истории просмотров допускается дублирование и она может содержать
 *  только десять задач. В следующем спринте нужно будет убрать дубли и расширить её размер. Для подготовки создаем
 *  данный интерфейс
 */

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();

}
