import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

public class IMTMTest extends AbstractTaskManagerTest {

    @Override
    @BeforeEach
    void setManager() {
        manager = new InMemoryTaskManager();
    }
}
