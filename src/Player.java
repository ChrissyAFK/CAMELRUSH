import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Player {
	private static int[] coordinates = {0,0};
	private static boolean inSun = false;
	private static boolean inMotion = false;
	private static boolean falling = true;
	private static int gravity = 2;
	private static double velocityY = 0;
	private int camelAnimation = 0;
	private BufferedImage idleCamel;
	private BufferedImage walkingCamel;

	
	
	Player() throws IOException {
		this.idleCamel = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_idle_animation (44x32).png").getImage());
        this.walkingCamel = toBufferedImage(new ImageIcon("CAMELRUSH/assets/player/camel_walking_animation (44x32).png").getImage());
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
	
	public static int[] getCoordinates() {
		return coordinates;
	}
	
	public static void changeXcoord(double change) {
		coordinates[0]+=(int)change;
	}
	
	public static void changeYcoord(double change) {
		coordinates[1]+=(int)change;
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
	
	private void isFalling(){
		falling = true;
	}

	private void isNotFalling(){
		falling = false;
	}
	public static void fall(){
		if (falling){
			velocityY+=0.5;
			changeYcoord(velocityY*gravity);
		}
		else{
			velocityY = 0;
		}
	}
}
