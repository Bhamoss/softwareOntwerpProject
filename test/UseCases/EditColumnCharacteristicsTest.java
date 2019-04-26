package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class EditColumnCharacteristicsTest {
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
    void OpenTable() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * table maken, dan column maken en dan de naam bijwerken
                 */
                File file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnCharacteristicsMSS.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * table maken, dan column maken en dan door de types scrollen
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnCharacteristicsA.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

                /**
                 * table maken, dan column maken dan een paar default values invullen en dan door de types scrollen
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnCharacteristicsA2.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

                /**
                 * table maken, dan column maken dan blanks selecteren en deselecteren
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnCharacteristicsB.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

                /**
                 * table maken, dan column maken dan een paar default values invullen
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnCharacteristicsC.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
