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
                 * de setup voor de testcase: 2 tables
                 *  Table2 heeft een column Col
                 *  Table1 heeft een query die refereert naar Table2 en Col van die table
                 */
                File file = new File(classLoader.getResource("resources/EditTableName/EditTableNameSETUP.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * User clicks a table name in a tables subwindow
                 *      cursor bij table name
                 *   stappen 1 en 2
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep1en2.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * het aanpassen van de tableName
                 *  stappen 3 en 4
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep3en4.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt op Enter geduwd, cursor verdwijnt en naam is aangepast
                 *  of er wordt ergens buiten de tablename geklikt, de cursor verdwijnt en de naam is aangepast
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep5.txt").getFile());
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
    void EditTableNameEscPressed() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * de setup voor de testcase: 2 tables
                 *  Table2 heeft een column Col
                 *  Table1 heeft een query die refereert naar Table2 en Col van die table
                 */
                File file = new File(classLoader.getResource("resources/EditTableName/EditTableNameSETUP.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * User clicks a table name in a tables subwindow
                 *      cursor bij table name
                 *   stappen 1 en 2
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep1en2.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * het aanpassen van de tableName
                 *  stappen 3 en 4
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep3en4.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt op ESC geduwd, cursor verdwijnt en naam is gereset
                 *  stap 5a
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep5a.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            System.out.println("EditTableNameESC test");

            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("EditTableNameESC test");

            e.printStackTrace();
        }
    }

    @Test
    void EditTableNameTableNameInvalid() {

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                ClassLoader classLoader = getClass().getClassLoader();
                /**
                 * de setup voor de testcase: 2 tables
                 *  Table2 heeft een column Col
                 *  Table1 heeft een query die refereert naar Table2 en Col van die table
                 */
                File file = new File(classLoader.getResource("resources/EditTableName/EditTableNameSETUP.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * User clicks a table name in a tables subwindow
                 *      cursor bij table name
                 *   stappen 1 en 2
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep1en2.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * het aanpassen van de tableName van table1
                 *  stappen 3 en 4
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep3en4.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * er wordt een invalid name ingegeven, rood boordje rond widget, er kan niet op enter geklikt worden
                 *  tot de name valid is
                 *      stap 6a
                 */
                file = new File(classLoader.getResource("resources/EditTableName/EditTableNameStep6a.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());

            });
        } catch (InterruptedException e) {
            System.out.println("EditTableNameInValid test");

            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("EditTableNameInValid test");

            e.printStackTrace();
        }
    }
}
