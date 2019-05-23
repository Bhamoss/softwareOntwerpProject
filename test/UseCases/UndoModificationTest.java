package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class UndoModificationTest {
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
    void UndoModification() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * creeert een table
                 */
                File file = new File(classLoader.getResource("resources/CreateTable/CreateTableMSS.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * creert nog enkele tables bij en verwijdert daarna 2 tables
                 *  edit een table name
                 *  verwijdert nog een table
                 */
                file = new File(classLoader.getResource("resources/UndoModification/UndoModificationSETUP.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt op CTRL+Z geklikt tot er geen tables meer zijn
                 */
                file = new File(classLoader.getResource("resources/UndoModification/UndoModificationUndo.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
