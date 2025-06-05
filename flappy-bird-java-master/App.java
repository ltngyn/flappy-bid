import javax.swing.*;

//implement mouse reader DONE
//collision DONE
//moving DONE
//abstract class and function DONE
//interface DONE
//implement key reader DONE
//making a menu interface in another panel linking to this one DONE
//making score board? DONE
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


