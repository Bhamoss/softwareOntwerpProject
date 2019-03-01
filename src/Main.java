import window.testWindow;

public class Main{
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new testWindow("My Canvas Window", 900, 600).show();
        });
    }
}