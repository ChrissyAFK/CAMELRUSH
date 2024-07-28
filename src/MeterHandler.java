import javax.swing.Timer;
public class MeterHandler {
}
class WaterMeter{ //obj name: water
    private float amount;
    private static float max_amount;
    private Timer drink_cooldown;
    WaterMeter(){
        this.amount = 0.0f;
        max_amount = 100.0f;
        this.drink_cooldown = new Timer(200,e->drink_cooldown.stop());
        this.drink_cooldown.start();
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
    OverheatMeter(){
        this.heat_rise_delay = new Timer(1000,e->heatRise(1f));
        this.heat_rise_delay.start();
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