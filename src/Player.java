import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Player {
	private static int[] coordinates = {0,0};
	private static boolean inSun = false;
	private static boolean inMotion = false;
	private static boolean falling = false;
	private static int gravity = 2;
	private static double[] velocity = {0,0};
	private static boolean facingRight = true;
	private int camelAnimation = 0;
	private BufferedImage idleCamel;
	private BufferedImage walkingCamel;
	private BufferedImage jumpingCamel;
	
	Player() throws IOException {
		this.idleCamel = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_idle_animation (44x32).png").getImage());
        this.walkingCamel = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_walking_animation (44x32).png").getImage());
		this.jumpingCamel = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_jump_animation (44x32).png").getImage());
	}
	
	public void changeCamelAnimation() {
		if (camelAnimation==14) {
			this.camelAnimation=0;
		} else {
			this.camelAnimation++;
		}
	}
	
	public BufferedImage getCurrentAnimation() {
		if (inMotion) {
			return this.walkingCamel.getSubimage(this.camelAnimation*44,0,44,32);
		} else {
			return this.idleCamel.getSubimage(this.camelAnimation*44,0,44,32);
		}
	}
	
	public static boolean inSun() {
		return inSun;
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
	
	public static void setCoordinates(int[] coordinates) {
		Player.coordinates = coordinates;
		System.out.println(coordinates[1]);
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
	
	private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img,0,0,null);
        bGr.dispose();
        
        return bimage;
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
