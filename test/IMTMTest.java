import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IMTMTest extends AbstractTaskManagerTest {

    @Override
    @BeforeEach
    void setManager() {
        manager = new InMemoryTaskManager();
    }
}
