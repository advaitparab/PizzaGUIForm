import javax.swing.*;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PizzaGUIFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);  // You can adjust the size
            frame.setVisible(true);
        });
    }
}
