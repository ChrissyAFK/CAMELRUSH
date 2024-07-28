import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel {
	public static int[] displaySize;
	private Background background;
	private Timer animateCamel;
	private Timer scrollTimer;
	private Timer spitCooldown;
	private InputHandler input;
	private Player player;
	private TileCalculator tile;
	private int playerSpeed = 2;
	private int frameCount;
	private double fps;
	private long startTime;
	private long prevTime;
	private JLabel fpsCounter;
	private ArrayList<Projectile> projectiles;
	private ArrayList<String[]> tileList = new ArrayList<>();
	private WaterMeter waterMeter;
	private OverheatMeter heatMeter;
	private long shiftCooldownEndTime = 0;
	private long shiftActiveEndTime = 0;

	Display(InputHandler input,Player player) throws Exception {
		this.setPreferredSize(new Dimension(1280,700));
		displaySize = new int[]{this.getWidth(),this.getHeight()};
		Player.setCoordinates(new int[]{0,displaySize[1]/250});
		setLayout(null); //44x32
		this.background = new Background();
		
		this.startTime = System.currentTimeMillis();
		this.prevTime = this.startTime;
		this.fpsCounter = new JLabel("");
		this.fpsCounter.setFont(new Font("Dialog",Font.BOLD,20));
		this.fpsCounter.setBounds(10,10,120,20);
		this.add(fpsCounter);
		this.tile = new TileCalculator();
		this.tileList = this.tile.getViewingSlice();
		
		this.animateCamel = new Timer(1000/15,e->animate());
		this.animateCamel.start();
		this.scrollTimer = new Timer(1000/1000,e->scroll());
		this.scrollTimer.start();
		this.spitCooldown = new Timer(1000,e->spitCooldown.stop());
		this.spitCooldown.start();
		this.input = input;
		this.player = player;
		this.projectiles = new ArrayList<>();
		this.waterMeter = new WaterMeter(); 
		this.heatMeter = new OverheatMeter(); 
	}
	
	private void animate() {
		this.player.changeCamelAnimation();
		this.tile.changeAnimationFrame();
		repaint();
	}
	
	private void scroll() {
		displaySize = new int[]{this.getWidth(),this.getHeight()};
		long currentTime = System.currentTimeMillis();
		//long delta = currentTime - this.prevTime;
		//System.out.println(delta);
		this.prevTime = currentTime;
		this.frameCount++;
		this.fpsCounter.setText("FPS: "+String.valueOf((double)Math.round(this.fps*100)/100));
		if (currentTime-this.startTime>1000) {
			this.fps = (double)this.frameCount/((currentTime-this.startTime)/1000.0);
			this.startTime = System.currentTimeMillis();
			this.frameCount = 0;
		}
		if (this.input.wKeyPressed()&&!Player.getFallingStatus()) {
			this.player.resetJumpingAnimation();
			Player.setVelocityY(10);
		}
		if (this.input.aKeyPressed()) {
			Player.setVelocityX(-this.playerSpeed);
			Player.isFacingLeft();
		}
		if (this.input.dKeyPressed()) {
			Player.setVelocityX(this.playerSpeed);
			Player.isFacingRight();
		}
		if (this.input.shiftKeyPressed() && currentTime > shiftCooldownEndTime) {
			shiftActiveEndTime = currentTime + 500; // 0.5 seconds
			shiftCooldownEndTime = currentTime + 1000; // 1 second cooldown
		}
		if (currentTime < shiftActiveEndTime) {
			Player.setVelocityX(Player.getVelocity()[0] * 6);
		}
		if (!this.input.aKeyPressed()&&!this.input.dKeyPressed()) {
			Player.setVelocityX(0);
		}
		if (this.input.upKeyPressed()) {
			Player.headtiltChange(-1);
		}
		if (this.input.downKeyPressed()) {
			Player.headtiltChange(1);
		}
		if (!this.input.upKeyPressed()&&!this.input.downKeyPressed()) {
			Player.headtiltChange(0);
		}
		if (this.input.eKeyPressed()&&CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"W","")) {
			this.waterMeter.drink();
		}
		if (this.input.inMotion()) {
			Player.isMoving();
		} else {
			Player.isNotMoving();
		}
		
		if (!CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"S","y")) {
			Player.isFalling();
		} else {
			Player.isNotFalling();
		}
		
		if (!CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"S","")) {
			Player.updateXCoordinates();
		}
		if (CollisionHandler.isColliding(this.tileList,"Camel Body",Player.getCoordinates(),Player.getVelocity(),"Z","")){
			Player.cooling();
		}
		else{
			Player.heating();
		}
		Player.updateYCoordinates();
		Player.fall();
		if (this.input.spaceKeyPressed() && (this.waterMeter.getAmount()>=20.0) && (!this.spitCooldown.isRunning())) {
			this.projectiles.add(new Projectile((new int[]{(Player.facingRight()?displaySize[0]/2+100:displaySize[0]/2-100),displaySize[1]/2-35}),3000,1.0,(Player.facingRight()?1:-1),Player.headtilt(),"spit_ball (5x5).png",(new int[]{20,20})));
			this.spitCooldown = new Timer((1000),e->spitCooldown.stop());
			this.spitCooldown.start();
			this.waterMeter.setAmount(this.waterMeter.getAmount()-20);
		}
		for (int i=0;i<projectiles.size();i++) {
			projectiles.get(i).move();
			if (projectiles.get(i).getStopped()){
				projectiles.remove(projectiles.get(i));
			}
		}
		this.waterMeter.updateWaterMeter();
		this.heatMeter.updateHeatMeter();
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0,0,displaySize[0],displaySize[1]);
		g.setColor(new Color(200,255,250));
		g.fillRect(0,0,displaySize[0],displaySize[1]);
		for (int i=0;i<(displaySize[0]/(256*5/2))+1;i++) {
			g.drawImage(this.background.getBackground(),i*256*5-(Player.getCoordinates()[0]/4)%(256*5),(Player.getCoordinates()[1]*6/5)+250,256*5,160*5,this);
		}
		try {
			this.tileList = this.tile.getViewingSlice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int j=0;j<this.tileList.get(0).length;j++) {
			for (int i=0;i<this.tileList.size();i++) {
				if (i<Player.getCoordinates()[0]/50+displaySize[0]/50 && i>Player.getCoordinates()[0]/50-displaySize[0]/50){//render distance
					if (this.tileList.get(i)[j].equals("S")) {
						g.drawImage(this.tile.getSandTile(),displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50,50,this);
					} else if (this.tileList.get(i)[j].equals("W")) {
						g.drawImage(this.tile.getWaterTile(),displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50,50,this);
					}
				}
			}
		}
		for (Projectile proj : projectiles) {
				g.drawImage(proj.getSprite(), proj.getCoordinates()[0], proj.getCoordinates()[1], proj.getSize()[0], proj.getSize()[1], this);
			}
		int[] camelDisplayAttributes = {};
		if (Player.facingRight()) {
			camelDisplayAttributes = new int[]{displaySize[0]/2-95,displaySize[1]/2-68,220,160};
		} else {
			camelDisplayAttributes = new int[]{displaySize[0]/2+95,displaySize[1]/2-68,-220,160};
		}
		g.drawImage(this.player.getCurrentAnimation(),camelDisplayAttributes[0],camelDisplayAttributes[1],camelDisplayAttributes[2],camelDisplayAttributes[3],this);
		// hitboxes
		g.setColor(new Color(255,0,0,90));
		if (Player.facingRight()) {
			//g.fillRect(255,270,18*5,26*5);
			g.fillRect(displaySize[0]/2-45,displaySize[1]/2-38,18*5,26*5);
			//g.fillRect(345,260,14*5,11*5);
			g.fillRect(displaySize[0]/2+45,displaySize[1]/2-48,14*5,11*5);
		} else {
			//g.fillRect(255,270,18*5,26*5);
			g.fillRect(displaySize[0]/2-45,displaySize[1]/2-38,18*5,26*5);
			//g.fillRect(185,260,14*5,11*5);
			g.fillRect(displaySize[0]/2-115,displaySize[1]/2-48,14*5,11*5);
		}
		for (int j=0;j<this.tileList.get(0).length;j++) {
			for (int i=0;i<this.tileList.size();i++) {
				if (i<Player.getCoordinates()[0]/50+displaySize[0]/50 && i>Player.getCoordinates()[0]/50-displaySize[0]/50){//render distance
					if (this.tileList.get(i)[j].equals("Z")) {
						g.setColor(new Color(0,0,0,50));
						g.fillRect(displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50,50);
					}
				}
			}
		}
		g.drawImage(this.waterMeter.getWaterMeter(),13,40,12*5/2,32*5/2,this);
		g.drawImage(this.heatMeter.getHeatMeter(),56,40,12*5/2,32*5/2,this);
		// center line
		//g.setColor(new Color(0,0,0,90));
		//g.fillRect(this.getWidth()/2-1,0,2,this.getHeight());
	}
	
	public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img,0,0,null);
        bGr.dispose();

        return bimage;
    }
}

class Background {
	private Image background;
	
	Background() {
		this.background = new ImageIcon("CAMELRUSH/assets/background (256x160).png").getImage();
	}
	
	public Image getBackground() {
		return this.background;
	}
}