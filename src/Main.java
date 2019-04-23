import ui.UIStarter;


public class Main{
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // Start UI
            new UIStarter();
            /*
             *
             * Difference repaint VS load
             * - repaint: paints the existing columnWidgets, without asking for new ones
             *
             * - load/rebuild: constructs all new columnWidgets with information from the tablesHandler and paint them
             *
             */
        });
    }
}