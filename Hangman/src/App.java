import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame main = new JFrame("Hangman");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(400, 700);
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setLayout(new BoxLayout(main.getContentPane(), BoxLayout.Y_AXIS));

        GUI gui = new GUI();
        main.add(gui);

        //main.pack();
        main.setVisible(true);

    }
}
