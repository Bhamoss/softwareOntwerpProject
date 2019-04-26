package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class EditTableNameTest {
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
    void EditTableName() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * User clicks a table name in a tables subwindow
                 *      cursor bij table name
                 */
                File file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * het aanpassen van de tableName
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS2.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt op Enter geduwd, cursor verdwijnt en naam is aangepast
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS3.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void EditTableNameEscPressed() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * User clicks a table name in a tables subwindow
                 *      cursor bij table name
                 */
                File file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * het aanpassen van de tableName
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS2.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt op ESC geduwd, cursor verdwijnt en naam is gereset
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS5a.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void EditTableNameTableNameInvalid() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * User clicks a table name in a tables subwindow
                 *      cursor bij table name
                 */
                File file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * het aanpassen van de tableName
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS2.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt een invalid name ingegeven, rood boordje rond widget
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameMSS5a.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
