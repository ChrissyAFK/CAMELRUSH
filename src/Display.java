import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Display extends JPanel {
	public static int[] displaySize;
	private Background background;
	private List<String> levels;
	private int currentLevel;
	//private Timer levelSwitchCooldown;
	private Timer animateCamel;
	private Timer scrollTimer;
	private Timer spitCooldown;
	private InputHandler input;
	private Player player;
	private Enemies enemy;
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
	private Timer stopwatchTimer;
	private long stopwatchStartTime;
	private long elapsedStopwatchTime;
	private JLabel stopwatchLabel;
	private boolean leaderboardhasupdated = false;
	Display(InputHandler input,Player player, Enemies enemyr) throws Exception {
		this.setPreferredSize(new Dimension(1280,700));
		displaySize = new int[]{this.getWidth(),this.getHeight()};
		//Player.setCoordinates(new int[]{0,displaySize[1]/2-250});
		//Player.setCoordinates(new int[]{0,displaySize[1]/250});
		Player.setCoordinates(new int[]{0,-200});
		setLayout(null); //44x32
		stopwatchTimer = new Timer(1000, e -> updateStopwatch());
        elapsedStopwatchTime = 0;
		stopwatchLabel = new JLabel("0:00");
		stopwatchLabel.setBounds(10, 40, 200, 20);
    	this.add(stopwatchLabel);
		this.background = new Background();
		this.enemy = enemyr;
		this.startTime = System.currentTimeMillis();
		this.prevTime = this.startTime;
		this.fpsCounter = new JLabel("");
		this.fpsCounter.setFont(new Font("Dialog",Font.BOLD,20));
		this.fpsCounter.setBounds(10,10,120,20);
		this.add(fpsCounter);
		this.player = player;
		this.tile = new TileCalculator();
		this.levels = new ArrayList<>();
		levels.add("flevel01.txt");
		levels.add("flevel02.txt");
		levels.add("flevel03.txt");
		//Collections.shuffle(this.levels);
		levels.add("flevel0b.txt");
		this.currentLevel = 2;
		this.tileList = this.tile.getViewingSlice(levels.get(currentLevel));
		this.waterMeter = new WaterMeter(); 
		this.heatMeter = new OverheatMeter();
		//this.levelSwitchCooldown = new Timer(1000, e->this.levelSwitchCooldown.stop());
		this.animateCamel = new Timer(1000/15,e->animate());
		this.animateCamel.start();
		this.scrollTimer = new Timer(1000/600,e->scroll());
		this.scrollTimer.start();
		this.spitCooldown = new Timer(1000,e->spitCooldown.stop());
		this.spitCooldown.start();
		this.input = input;
		this.projectiles = new ArrayList<>();
		this.enemy.startMoving(2,0);
		startStopwatch();
	}
	public void startStopwatch() {
		stopwatchStartTime = System.currentTimeMillis();
		elapsedStopwatchTime = 0; // Reset elapsed time when starting
		stopwatchTimer.start();
	}

	public void resetStopwatch() {
		elapsedStopwatchTime = 0;
	}

	private void updateStopwatch() {
		long currentTime = System.currentTimeMillis();
		long timeElapsed = elapsedStopwatchTime + (currentTime - stopwatchStartTime);
		long totalSeconds = timeElapsed / 1000;
		long minutes = totalSeconds / 60;
		long seconds = totalSeconds % 60;
		
		String formattedTime = String.format("%d:%02d", minutes, seconds);
		
		stopwatchLabel.setText("Stopwatch Time: " + formattedTime);
		if (currentLevel == 3 && !leaderboardhasupdated) {
			try {
				leaderboard.insertdata("TEST", formattedTime);
				leaderboardhasupdated = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void animate() {
		this.player.changeCamelAnimation();
		this.tile.changeAnimationFrame();
		this.enemy.changeEnemyAnimation();
		repaint();
	}
	
	private void scroll() {
		displaySize = new int[]{this.getWidth(),this.getHeight()};
		long currentTime = System.currentTimeMillis();
		//long delta = currentTime - this.prevTime;
		//System.out.println(delta);
		this.prevTime = currentTime;
		updateStopwatch();
		this.frameCount++;
		this.fpsCounter.setText("FPS: "+String.valueOf((double)Math.round(this.fps*100)/100));
		if (currentTime-this.startTime>1000) {
			this.fps = (double)this.frameCount/((currentTime-this.startTime)/1000.0);
			this.startTime = System.currentTimeMillis();
			this.frameCount = 0;
		}
		if (this.input.wKeyPressed()&&!Player.getFallingStatus()&&!Player.drinking()) {
			this.player.resetJumpingAnimation();
			Player.setVelocityY(10);
		}
		if (this.input.aKeyPressed()&&!Player.drinking()) {
			Player.setVelocityX(-this.playerSpeed);
			Player.isFacingLeft();
		}
		if (this.input.dKeyPressed()&&!Player.drinking()) {
			Player.setVelocityX(this.playerSpeed);
			Player.isFacingRight();
		}
		if (!this.input.aKeyPressed()&&!this.input.dKeyPressed()&&!Player.drinking()) {
			Player.setVelocityX(0);
		}
		if (this.input.shiftKeyPressed()&&currentTime>shiftCooldownEndTime) {
			shiftActiveEndTime = currentTime+500; // 0.5 seconds
			shiftCooldownEndTime = currentTime+1000; // 1 second cooldown
		}
		if (currentTime<shiftActiveEndTime&&(this.input.aKeyPressed()||this.input.dKeyPressed())) {
			this.player.startDashingAnimation();
			Player.setVelocityX(Player.getVelocity()[0]*6);
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
			if (this.player.inDrinkingInProgress()) {
				this.waterMeter.drink();
			}
			this.player.isDrinking();
		} else {
			this.player.isNotDrinking();
		}
		if (this.input.inMotion()) {
			Player.isMoving();
		} else {
			Player.isNotMoving();
		}
		
		if (!CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"S","y") && 
			!CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"V","y") && 
			!CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"P","y")){
			Player.isFalling();
		} else {
			Player.isNotFalling();
		}
		
		if (!CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"S","") && 
			!CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"V","") && 
			!CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"P","")){
			Player.updateXCoordinates();
		}
		if (CollisionHandler.isColliding(this.tileList,"Camel Body",Player.getCoordinates(),Player.getVelocity(),"Z","")||
				CollisionHandler.isColliding(this.tileList,"Camel Body",Player.getCoordinates(),Player.getVelocity(),"B","")||
				CollisionHandler.isColliding(this.tileList,"Camel Body",Player.getCoordinates(),Player.getVelocity(),"T","")){
			Player.cooling();
		} else {
			Player.heating();
		}
		Player.updateYCoordinates();
		if (!CollisionHandler.isColliding(this.tileList,"Enemy Cactus vs Tile",enemy.getCoordinates(),enemy.getVelocity(),"S","y")) {
			enemy.isFalling();
		} else {
			enemy.isNotFalling();
		}
		enemy.updateYPosition();
		/*if (CollisionHandler.isColliding(this.tileList,"Enemy Cactus vs Tile",enemy.getCoordinates(),enemy.getVelocity(),"S","")) {
			enemy.stopMoving();
		} else {
			enemy.startMoving(2,0);
		}*/
		enemy.updateXPosition();
		Player.fall();
		enemy.Enemyfall();
		if (this.input.spaceKeyPressed()&&(this.waterMeter.getAmount()>=20.0)&&(!this.spitCooldown.isRunning())&&!Player.drinking()) {
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
		if (CollisionHandler.isColliding(this.tileList,"Camel",Player.getCoordinates(),Player.getVelocity(),"X","")/*&&!this.levelSwitchCooldown.isRunning()*/){
			Player.setCoordinates(new int[]{0,displaySize[1]/2-300});
			this.currentLevel++;
			if (this.currentLevel==levels.size()){
				currentLevel = 0;
			}
			if (this.currentLevel == 3){
				Player.setCoordinates(new int[]{1550,displaySize[1]/2-700});
				background.setBossLvl();
			}
		}
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0,0,displaySize[0],displaySize[1]);
		g.setColor(new Color(200,255,250));
		g.fillRect(0,0,displaySize[0],displaySize[1]);
		for (int i=0;i<(displaySize[0]/(256*5/2))+1;i++) {
			g.drawImage(this.background.getBackground(),i*256*5-(Player.getCoordinates()[0]/4)%(256*5),(Player.getCoordinates()[1]*6/5)+250,256*5,160*5,this);
			if (currentLevel == 3) {
				g.drawImage(this.background.getBackground(),i*256*5-(Player.getCoordinates()[0]/4)%(256*5),(Player.getCoordinates()[1]*6/5)-144*5+250,256*5,144*5,this);
			}
		}

		
		try {
			this.tileList = this.tile.getViewingSlice(levels.get(currentLevel));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
		for (int j=0;j<this.tileList.get(0).length;j++) {
			for (int i=0;i<this.tileList.size();i++) {
				if (i<Player.getCoordinates()[0]/50+displaySize[0]/50 && i>Player.getCoordinates()[0]/50-displaySize[0]/50){//render distance
					if (this.tileList.get(i)[j].equals("S")) {
						g.drawImage(this.tile.getSandTile(),displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50,50,this);
					} else if (this.tileList.get(i)[j].equals("W")) {
						g.drawImage(this.tile.getWaterTile(),displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50,50,this);
					} else if (this.tileList.get(i)[j].equals("B")) {
						g.drawImage(this.tile.getPalmBase(),displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-200+Player.getCoordinates()[1],50*4,100,this);
					} else if (this.tileList.get(i)[j].equals("T")) {
						g.drawImage(this.tile.getPalmTrunk(),displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50*4,50,this);
					} else if (this.tileList.get(i)[j].equals("H")) {
						g.drawImage(this.tile.getPalmHead(),displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-210+Player.getCoordinates()[1],50*4,110,this);
					} else if (this.tileList.get(i)[j].equals("X")) {
						g.setColor(new Color(255,0,0,90));
						g.fillRect(displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50,50);
					} else if (this.tileList.get(i)[j].equals("V")) {
						g.drawImage(this.tile.getSandBrickTile(),displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50,50,this);
					}
				}
			}
		}
		for (Projectile proj:this.projectiles) {
			g.drawImage(proj.getSprite(),proj.getCoordinates()[0],proj.getCoordinates()[1],proj.getSize()[0],proj.getSize()[1],this);
		}
		if (Player.facingRight()) {
			g.drawImage(this.player.getCurrentAnimation(),displaySize[0]/2-95,displaySize[1]/2-68,220,160,this);
			enemy.faceRight();
		} else {
			g.drawImage(this.player.getCurrentAnimation(),displaySize[0]/2+95,displaySize[1]/2-68,-220,160,this);
			enemy.faceLeft();
		}
		if (enemy.getEnemyImage()!=null) {
			if (enemy.facingRight()) {
				g.drawImage(enemy.getEnemyCurrentAnimation(),displaySize[0]/2+enemy.getCoordinates()[0]-Player.getCoordinates()[0],displaySize[1]/2+enemy.getCoordinates()[1]+Player.getCoordinates()[1],24*5,24*5,this);
				g.setColor(new Color(255,0,0,50));
				g.fillRect(displaySize[0]/2+enemy.getCoordinates()[0]-Player.getCoordinates()[0]+5*5,displaySize[1]/2+enemy.getCoordinates()[1]+Player.getCoordinates()[1]+5*5,15*5,19*5);
			} else {
				g.drawImage(enemy.getEnemyCurrentAnimation(),displaySize[0]/2+enemy.getCoordinates()[0]-Player.getCoordinates()[0],displaySize[1]/2+enemy.getCoordinates()[1]+Player.getCoordinates()[1],-24*5,24*5,this);g.setColor(new Color(255,0,0,50));
				g.fillRect(displaySize[0]/2+enemy.getCoordinates()[0]-Player.getCoordinates()[0]+5*5,displaySize[1]/2+enemy.getCoordinates()[1]+Player.getCoordinates()[1]+5*5,15*5,19*5);
			}
		}
		// hitboxes
		/*g.setColor(new Color(255,0,0,90));
		if (Player.facingRight()) {
			g.fillRect(displaySize[0]/2-45,displaySize[1]/2-38,18*5,26*5);
			g.fillRect(displaySize[0]/2+45,displaySize[1]/2-48,14*5,11*5);
		} else {
			g.fillRect(displaySize[0]/2-45,displaySize[1]/2-38,18*5,26*5);
			g.fillRect(displaySize[0]/2-115,displaySize[1]/2-48,14*5,11*5);
		}*/
		/*for (int j=0;j<this.tileList.get(0).length;j++) {
			for (int i=0;i<this.tileList.size();i++) {
				if (i<Player.getCoordinates()[0]/50+displaySize[0]/50 && i>Player.getCoordinates()[0]/50-displaySize[0]/50){//render distance
					if (this.tileList.get(i)[j].equals("Z")||this.tileList.get(i)[j].equals("B")||this.tileList.get(i)[j].equals("T")) {
						g.setColor(new Color(0,0,0,50));
						g.fillRect(displaySize[0]/2+i*50-Player.getCoordinates()[0],displaySize[1]/2+j*50-150+Player.getCoordinates()[1],50,50);
					}
				}
			}
		}*/
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

	public void setBossLvl () {
		this.background = new ImageIcon("CAMELRUSH/assets/boss_background (256x144).png").getImage();
	}

}