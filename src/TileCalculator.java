import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class TileCalculator {
	private BufferedImage sandTile;
	private BufferedImage waterTile;
	private int animationFrame = 0;
	
	TileCalculator() {
		//this.tile = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/sandtile.png").getImage());
		this.sandTile = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/blocks/sand_tile (8x8).png").getImage());
		this.waterTile = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/blocks/water_tile (8x8).png").getImage());
	}
	
	public void changeAnimationFrame() {
		if (this.animationFrame==6) {
			this.animationFrame = 0;
		} else {
			this.animationFrame++;
		}
	}
	
	public BufferedImage getSandTile() {
		return this.sandTile;
	}
	
	public BufferedImage getWaterTile() {
		return this.waterTile.getSubimage(animationFrame*8,0,8,8);
	}
	
	public ArrayList<String[]> getViewingSlice() throws Exception {
		return generateRoom("CAMELRUSH/levels/ftest_level.txt");
	}
	
	public int getOffset() {
		return 50-Player.getCoordinates()[0]%50;
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