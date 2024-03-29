package UseCases;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CanvasWindow;
import ui.UIStarter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class DeleteRowTest {
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
    void DeleteRow() {

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
                 * Er wordt een extra row subwindow aangemaakt
                 * De eerste row wordt geedit in tables row subwindow
                 *  in beide subwindows is de nieuwe value zichtbaar
                 */
                file = new File(classLoader.getResource("resources/EditRowValue/EditRowRowMode.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * De enkele rows worden verwijderd, ook zichtbaar in beide subwindows
                 */
                file = new File(classLoader.getResource("resources/DeleteRow/DeleteRowStep1.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void DeleteRowCTRLD() {

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
                 * Er wordt een form  subwindow aangemaakt
                 * De eerste row wordt geedit in form subwindow
                 *  in beide subwindows is de nieuwe value zichtbaar
                 */
                file = new File(classLoader.getResource("resources/EditRowValue/EditRowFormMode.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
                /**
                 * De eerste row wordt verwijderd, ook zichtbaar in beide subwindows
                 *  row wordt in form subwindow verwijderd mbv CTRL+D
                 */
                file = new File(classLoader.getResource("resources/DeleteRow/DeleteRowStep1a.txt").getFile());
                CanvasWindow.replayRecording(file.getAbsolutePath(),uiStarter.getCompositor());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
