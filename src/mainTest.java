import ui.CanvasWindow;
import ui.UIStarter;

import java.lang.reflect.InvocationTargetException;

public class mainTest {

    static UIStarter uiStarter;
    public static void main(String[] args){
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

        try {
//            java.awt.EventQueue.invokeAndWait(() -> {
//                //     Start UI
//                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
//                        "EditTableQuery/EditTableQueryStep1.txt", uiStarter.getCompositor());
//
//            });
//            java.awt.EventQueue.invokeAndWait(() -> {
//                // Start UI
//                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
//                        "EditTableName/EditTableNameStep1en2.txt", uiStarter.getCompositor());
//
//            });
            java.awt.EventQueue.invokeAndWait(() -> {
                // Start UI
                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
                        "CreateTable/CreateTableMSS.txt", uiStarter.getCompositor());

            });
            java.awt.EventQueue.invokeAndWait(() -> {
                // Start UI
                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
                        "UndoModification/UndoModificationSETUP.txt", uiStarter.getCompositor());

            });
            java.awt.EventQueue.invokeAndWait(() -> {
                // Start UI
                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
                        "UndoModification/UndoModificationUndo.txt", uiStarter.getCompositor());

            });
            java.awt.EventQueue.invokeAndWait(() -> {
                // Start UI
                uiStarter.getCompositor().recordSession("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
                        "RedoModification/RedoModificationRedo.txt");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
