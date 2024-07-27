import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player {
	private int[] coordinates = {0,0};
	private boolean inSun = false;
	private boolean inMotion = false;
	private int camelAnimation = 0;
	private BufferedImage idleCamel;
	private BufferedImage walkingCamel;
	
	Player() throws IOException {
		this.idleCamel = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_idle_animation (40x32).png").getImage());
        this.walkingCamel = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_walking_animation (40x32).png").getImage());
	}
	
	public void changeCamelAnimation() {
		if (camelAnimation==14) {
			this.camelAnimation=0;
		} else {
			this.camelAnimation++;
		}
	}
	
	public BufferedImage getCurrentAnimation() {
		if (inMotion) {
			return this.walkingCamel.getSubimage(this.camelAnimation*40,0,40,32);
		} else {
			return this.idleCamel.getSubimage(this.camelAnimation*40,0,40,32);
		}
	}
	
	private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}
