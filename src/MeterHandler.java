import java.util.Timer;
import java.util.TimerTask;
public class MeterHandler {
    public static void main(String[] args) {
        WaterMeter water = new WaterMeter();
        OverheatMeter heat = new OverheatMeter();        
    }
}
class WaterMeter{ //obj name: water
    private float amount = 0.0f;
    static float max_amount = 100.0f;
    float getAmount(){
        return this.amount;
    }
    static float getMax(){
        return max_amount;
    }
    void setAmount(float amount){
        this.amount = amount;
    }
}
class OverheatMeter{ //obj name: heat
    private float amount = 0.0f;
    static float max_amount = 600.0f;//seconds
    Timer heat_rise_delay;
    OverheatMeter(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                heatRise(1f);
            }
        };
        this.heat_rise_delay = new Timer();
        this.heat_rise_delay.schedule(timerTask, 1000, 1000);
    }
    float getAmount(){
        return this.amount;
    }
    static float getMax(){
        return max_amount;
    }
    void setAmount(float amount){
        this.amount = amount;
    }
    private void heatRise(float amount){
        if (Player.inSun()){ //change to Player.inSun()
            this.setAmount(this.getAmount()+amount);
            //System.out.println("heat: "+this.getAmount()); // debugging
        }
        if (this.getAmount() >= getMax()){
            System.out.println("Player has died."); // change this into a method which tells the player its dead
            this.heat_rise_delay.cancel();
        }
    }
}