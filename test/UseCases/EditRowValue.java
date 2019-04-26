package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class AddColumnTest {
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
    void AddColumn() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("resources/AddRow/Setup.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                file = new File(classLoader.getResource("resources/AddRow/AddRow.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                file = new File(classLoader.getResource("resources/EditRowValue/EditRowValue.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
