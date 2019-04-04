import window.UIWindowHandler;


public class Main{
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // create the UIWindowHandler
            UIWindowHandler ui = new UIWindowHandler();
            ui.show();
            // start the application by loading a window in table-mode
            ui.loadTablesWindow();
        });
    }
}