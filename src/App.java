import javax.swing.JFrame;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Camel Rush");
        frame.setSize(1280,720);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        InputHandler input = new InputHandler();
        input.requestFocus();
        frame.add(input);
        
        Player player = new Player();
        
        Display display = new Display(input,player);
        frame.add(display);

        frame.setVisible(true);
    }
}