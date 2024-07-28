import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.util.ArrayList;

public class Projectile{
    private int[] coordinates = {0,0};
    private double speed;
    private int horizontal; //-1 = left, 1 = right
    private int vertical; //-1 = up, 1 = down
    private Image sprite;
    private int[] size = {0,0};
    private Timer spitDuration;
    Projectile(int[] start, int end, double speed, int h, int v, String sprite, int[] size){
        this.coordinates[0] = start[0];
        this.coordinates[1] = start[1];
        this.speed = speed;
        this.horizontal = h;
        this.vertical = v;
        this.sprite = new ImageIcon("CAMELRUSH/assets/projectiles/"+sprite).getImage();
        this.size[0] = size[0];
        this.size[1] = size[1];
        this.spitDuration = new Timer(end,e->spitDuration.stop());
		this.spitDuration.start();
    }
    public void move(){
        coordinates[0] += horizontal*speed - Player.getVelocity()[0];
        coordinates[1] += vertical*speed + (int)Player.getVelocity()[1];
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
    public double[] getVelocity(){
        return new double[]{(this.horizontal*this.speed), (this.vertical*this.speed)};
    }
    public boolean getStopped(){
        return !this.spitDuration.isRunning();
    }

}
