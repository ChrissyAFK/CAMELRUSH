public class MeterHandler {
    public static void main(String[] args) {
        WaterMeter water = new WaterMeter();
        OverheatMeter heat = new OverheatMeter();
        while (true){
            if (App.inSun()){
                heat.setAmount(heat.getAmount()+1);
                System.out.println("heat"+heat.getAmount());
            }
        }
    }
}
class WaterMeter{
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
class OverheatMeter{
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