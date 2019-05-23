package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class EditTableQueryTest {
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
    void EditTableQuery() {

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
                 *   Vervolgens worden van beide tables de tableRowMode geopend en zien we dat bij het
                 *      toevoegen van een row bij Table2, er ook een row wordt toegevoegd bij Table1
                 *      Als er een value verandert van "test" naar iets anders, dan wordt die row bij Table1 verwijderd
                 */
                File file = new File(classLoader.getResource("resources/EditTableQuery/EditTableQueryStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            System.out.println("EditTableName test");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("EditTableName test");

            e.printStackTrace();
        }
    }

    @Test
    void EditTableQueryInvalid() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * User clicks a table query in a tables subwindow
                 *      cursor bij table query
                 *   Eerst en vooral worden er 2 tables aangemaakt, een stored en een computed
                 *      De stored krijgt een column met als naam Col, type String, blanksAllowed false en default "test"
                 *   Daarna wordt de query van de computedTable (table1) gezet met een aantal invalid querys, dit lukt niet
                 *      invalid query's:
                 *          Select ... (in kleine letter ipv hoofdletters waar nodig)
                 *          SELECT t.Col AS t FROM Table AS t WHERE ... (Table bestaat niet, het moet Table2 zijn)
                 */
                File file = new File(classLoader.getResource("resources/EditTableQuery/EditTableQueryStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

                file = new File(classLoader.getResource("resources/EditTableQuery/EditTableQueryStepInvalid.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            System.out.println("EditTableName test");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("EditTableName test");

            e.printStackTrace();
        }
    }

}
