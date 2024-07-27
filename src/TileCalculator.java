import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class TileCalculator {
	private long seed;
	private int frameWidth;
	private BufferedImage tile;
	
	
	TileCalculator(long seed) {
		this.seed = seed;
		this.frameWidth = 600;
		this.tile = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/sandtile.png").getImage());
	}
	
	public BufferedImage getTile() {
		return this.tile;
	}
	
	public ArrayList<String[]> getViewingSlice() {
		return generateViewingSlice(this.seed,600,Player.getCoordinates()[0]);
	}
	
	public int getOffset() {
		return 50-Player.getCoordinates()[0]%50;
	}
	
	private ArrayList<String[]> generateViewingSlice(long seed,int width,int x) {
		ArrayList<String[]> viewingSlice = new ArrayList<>();
		for (int i=0;i<width/50+5;i++) {
			viewingSlice.add(new String[]{"S"});
		}
		return viewingSlice;
	}
	
	private int randomInt(long seed,int x) {
		Random random = new Random(seed*13*x);
		return random.nextInt();
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