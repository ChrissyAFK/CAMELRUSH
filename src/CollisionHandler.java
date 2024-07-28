import java.util.ArrayList;

public class CollisionHandler {
	private static final int[][] camelFacingRight = {{270,270,18*5-3,26*5},{240+28*5,260,10*5,11*5}};//{{190+5*10,270,18*5,0},{190+5*10+17*5,260,14*5,11*5}};//{{275,270,14*5,26*5},{240+28*5,260,14*5,11*5}};//{{235,270,18*5,26*5},{240+17*5,260,14*5,11*5}}
	//private static final int[][] camelFacingRight = {{190,240,220,160}};
	public static boolean willCollide(int[] tileCoordinates,int[] entityCoordinates,String entityType,int[] direction) {
		if (entityType.equals("Camel")) {
			//int[] updatedTileCoordinates = {tileCoordinates[0]-direction[0]+7,tileCoordinates[1]+direction[1]};
			int[] updatedTileCoordinates;
			updatedTileCoordinates = new int[]{tileCoordinates[0]-direction[0],tileCoordinates[1]+direction[1]};
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
	
	public static boolean isColliding(ArrayList<String[]> tileList) {
		for (int j=0;j<tileList.get(0).length;j++) {
			for (int i=0;i<tileList.size();i++) {
				if (i<Player.getCoordinates()[0]/50+15 && i>Player.getCoordinates()[0]/50-3){
					if (tileList.get(i)[j].equals("S")) {
						if (willCollide(new int[]{i*50-Player.getCoordinates()[0],j*50-150+Player.getCoordinates()[1]},new int[]{},"Camel",
								new int[]{(int)Player.getVelocityX(),(int)Player.getVelocityY()})) {
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
=======
	private ArrayList<Tile> tile_hitboxes;
	private int[] player_body = {}; //top left, top right, bottom left, bottom right
	private int[] player_head;
	public void initializeHitboxes() throws Exception{
>>>>>>> 6bbd3b55b036f08d50ff6a0cf06cc67396ea921b
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
	}*/
}