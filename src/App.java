import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Camel Rush");
        frame.setSize(600,600);
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

class Display extends JPanel {
	Timer animateCamel;
	InputHandler input;
	Player player;
	
	Display(InputHandler input,Player player) {
		this.setPreferredSize(new Dimension(600,600));
		setLayout(null); //40x32
		this.animateCamel = new Timer((1000/15),e->animate());
		this.animateCamel.start();
		this.input = input;
		this.player = player;
	}
	
	private void animate() {
		if (this.input.wKeyPressed()) {
			Player.changeYcoord(3);
		}
		if (this.input.aKeyPressed()) {
			Player.changeXcoord(-3);
		}
		if (this.input.sKeyPressed()) {
			Player.changeYcoord(-3);
		}
		if (this.input.dKeyPressed()) {
			Player.changeXcoord(3);
		}
		if (this.input.inMotion()) {
			Player.isMoving();
		} else {
			Player.isNotMoving();
		}
		this.player.changeCamelAnimation();
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(Color.blue);
		g.fillRect(0,0,600,600);
		g.drawImage(this.player.getCurrentAnimation(),100+Player.getCoordinates()[0],100-Player.getCoordinates()[1],220,160,this);
	}
}