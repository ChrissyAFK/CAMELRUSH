import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class Projectile{
    private int[] coordinates = {0,0};
    private int end_point;
    private double speed;
    private int horizontal; //-1 = left, 1 = right
    private int vertical; //-1 = up, 1 = down
    private Image sprite;
    Projectile(int[] start, int end, double speed, int h, int v, String sprite){
        this.coordinates[0] = start[0];
        this.coordinates[1] = start[0];
        this.end_point = end;
        this.speed = speed;
        this.horizontal = h;
        this.vertical = v;
        this.sprite = new ImageIcon("CAMELRUSH/assets/projectiles/"+sprite).getImage();
    }
    public void move(){
        coordinates[0] += horizontal*speed;
        coordinates[1] += vertical*speed;
    }

}
