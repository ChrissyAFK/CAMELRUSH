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
	
	private BufferedImage baseOfPalm;
	private BufferedImage trunkOfPalm;
	private BufferedImage headOfPalm;
	private int animationFrame = 0;
	
	TileCalculator() {
		this.sandTile = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/blocks/sand_tile (8x8).png").getImage());
		this.waterTile = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/blocks/water_tile (8x8).png").getImage());
		
		this.baseOfPalm = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/blocks/palm_base (32x16).png").getImage());
		this.trunkOfPalm = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/blocks/palm_trunk (32x8).png").getImage());
		this.headOfPalm = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/blocks/palm_head (32x20).png").getImage());
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
	
	public BufferedImage getPalmBase() {
		return this.baseOfPalm;
	}
	
	public BufferedImage getPalmTrunk() {
		return this.trunkOfPalm;
	}
	
	public BufferedImage getPalmHead() {
		return this.headOfPalm;
	}
	
	public ArrayList<String[]> getViewingSlice(String level) throws Exception {
		return generateRoom("CAMELRUSH/levels/"+level);
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