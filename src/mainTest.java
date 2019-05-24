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
                //     Start UI
                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
                        "EditTableQuery/EditTableQueryStep1.txt", uiStarter.getCompositor());

            });
//            java.awt.EventQueue.invokeAndWait(() -> {
//                // Start UI
//                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
//                        "EditTableName/EditTableNameStep1en2.txt", uiStarter.getCompositor());
//
//            });
//            java.awt.EventQueue.invokeAndWait(() -> {
//                // Start UI
//                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
//                        "AddColumn/AddColumnSETUP.txt", uiStarter.getCompositor());
//
//            });
//            java.awt.EventQueue.invokeAndWait(() -> {
//                // Start UI
//                CanvasWindow.replayRecording("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
//                        "AddColumn/AddColumnStep1.txt", uiStarter.getCompositor());
//
//            });
            java.awt.EventQueue.invokeAndWait(() -> {
                // Start UI
                uiStarter.getCompositor().recordSession("/home/m/Documents/KULeuven/Fase_3/swop/softwareOntwerpProject/test/resources/" +
                        "DeleteColumn/DeleteColumnStep4a.txt");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
