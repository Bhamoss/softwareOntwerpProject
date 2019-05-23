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
                 * table wordt aangemaakt en er wordt naar de designmode subwindow gegaan
                 */
                File file = new File(classLoader.getResource("resources/AddColumn/AddColumnSETUP.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

                /**
                 * Er wordt een nieuwe column toegevoegd
                 */
                file = new File(classLoader.getResource("resources/AddColumn/AddColumnStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * Eerst en vooral wordt er nog eenzelfde designMode subwindow gecreeerd
                 * De kolom wordt geselecteerd en er wordt op DEL geklikt
                 *  de kolom wordt verwijdert uit de lijst columns, in beide subwindows
                 */
                file = new File(classLoader.getResource("resources/DeleteColumn/DeleteColumnStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void DeleteColumnAndTables() {

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
                 * Er wordt een designMode window aangemaakt voor Table2 en een tableswindow ook
                 *      bij het verwijderen van column Col van Table2 moet Table1 verwijdert worden
                 *      want die is afhankelijk van die column
                 *          hierdoor wordt ook het rows subwindow van Table1 verwijderd
                 */
                file = new File(classLoader.getResource("resources/DeleteColumn/DeleteColumnStep4a.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
