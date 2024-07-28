import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.Timer;
public class MeterHandler {
}
class WaterMeter{ //obj name: water
    private float amount;
    private static float max_amount;
    private Timer drink_cooldown;
    private BufferedImage waterMeter;
    private int waterHeight = 0;
    
    WaterMeter() {
    	this.waterMeter = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/other/Water_Meter (12x32).png").getImage());
        this.amount = 0.0f;
        max_amount = 100.0f;
        this.drink_cooldown = new Timer(200,e->drink_cooldown.stop());
        this.drink_cooldown.start();
    }
    
    public void updateWaterMeter() {
    	this.waterHeight = 25*getPercentFilled()/100;
    }
    
    public BufferedImage getWaterMeter() {
    	return this.waterMeter.getSubimage(this.waterHeight*12,0,12,32);
    }
    
    public float getAmount(){
        return this.amount;
    }
    static float getMax(){
        return max_amount;
    }
    int getPercentFilled(){
        return (int)(getAmount()/getMax()*100);
    }
    public void setAmount(float amount){
        this.amount = amount;
        //System.out.println("a: "+getAmount());//debug
    }
    public void drink(){
        if (!this.drink_cooldown.isRunning()){
            if (getPercentFilled() < 100){
                this.setAmount(getAmount()+10);
            } else {
                this.setAmount(100);
            }
            this.drink_cooldown.start();
        }
    }
}
class OverheatMeter{ //obj name: heat
    private float amount = 0.0f;
    private static float max_amount = 600.0f;//seconds
    private Timer heat_rise_delay;
    private BufferedImage heatMeter;
    private int heatHeight = 0;
    
    OverheatMeter() {
    	this.heatMeter = Display.toBufferedImage(new ImageIcon("CAMELRUSH/assets/other/Water_Meter (12x32).png").getImage());
        this.heat_rise_delay = new Timer(1000,e->heatRise(1f));
        this.heat_rise_delay.start();
    }
    
    public void updateHeatMeter() {
    	this.heatHeight = 25*(int)getPercentFilled()/100;
    }
    
    public BufferedImage getHeatMeter() {
    	return this.heatMeter.getSubimage(this.heatHeight*12,0,12,32);
    }
    
    float getAmount(){
        return this.amount;
    }
    static float getMax(){
        return max_amount;
    }
    float getPercentFilled(){
        return getAmount()/getMax()*100;
    }
    void setAmount(float amount){
        this.amount = amount;
    }
    private void heatRise(float amount){
        if (Player.inSun()){
            this.setAmount(this.getAmount()+amount);
            //System.out.println("heat: "+this.getPercentFilled()); // debugging
        }
        if (this.getAmount() >= getMax()){
            System.out.println("Player has died."); // change this into a method which tells the player its dead
            this.heat_rise_delay.stop();
        }
    }
}