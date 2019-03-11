import window.UIWindowHandler;


public class Main{
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            UIWindowHandler ui = new UIWindowHandler();
            ui.show();
            ui.loadTablesWindow();
        });
    }
}