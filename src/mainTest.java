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
//            java.awt.EventQueue.invokeAndWait(() -> {
//                // Start UI
//                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/" +
//                        "testUseCaseResources/OpenTable/OpenTableAddSomeColumns.txt", uiStarter.getCompositor());
//            });
//            java.awt.EventQueue.invokeAndWait(() -> {
//                // Start UI
//                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/" +
//                        "testUseCaseResources/EditTableName/EditTableNameStep5.txt", uiStarter.getCompositor());
//
//            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                // Start UI
                uiStarter.getCompositor().recordSession("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
                        "UndoModification/UndoModificationUndo.txt");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
