import ui.UIWindowHandler;


public class Main{
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // create the UIWindowHandler
            UIWindowHandler ui = new UIWindowHandler();
            /*
             *
             * Difference repaint VS load
             * - repaint: paints the existing widgets, without asking for new ones
             *
             * - load/rebuild: constructs all new widgets with information from the tablesHandler and paint them
             *
             */
        });
    }
}