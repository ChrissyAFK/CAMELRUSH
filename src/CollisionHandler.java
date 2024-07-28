import java.util.ArrayList;

public class CollisionHandler {
	private static final int[][] camelFacingRight = {{270,270,18*5,26*5},{240+28*5,260,9*5+2,11*5}};//{{190+5*10,270,18*5,0},{190+5*10+17*5,260,14*5,11*5}};//{{275,270,14*5,26*5},{240+28*5,260,14*5,11*5}};//{{235,270,18*5,26*5},{240+17*5,260,14*5,11*5}}
	//private static final int[][] camelFacingRight = {{190,240,220,160}};
	public static boolean willCollide(int[] tileCoordinates,int[] entityCoordinates,String entityType,int[] direction,String axis) {
		if (entityType.equals("Camel")) {
			//int[] updatedTileCoordinates = {tileCoordinates[0]-direction[0]+7,tileCoordinates[1]+direction[1]};
			int[] updatedTileCoordinates;
			if (axis.toLowerCase().equals("x")) {
				updatedTileCoordinates = new int[]{tileCoordinates[0]-direction[0],tileCoordinates[1]};
			} else if (axis.toLowerCase().equals("y")) {
				updatedTileCoordinates = new int[]{tileCoordinates[0],tileCoordinates[1]+direction[1]};
			} else {
				System.out.println("Error: Invalid axis");
				return false;
			}
			for (int[] hitbox:camelFacingRight) {
				if (hitbox[0]+hitbox[2]>updatedTileCoordinates[0]+35&&
						hitbox[0]<updatedTileCoordinates[0]+85&&
						hitbox[1]+hitbox[3]>updatedTileCoordinates[1]&&
						hitbox[1]<updatedTileCoordinates[1]+50) {
					return true;
				}
			}
		} else {
			System.out.println("Error: Unknown untity type");
		}
		return false;
	}
	
	public static boolean isColliding(ArrayList<String[]> tileList,String axis) {
		for (int j=0;j<tileList.get(0).length;j++) {
			for (int i=0;i<tileList.size();i++) {
				if (i<Player.getCoordinates()[0]/50+15 && i>Player.getCoordinates()[0]/50-3){
					if (tileList.get(i)[j].equals("S")) {
						if (willCollide(new int[]{i*50-Player.getCoordinates()[0],j*50-150+Player.getCoordinates()[1]},new int[]{},"Camel",
								new int[]{(int)Player.getVelocityX(),(int)Player.getVelocityY()},axis)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/*private ArrayList<int[]> tile_hitboxes;
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
	}*/
}