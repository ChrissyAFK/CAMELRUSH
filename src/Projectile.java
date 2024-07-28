import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Projectile{
    private int[] coordinates = {0,0};
    private int end_point;
    private double speed;
    private int horizontal; //-1 = left, 1 = right
    private int vertical; //-1 = up, 1 = down
    private Image sprite;
    private int[] size = {0,0};
    Projectile(int[] start, int end, double speed, int h, int v, String sprite, int[] size){
        this.coordinates[0] = start[0];
        this.coordinates[1] = start[1];
        this.end_point = end;
        this.speed = speed;
        this.horizontal = h;
        this.vertical = v;
        this.sprite = new ImageIcon("CAMELRUSH/assets/projectiles/"+sprite).getImage();
        this.size[0] = size[0];
        this.size[1] = size[1];
    }
    public void move(){
        coordinates[0] += horizontal*speed;
        coordinates[1] += vertical*speed;
    }
    public static void moveFromWorld(ArrayList<Projectile> projectiles){
        for (Projectile proj : projectiles) {
            proj.coordinates[0] -= Player.getVelocityX();
            proj.coordinates[1] -= Player.getVelocityY();
        }
    }
    public Image getSprite(){
        return this.sprite;
    }
    public int[] getCoordinates(){
        return this.coordinates;
    }
    public int[] getSize(){
        return this.size;
    }

}
