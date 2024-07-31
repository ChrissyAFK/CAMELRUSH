import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
public class Enemies {
    private int health;
    private boolean inMotion = false;
    private int[] coordinates = new int[2];
    private double[] velocity = {2,0};
    private boolean falling = true;
    private boolean facingRight = true;
    private int enemynum;
    private BufferedImage enemyImage;
    private int enemyWalkingAnimation;
    private int gravity = 2;
    // Constructor
    Enemies(int[] coordinates, int health, int enemyNum) {
        this.health = 100;
        this.coordinates = coordinates;
        this.enemynum = enemyNum;
        this.enemyImage = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/enemy/cactus_enemy_walking (24x24).png").getImage());
    }

    // Method to update the enemy's position
    public void updateXPosition() {
        if (inMotion) {
            coordinates[0] += velocity[0];
        }
    }
    
    public void updateYPosition() {
    	if (falling) {
            coordinates[1] -= (int)velocity[1]; // Example gravity effect
        }
    }
    
    public void Enemyfall() {
		if (falling){
			velocity[1]-=0.22;
		}
		else{
			velocity[1] = 0;
		}
	}
    // Method to take damage
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            // Handle enemy defeat logic
            System.out.println("Enemy defeated!");
        }
    }

    // Method to start moving
    public void startMoving(double velX, double velY) {
        velocity[0] = velX;
        velocity[1] = velY;
        inMotion = true;
    }

    // Method to stop moving
    public void stopMoving() {
        velocity[0] = 0;
        velocity[1] = 0;
        inMotion = false;
    }
    
    public void stopXMotion() {
    	velocity[0] = 0;
    }

    // Method to check if the enemy is falling
    public boolean falling() {
        return falling;
    }

    // Method to set the falling state
    public void isFalling() {
        falling = true;
    }
    
    public void isNotFalling() {
        falling = false;
    }

    // Getters for coordinates and health
    public int[] getCoordinates() {
        return coordinates;
    }
    
    public double[] getVelocity() {
    	return this.velocity;
    }

    public int getHealth() {
        return health;
    }
    public BufferedImage getEnemyImage() {
        return enemyImage;
    }
    public void changeEnemyAnimation(){
        if (enemyWalkingAnimation==6){
            enemyWalkingAnimation = 0;
        } else {
            enemyWalkingAnimation++;
        }
    }
    public BufferedImage getEnemyCurrentAnimation(){
        return enemyImage.getSubimage(enemyWalkingAnimation*24,0,24,24);
    }
    
    public void faceRight() {
    	this.facingRight = true;
    }
    
    public void faceLeft() {
    	this.facingRight = false;
    }
    
    public boolean facingRight() {
    	return this.facingRight;
    }
}