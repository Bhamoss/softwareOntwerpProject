package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class OpenTableTest {
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
                 * double click op een table name, die nog geen columns heeft
                 *      een nieuw table design subwindow wordt gemaakt voor de aangeklikte table
                 */
                File file = new File(classLoader.getResource("resources/OpenTable/OpenTableDesign.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * Een paar columns worden toegevoegd aan de table (Table2)
                 */
                file = new File(classLoader.getResource("resources/OpenTable/OpenTableAddSomeColumns.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * Opnieuw wordt er dubbel geklikt op dezelfde table name, nu heeft die wel al columns
                 *      een nieuw table rows subwindow wordt gemaakt
                 */
                file = new File(classLoader.getResource("resources/OpenTable/OpenTableRow.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void OpenTableForm() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * double click op een table name, die nog geen columns heeft
                 *      een nieuw table design subwindow wordt gemaakt voor de aangeklikte table
                 */
                File file = new File(classLoader.getResource("resources/OpenTable/OpenTableDesign.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * Een paar columns worden toegevoegd aan de table (Table2)
                 */
                file = new File(classLoader.getResource("resources/OpenTable/OpenTableAddSomeColumns.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * Table2 wordt geselecteerd en er wordt op CTRL + F geklikt
                 *  een nieuw Form subwindow wordt gemaakt
                 */
                file = new File(classLoader.getResource("resources/OpenTable/OpenTableForm.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
