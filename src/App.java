import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Camel Rush");
        frame.setSize(600,600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
