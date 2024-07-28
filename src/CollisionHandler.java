import java.util.ArrayList;

public class CollisionHandler {
	private ArrayList<int[]> tile_hitboxes;
	private int[] player_hitbox;
	public static void initializeHitboxes() throws Exception{
		ArrayList<String[]> room = new TileCalculator().getViewingSlice();
		for (int j=0;j<room.get(0).length;j++) {
			for (int i=0;i<room.size();i++) {
				
			}
		}
	}
	private class tile{
		private int x1;
		private int x2;
		private int y1;
		private int y2;
		public String type; 
		public tile(int x1, int x2, int y1, int y2, String type){
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			this.type = type;
		}
	}

}