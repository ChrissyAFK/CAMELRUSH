import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Player {
	private static int[] coordinates = {0,0};
	private static boolean inSun = true;
	private static boolean inMotion = false;
	private static boolean falling = false;
	private static int gravity = 2;
	private static double[] velocity = {0,0};
	private static boolean facingRight = true;
	private static int headtilt = 0;
	
	private int defaultCamelAnimation = 0;
	
	private int jumpingCamelAnimation = 0;
	private int openingCamelAnimation = 4;
	private int fallingCamelAnimation = 0;
	private int landingCamelAnimation = 0;
	private boolean landingInProgress = false;
	
	private static boolean drinkingInProgress = false;
	private boolean startDrinkingInProgress = false;
	private boolean inDrinkingInProgress = false;
	private boolean endDrinkingInProgress = false;
	private int startDrinkingAnimation = 0;
	private int inDrinkingAnimation = 0;
	private int endDrinkingAnimation = 0;
	
	private boolean dashingInProgress;
	private int dashingCamelAnimation = 0;
	
	private BufferedImage idleCamel;
	private BufferedImage walkingCamel;
	
	private BufferedImage jumpingCamel;
	private BufferedImage fallingCamel;
	private BufferedImage landingCamel;
	
	private BufferedImage startDrinkingCamel;
	private BufferedImage inDrinkingCamel;
	private BufferedImage endDrinkingCamel;
	
	private BufferedImage dashingCamel;
	
	Player() throws IOException {
		this.idleCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_idle_animation (44x32).png").getImage());
        this.walkingCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_walking_animation (44x32).png").getImage());
        
		this.jumpingCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_jump_animation (44x32).png").getImage());
		this.fallingCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_fall_animation (44x32).png").getImage());
		this.landingCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_land_animation (44x32).png").getImage());
		
		this.startDrinkingCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_start_drinking (44x32).png").getImage());
		this.inDrinkingCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_in_drinking (44x32).png").getImage());
		this.endDrinkingCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_end_drinking (44x32).png").getImage());
		
		this.dashingCamel = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_dash_animation (44x32).png").getImage());
	}
	
	public void changeCamelAnimation() {
		if (this.defaultCamelAnimation==14) {
			this.defaultCamelAnimation=0;
		} else {
			this.defaultCamelAnimation++;
		}
		
		if (!(this.jumpingCamelAnimation==3)) {
			this.jumpingCamelAnimation++;
		}
		
		if (!(this.openingCamelAnimation==7)) {
			this.openingCamelAnimation++;
		}
		
		if (this.fallingCamelAnimation==3) {
			this.fallingCamelAnimation=0;
		} else {
			this.fallingCamelAnimation++;
		}
		
		if (!(this.landingCamelAnimation==8)) {
			this.landingCamelAnimation++;
		} else {
			this.landingInProgress = false;
		}
		
		if (!(this.startDrinkingAnimation==15)) {
			this.startDrinkingAnimation++;
		}
		
		if (this.inDrinkingAnimation==12) {
			this.inDrinkingAnimation=0;
		} else {
			this.inDrinkingAnimation++;
		}
		
		if (!(this.endDrinkingAnimation==13)) {
			this.endDrinkingAnimation++;
		}
		
		if (this.dashingCamelAnimation==14) {
			this.dashingInProgress = false;
		} else {
			this.dashingCamelAnimation++;
		}
	}
	
	public BufferedImage getCurrentAnimation() {
		if (this.dashingInProgress) {
			return this.dashingCamel.getSubimage(this.dashingCamelAnimation*44,0,44,32);
		} else if (startDrinkingInProgress) {
			return this.startDrinkingCamel.getSubimage(this.startDrinkingAnimation*44,0,44,32);
		} else if (drinkingInProgress) {
			return this.inDrinkingCamel.getSubimage(this.inDrinkingAnimation*44,0,44,32);
		} else if (endDrinkingInProgress) {
			return this.endDrinkingCamel.getSubimage(this.endDrinkingAnimation*44,0,44,32);
		} else if (velocity[1]>0) {
			return this.jumpingCamel.getSubimage(this.jumpingCamelAnimation*44,0,44,32);
		} else if (this.landingInProgress) {
			return this.landingCamel.getSubimage(this.landingCamelAnimation*44,0,44,32);
		} else if (velocity[1]==-10.020000000000007) {
			this.landingInProgress = true;
			this.landingCamelAnimation = 2;
			return this.landingCamel.getSubimage(0,0,44,32);
		} else if (velocity[1]<-6) {
			this.openingCamelAnimation = 4;
			return this.fallingCamel.getSubimage(this.fallingCamelAnimation*44,0,44,32);
		} else if (velocity[1]<-2.65) {
			this.jumpingCamelAnimation = 0;
			return this.jumpingCamel.getSubimage(this.openingCamelAnimation*44,0,44,32);
		} else if (velocity[1]<-2.4&&velocity[1]!=-2.4200000000000004&&velocity[1]!=-2.6400000000000006) {
			this.openingCamelAnimation = 4;
			return this.jumpingCamel.getSubimage(this.openingCamelAnimation*44,0,44,32);
		} else if (inMotion) {
			return this.walkingCamel.getSubimage(this.defaultCamelAnimation*44,0,44,32);
		} else {
			return this.idleCamel.getSubimage(this.defaultCamelAnimation*44,0,44,32);
		}
	}
	
	public void startDashingAnimation() {
		if (!this.dashingInProgress) {
			this.dashingCamelAnimation = 0;
		}
		this.dashingInProgress = true;
	}
	
	public void isDrinking() {
		if (!Player.drinkingInProgress) {
			this.endDrinkingInProgress = false;
			this.inDrinkingInProgress = false;
			this.startDrinkingInProgress = true;
			this.startDrinkingAnimation = 0;
		}
		if (this.startDrinkingAnimation==15&&!this.inDrinkingInProgress) {
			this.startDrinkingInProgress = false;
			this.inDrinkingInProgress = true;
			this.inDrinkingAnimation = 0;
		}
		Player.drinkingInProgress = true;
	}
	
	public void isNotDrinking() {
		if (Player.drinkingInProgress) {
			this.inDrinkingInProgress = false;
			this.endDrinkingInProgress = true;
			this.endDrinkingAnimation = 0;
		}
		if (this.endDrinkingAnimation==13) {
			this.endDrinkingInProgress = false;
		}
		if (this.startDrinkingInProgress) {
			this.startDrinkingInProgress = false;
		}
		Player.drinkingInProgress = false;
	}
	
	public static boolean drinking() {
		return Player.drinkingInProgress;
	}
	
	public boolean inDrinkingInProgress() {
		return this.inDrinkingInProgress;
	}
	
	public void resetJumpingAnimation() {
		this.jumpingCamelAnimation = 0;
	}
	
	public static boolean inSun() {
		return inSun;
	}

	public static void cooling(){
		inSun = false;	
	}
	public static void heating(){
		inSun = true;	
	}
	
	public static boolean facingRight() {
		return facingRight;
	}
	
	public static void isFacingRight() {
		facingRight = true;
	}
	
	public static void isFacingLeft() {
		facingRight = false;
	}

	public static int headtilt() {
		return headtilt;
	}

	public static void headtiltChange(int change) {
		headtilt = change;
	}
	
	public static void setCoordinates(int[] coordinates) {
		Player.coordinates = coordinates;
	}
	
	public static int[] getCoordinates() {
		return coordinates;
	}
	
	public static double[] getVelocity() {
		return velocity;
	}
	
	public static void setVelocityX(double velocity) {
		Player.velocity[0] = velocity;
	}
	
	public static void setVelocityY(double velocity) {
		Player.velocity[1] = velocity;
	}
	
	public static void setYCoordinate(int y) {
		coordinates[1]=y;
	}
	
	public static void updateYCoordinates() {
		if (falling) {
			coordinates[1]+=(int)(velocity[1]);
		}
	}
	
	public static void updateXCoordinates() {
		coordinates[0]+=(int)(velocity[0]);
	}
	
	public static void isMoving() {
		inMotion = true;
	}
	
	public static void isNotMoving() {
		inMotion = false;
	}
	
	public static void isFalling() {
		falling = true;
	}

	public static void isNotFalling() {
		falling = false;
	}
	public static boolean getFallingStatus() {
		return falling;
	}

	public static void fall() {
		if (falling){
			velocity[1]-=0.22;
		}
		else{
			velocity[1] = 0;
		}
	}
}
