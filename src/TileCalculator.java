import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;


import javax.swing.ImageIcon;

public class TileCalculator {
	private long seed;
	private int frameWidth;
	private BufferedImage sandTile;
	private BufferedImage waterTile;
	
	TileCalculator() {
		this.frameWidth = 600;
		//this.tile = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/sandtile.png").getImage());
		this.sandTile = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/test_sand_tile.png").getImage());
		this.waterTile = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/test_water_tile.png").getImage());
	}
	
	public BufferedImage getSandTile() {
		return this.sandTile;
	}
	
	public BufferedImage getWaterTile() {
		return this.waterTile;
	}
	
	public ArrayList<String[]> getViewingSlice() throws Exception {
		//return generateViewingSlice(this.seed,600,Player.getCoordinates()[0]);
		return generateRoom("CAMELRUSH/levels/ftest_level.txt");
	}
	
	public int getOffset() {
		return 50-Player.getCoordinates()[0]%50;
	}
	
	private ArrayList<String[]> generateViewingSlice(long seed,int width,int x) {
		ArrayList<String[]> viewingSlice = new ArrayList<>();
		for (int i=0;i<width/50+5;i++) {
			//String[] stringToAdd = new String[randomInt(seed,x+i)];
			//for (int j=0;j<stringToAdd.length;j++) {
			//	stringToAdd[j] = "S";
			//}
			//viewingSlice.add(stringToAdd);
			viewingSlice.add(new String[]{"S"});
		}
		return viewingSlice;
	}
	
	private int randomInt(long seed,int x) {
		Random random = new Random(seed*13*x);
		return random.nextInt(3)+1;
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

	private ArrayList<String[]> generateRoom(String level) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(level));
		String line;
		ArrayList<String[]> list = new ArrayList<>();
		while ((line = reader.readLine())!=null) {
			String[] tokens = line.split("");
			list.add(tokens);
		}
		reader.close();
		return list;
	}
}