import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
public class Enemies {
    private int health;
    private boolean inMotion = false;
    private int[] coordinates = new int[2];
    private double[] velocity = new double[2];
    private boolean falling = false;
    private int enemynum;
    private BufferedImage enemyImage;
    // Constructor
    Enemies(int[] coordinates, int health, int enemyNum) {
        this.health = 100;
        this.coordinates = coordinates;
        this.enemynum = enemyNum;
        this.enemyImage = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/enemy/Test_Enemy (32x32).png").getImage());
    }

    // Method to update the enemy's position
    public void updatePosition() {
        if (inMotion) {
            coordinates[0] += velocity[0];
            coordinates[1] += velocity[1];
        }
        // Handle falling logic if needed
        if (falling) {
            coordinates[1] += 1; // Example gravity effect
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

    // Method to check if the enemy is falling
    public boolean isFalling() {
        return falling;
    }

    // Method to set the falling state
    public void setFalling(boolean isFalling) {
        falling = isFalling;
    }

    // Getters for coordinates and health
    public int[] getCoordinates() {
        return coordinates;
    }

    public int getHealth() {
        return health;
    }
    public BufferedImage getEnemyImage() {
        return enemyImage;
    }
}