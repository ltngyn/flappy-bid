import javax.swing.*;

//implement mouse reader
//implement key reader DONE
//making a menu interface in another panel linking to this one
//making score board?
//gib coin after 5 score? coin to unlock new bird? store?

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Menu(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


