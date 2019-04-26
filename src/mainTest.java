import ui.UIStarter;

public class mainTest {

    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(() -> {
            // Start UI
            UIStarter uiStarter = new UIStarter();
            uiStarter.getCompositor().recordSession("");
        });
    }
}
