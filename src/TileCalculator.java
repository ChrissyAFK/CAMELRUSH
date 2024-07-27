import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

public class TileCalculator {
	private long seed;
	private BufferedImage tile;
	
	TileCalculator(long seed) {
		this.seed = seed;
		this.tile = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/sandtile.png").getImage());
	}
	
	public BufferedImage getTile() {
		return this.tile;
	}
	
	private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img,0,0,null);
        bGr.dispose();

        return bimage;
    }
}