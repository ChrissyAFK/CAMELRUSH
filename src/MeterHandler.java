import javax.swing.Timer;
public class MeterHandler {
    public static void main(String[] args) {
        WaterMeter water = new WaterMeter();
        OverheatMeter heat = new OverheatMeter();
        
            if (Player.inSun()){ //should be changed to Player.inSun() 
                heat.setAmount(heat.getAmount()+1);
                System.out.println("heat: "+heat.getAmount());
            }
        
    }
}
class WaterMeter{ //obj name: water
    private float amount = 0.0f;
    static float max_amount = 100.0f;
    float getAmount(){
        return this.amount;
    }
    float getMax(){
        return this.max_amount;
    }
    void setAmount(float amount){
        this.amount = amount;
    }
}
class OverheatMeter{ //obj name: heat
    private float amount = 0.0f;
    static float max_amount = 100.0f;
    Timer heat_rise_delay;
    OverheatMeter(){
        this.heat_rise_delay = new Timer((1000/50));
    }
    float getAmount(){
        return this.amount;
    }
    float getMax(){
        return this.max_amount;
    }
    void setAmount(float amount){
        this.amount = amount;
    }
}