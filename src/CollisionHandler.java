import java.util.ArrayList;

public class CollisionHandler {
	private static final int[][] camelFacingRight = {{Display.displaySize[0]/2-45,Display.displaySize[1]/2-38,18*5,26*5},{Display.displaySize[0]/2+45,Display.displaySize[1]/2-48,14*5,11*5}};
	private static final int[][] camelFacingLeft = {{Display.displaySize[0]/2-45,Display.displaySize[1]/2-38,18*5,26*5},{Display.displaySize[0]/2-115,Display.displaySize[1]/2-48,14*5,11*5}};
	private static final int[][] camelBody = {{Display.displaySize[0]/2-45,Display.displaySize[1]/2-38,18*5,26*5}};
	private static final int[][] spitball = {{0,0,25,25}};
	private static final int[][] enemyCactus = {{25,25,15*5,19*5}};
	public static boolean willCollide(int[] tileCoordinates,int[] entityCoordinates,String entityType,double[] direction,String blockType,String axis) {
		int[] updatedTileCoordinates = {};
		switch (entityType) {
			case "Camel":
				//System.out.println(entityCoordinates[0]);
				//System.out.println(entityCoordinates[1]+"\n-------");
				if (axis.toLowerCase().equals("y")) {
					updatedTileCoordinates = new int[]{tileCoordinates[0],tileCoordinates[1]+(int)direction[1]};
				} else if (blockType.equals("W")) {
					updatedTileCoordinates = new int[]{tileCoordinates[0]-(int)direction[0],tileCoordinates[1]+(int)direction[1]+20};
				} else {
					updatedTileCoordinates = new int[]{tileCoordinates[0]-(int)direction[0],tileCoordinates[1]+(int)direction[1]};
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
				break;
			case "Camel Body":
				updatedTileCoordinates = new int[]{tileCoordinates[0]-(int)direction[0],tileCoordinates[1]+(int)direction[1]};
				for (int[] hitbox:camelBody) {
					if (hitbox[0]+hitbox[2]>updatedTileCoordinates[0]&&
							hitbox[0]<updatedTileCoordinates[0]+50&&
							hitbox[1]+hitbox[3]>updatedTileCoordinates[1]&&
							hitbox[1]<updatedTileCoordinates[1]+50) {
						return true;
					}
				}
				break;
			case "Spitball":
				updatedTileCoordinates = new int[]{tileCoordinates[0]-(int)direction[0],tileCoordinates[1]+(int)direction[1]};
				if (Player.facingRight()) {
					for (int[] hitbox:spitball) {
						if (entityCoordinates[0]+hitbox[2]>updatedTileCoordinates[0]&&
								entityCoordinates[0]<updatedTileCoordinates[0]+50&&
								entityCoordinates[1]+hitbox[3]>updatedTileCoordinates[1]&&
								entityCoordinates[1]<updatedTileCoordinates[1]+50) {
							return true;
						}
					}
				}
				break;
			case "Enemy Cactus vs Tile":
				if (axis.toLowerCase().equals("y")) {
					updatedTileCoordinates = new int[]{tileCoordinates[0],tileCoordinates[1]+(int)direction[1]};
				} else {
					updatedTileCoordinates = new int[]{tileCoordinates[0]-(int)direction[0],tileCoordinates[1]+(int)direction[1]};
				}
				if (Player.facingRight()) {
					for (int[] hitbox:enemyCactus) {
						if (entityCoordinates[0]+hitbox[2]+hitbox[0]>updatedTileCoordinates[0]&&
								entityCoordinates[0]+hitbox[0]<updatedTileCoordinates[0]+50&&
								-entityCoordinates[1]+hitbox[1]+hitbox[3]>-updatedTileCoordinates[1]+375&&
								-entityCoordinates[1]+hitbox[1]<(-300)+50) {
							return true;
						}
						/*if (entityCoordinates[0]+hitbox[2]+hitbox[0]>0&&
								entityCoordinates[0]+hitbox[0]<0+50&&
								-entityCoordinates[1]+hitbox[1]+hitbox[3]>-300+0&&
								-entityCoordinates[1]+hitbox[1]<-300+50) {
							System.out.println("colliding");
							return true;
						}*/
					}
				}
				break;
			/*case "Enemy Cactus vs Camel":
				updatedTileCoordinates = new int[]{tileCoordinates[0]-(int)direction[0],tileCoordinates[1]+(int)direction[1]};
				if (Player.facingRight()) {
					for (int[] hitbox:enemyCactus) {
						if (entityCoordinates[0]+hitbox[2]>updatedTileCoordinates[0]&&
								entityCoordinates[0]<updatedTileCoordinates[0]+50&&
								entityCoordinates[1]+hitbox[3]>updatedTileCoordinates[1]&&
								entityCoordinates[1]<updatedTileCoordinates[1]+50) {
							return true;
						}
					}
				}
				break;*/
			default:
				System.out.println("Error: Unknown entity type");
				return false;
		}
		return false;
	}
	
	public static boolean isColliding(ArrayList<String[]> tileList,String entityType,int[] entityCoordinate,double[] entityVelocity,String blockType,String axis) {
		for (int j=0;j<tileList.get(0).length;j++) {
			for (int i=0;i<tileList.size();i++) {
				if (i<Player.getCoordinates()[0]/50+15 && i>entityCoordinate[0]/50-3){
					if (tileList.get(i)[j].equals(blockType)) {
						if (willCollide(new int[]{i*50-entityCoordinate[0],j*50-150+entityCoordinate[1]},entityCoordinate,entityType,
								entityVelocity,blockType,axis)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}