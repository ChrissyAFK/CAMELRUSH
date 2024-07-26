import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Camel Rush");
        frame.setSize(600,600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setBackground(Color.blue);
        
        Display display = new Display();
        frame.add(display);

        frame.setVisible(true);
    }
}

class Display extends JPanel {
	Image camel = new ImageIcon("idle_animation (40x32).png").getImage();
	//Timer timer;
	
	Display() {
		this.setPreferredSize(new Dimension(600,600));
		setLayout(null);
		//this.timer = new Timer(10,e->repaint());
		//this.timer.start();
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(this.camel,0,0,this.getWidth(),this.getHeight(),this);
		System.out.println("Drew image");
	}
}