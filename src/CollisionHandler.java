import java.util.ArrayList;

public class CollisionHandler {
	private static final int[][] camelFacingRight = {{235,270,18*5,26*5},{240+28*5,260,14*5,11*5}};//{{235,270,18*5,26*5},{240+17*5,260,14*5,11*5}}
	public static boolean willCollide(int[] tileCoordinates,int[] entityCoordinates,String entityType,int[] direction) {
		if (entityType.equals("Camel")) {
			int[] updatedTileCoordinates = {tileCoordinates[0]-direction[0]+7,tileCoordinates[1]+direction[1]};
			for (int[] hitbox:camelFacingRight) {
				if (hitbox[0]+hitbox[3]>updatedTileCoordinates[0]+35&&
					hitbox[0]<updatedTileCoordinates[0]+85&&
					hitbox[1]+hitbox[3]>updatedTileCoordinates[1]&&
					hitbox[1]<updatedTileCoordinates[1]+50) {
					/*System.out.println(hitbox[0]);
					System.out.println(hitbox[1]);
					System.out.println(hitbox[2]);
					System.out.println(hitbox[3]);*/
					return true;
				}
			}
			return false;
		} else {
			System.out.println("unknown untity type");
			return false;
		}
	}
	
	public static boolean isColliding(ArrayList<String[]> tileList) {
		for (int j=0;j<tileList.get(0).length;j++) {
			for (int i=0;i<tileList.size();i++) {
				if (i<Player.getCoordinates()[0]/50+15 && i>Player.getCoordinates()[0]/50-3){
					if (tileList.get(i)[j].equals("S")||tileList.get(i)[j].equals("W")) {
						if (willCollide(new int[]{i*50-Player.getCoordinates()[0],j*50-150+Player.getCoordinates()[1]},new int[]{},"Camel",new int[]{(int)Player.getVelocityX(),(int)Player.getVelocityY()})) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}