package manager;

import java.util.ArrayList;
import java.util.HashMap;

public class Managers {
    private TaskManager manager;

    public TaskManager getDefault() {
        return new InMemoryTaskManager(new HashMap<>(), new HashMap<>(), new HashMap<>(), new ArrayList<>());
    }
}
