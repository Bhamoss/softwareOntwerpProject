package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class DeleteColumnTest {
    static UIStarter uiStarter;
    @BeforeEach
    void setUp() {
        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                // Start UI
                uiStarter = new UIStarter();
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void DeleteColumn() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * een row toevoegen, met bepaalde default values
                 */
                File file = new File(classLoader.getResource("resources/AddRow/AddRow.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
