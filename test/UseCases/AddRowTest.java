package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class AddRowTest {
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
    void AddRowDoubleClick() {

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
                 * Er wordt een rows subwindow aangemaakt
                 *  daarin worden 4 rows toegevoegd door double te klikken op de AddRow button
                 */
                file = new File(classLoader.getResource("resources/AddRow/AddRowDoubleClick.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    @Test
    void AddRowCTRLN() {

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
                 * Er wordt een rows subwindow aangemaakt
                 *  daarin worden 4 rows toegevoegd door double te klikken op de AddRow button
                 */
                file = new File(classLoader.getResource("resources/AddRow/AddRowDoubleClick.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * Er wordt een form subwindow gemaakt
                 *  daarin wordt 3 keer op ctrl+N geklikt, er worden 3 rows toegevoegd
                 *      zichtbaar in de openstaande rows subwindow
                 */
                file = new File(classLoader.getResource("resources/AddRow/AddRowCTRLN.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
