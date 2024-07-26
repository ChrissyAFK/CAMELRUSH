import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
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
        //frame.setBackground(Color.blue);
        
        Display display = new Display();
        frame.add(display);

        frame.setVisible(true);
    }
}

class Display extends JPanel {
	Image camel1 = new ImageIcon("CAMELRUSH/assets/player/camelIdle.png").getImage();
	Image camel2 = new ImageIcon("CAMELRUSH/assets/player/walking_animation (40x32).png").getImage();
	int currentCamel = 0;
	BufferedImage camelAnimation1 = toBufferedImage(camel1).getSubimage(currentCamel*40,0,40,32);
	BufferedImage camelAnimation2 = toBufferedImage(camel2).getSubimage(currentCamel*40,0,40,32);
	Timer animateCamel;
	
	Display() {
		this.setPreferredSize(new Dimension(600,600));
		setLayout(null); //40x32
		this.animateCamel = new Timer((1000/15),e->changeCamelAnimation());
		this.animateCamel.start();
	}
	
	private void changeCamelAnimation() {
		if (currentCamel==14) {
			this.currentCamel=0;
		} else {
			this.currentCamel++;
		}
		this.camelAnimation1 = toBufferedImage(this.camel1).getSubimage(currentCamel*40,0,40,32);
		this.camelAnimation2 = toBufferedImage(this.camel2).getSubimage(currentCamel*40,0,40,32);
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0,0,this.getWidth(),this.getHeight());
		g.drawImage(this.camelAnimation1,0,0,this.getWidth()/2,this.getHeight()/2,this);
		g.drawImage(this.camelAnimation2,this.getWidth()/2,0,this.getWidth()/2,this.getHeight()/2,this);
	}
	
	public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}