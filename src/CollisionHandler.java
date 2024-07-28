import java.util.ArrayList;
import java.util.List;

public class CollisionHandler {
	private ArrayList<Tile> tile_hitboxes;
	private int[] player_body = {}; //top left, top right, bottom left, bottom right
	private int[] player_head;
	public void initializeHitboxes() throws Exception{
		ArrayList<String[]> room = new TileCalculator().getViewingSlice();
		for (int j=0;j<room.get(0).length;j++) {
			for (int i=0;i<room.size();i++) {
				if (!room.get(j)[i].equals("O")){
					tile_hitboxes.add(new Tile(i*50-Player.getCoordinates()[0],j*50-150+Player.getCoordinates()[1], room.get(j)[i]));
				}
			}
		}
	}
	public List<Tile> isTouching(){
		for (int j=0;j<tile_hitboxes.get(0).length;j++) {
			for (int i=0;i<tile_hitboxes.size();i++) {
				if (i<Player.getCoordinates()[0]/50+15 && i>Player.getCoordinates()[0]/50-3){
					
				}
			}
		}
	}
	public class Tile{
		public int x;
		public int y;
		public static final int width = 50;
		public static final int height = 50;
		public String type; 
		public Tile(int x, int y, String type){
			this.x = x;
			this.y = y;
			this.type = type;
		}
	}

}