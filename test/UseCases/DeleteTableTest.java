package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class DeleteTableTest {
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
    void DeleteTable() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * creert 2 tables, Table2 wordt geselecteerd
                 */
                File file = new File(classLoader.getResource("resources/DeleteTable/DeleteTableStep1en2.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt op DEL geklikt, Table2 wordt verwijderd en de lijst is geupdatet
                 */
                file = new File(classLoader.getResource("resources/DeleteTable/DeleteTableStep3en4.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void DeleteTableAndComputedTables() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * User clicks a table query in a tables subwindow
                 *      cursor bij table query
                 *   Eerst en vooral worden er 2 tables aangemaakt, een stored en een computed
                 *      De stored krijgt een column met als naam Col, type String, blanksAllowed false en default "test"
                 *   Daarna wordt de query van de computedTable (table1) gezet:
                 *      SELECT t.Col AS col FROM Table2 AS t WHERE t.Col = "test"
                 *
                 *  Table1 is dus afhankelijkg van Table2
                 */
                File file = new File(classLoader.getResource("resources/EditTableQuery/EditTableQueryStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt op DEL geklikt, Table2 wordt verwijderd en de lijst is geupdatet
                 *      aangezien Table1 afhankelijk is van Table2, wordt die ook verwijderd
                 */
                file = new File(classLoader.getResource("resources/DeleteTable/DeleteTableStep3en4a.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
