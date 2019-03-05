import window.testWindow;
import be.kuleuven.cs.som.*;


public class Main{
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new testWindow("My Canvas Window").show();
        });
    }
}