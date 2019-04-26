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

//        try {
//            java.awt.EventQueue.invokeAndWait(() -> {
//                // Start UI
//                CanvasWindow.replayRecording("C:\\Users\\michi\\Desktop\\AddRow\\Setup.txt", uiStarter.getCompositor());
//                CanvasWindow.replayRecording("C:\\Users\\michi\\Desktop\\AddRow\\AddRow.txt", uiStarter.getCompositor());
//
//            });
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        try {
            java.awt.EventQueue.invokeAndWait(() -> {
                // Start UI
                uiStarter.getCompositor().recordSession("C:\\Users\\michi\\Desktop\\EditRowValue\\EditRowValue.txt");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
