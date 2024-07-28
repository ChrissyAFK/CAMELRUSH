import java.util.ArrayList;

public class CollisionHandler {
	private static final int[][] camelFacingRight = {{Display.displaySize[0]/2-45,Display.displaySize[1]/2-38,18*5,26*5},{Display.displaySize[0]/2+45,Display.displaySize[1]/2-48,14*5,11*5}};
	private static final int[][] camelFacingLeft = {{Display.displaySize[0]/2-45,Display.displaySize[1]/2-38,18*5,26*5},{Display.displaySize[0]/2-115,Display.displaySize[1]/2-48,14*5,11*5}};
	public static boolean willCollide(int[] tileCoordinates,int[] entityCoordinates,String entityType,int[] direction,String axis) {
		if (entityType.equals("Camel")) {
			int[] updatedTileCoordinates = {};
			if (axis.toLowerCase().equals("y")) {
				updatedTileCoordinates = new int[]{tileCoordinates[0],tileCoordinates[1]+direction[1]};
			} else {
				updatedTileCoordinates = new int[]{tileCoordinates[0]-direction[0],tileCoordinates[1]+direction[1]};
			}
			if (Player.facingRight()) {
				for (int[] hitbox:camelFacingRight) {
					if (hitbox[0]+hitbox[2]>updatedTileCoordinates[0]&&
							hitbox[0]<updatedTileCoordinates[0]+50&&
							hitbox[1]+hitbox[3]>updatedTileCoordinates[1]&&
							hitbox[1]<updatedTileCoordinates[1]+50) {
						return true;
					}
				}
			} else {
				for (int[] hitbox:camelFacingLeft) {
					if (hitbox[0]+hitbox[2]>updatedTileCoordinates[0]&&
							hitbox[0]<updatedTileCoordinates[0]+50&&
							hitbox[1]+hitbox[3]>updatedTileCoordinates[1]&&
							hitbox[1]<updatedTileCoordinates[1]+50) {
						return true;
					}
				}
			}
		} else {
			System.out.println("Error: Unknown entity type");
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
}