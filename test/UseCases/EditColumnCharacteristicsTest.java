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
    void EditColumnNameValid() {

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
                 * Column name wordt aangepast
                 *      eerst naar een lege string, invalid
                 *      daarna naar Column5, valid
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnName/EditColumnNameStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Test
    void EditColumnNameInValid() {

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
                /**
                 * Naar designMode subwindow gaan
                 *      column Col proberen de naam aan te passen, onmogelijk want
                 *      Table1 verwijst naar die column
                 *      enkel de oude naam is valid
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnName/EditColumnNameStep2a.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void EditColumnType() {

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
                 * Column type wordt aangepast
                 *      eerst met een blank default value --> alle types zijn valid
                 *      daarna een string in blank default --> enkel String type is valid,
                 *          de rest wordt rood en moet je doorklikken tot String type om iets anders aan te passen
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnType/EditColumnTypeStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void EditColumnBlanksAllowed() {

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
                 * Column blanksallowed wordt aangepast
                 *      eerst proberen het uit te zetten --> kan niet want default value is blank
                 *      daarna default value aangepast en blanks allowed kan nu wel uitgezet worden
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnBlanksAllowed/EditColumnBlanksStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void EditColumnDV() {

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
                 * Column default value wordt aangepast
                 *      Er wordt 1 extra columns toegevoegd
                 *          elke column krijgt een verschillend type (email wordt hier niet getest, de computer waarop
                 *              de tests uitgevoerd werden kon de @ niet geregistreerd worden door de applicatie)
                 *      De default value van elke column wordt aangepast
                 */
                file = new File(classLoader.getResource("resources/EditColumnCharacteristics/EditColumnDV/EditColumnDVStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
