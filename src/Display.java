import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel {
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
	
	Display(InputHandler input,Player player) throws Exception {
		this.setPreferredSize(new Dimension(600,600));
		setLayout(null); //44x32
		
		this.startTime = System.currentTimeMillis();
		this.prevTime = this.startTime;
		this.fpsCounter = new JLabel("");
		this.fpsCounter.setFont(new Font("Dialog",Font.BOLD,20));
		this.fpsCounter.setBounds(10,10,120,20);
		this.add(fpsCounter);
		this.tile = new TileCalculator();
		this.tileList = this.tile.getViewingSlice();
		
		this.animateCamel = new Timer((1000/15),e->animate());
		this.animateCamel.start();
		this.scrollTimer = new Timer((0),e->scroll());
		this.scrollTimer.start();
		this.spitCooldown = new Timer((1000),e->spitCooldown.stop());
		this.spitCooldown.start();
		this.input = input;
		this.player = player;
		this.projectiles = new ArrayList<>();
	}
	
	private void animate() {
		this.player.changeCamelAnimation();
		this.tile.changeAnimationFrame();
		repaint();
	}
	
	private void scroll() {
		long currentTime = System.currentTimeMillis();
		long delta = currentTime - this.prevTime;
		System.out.println(delta);
		this.prevTime = currentTime;
		this.frameCount++;
		this.fpsCounter.setText("FPS: "+String.valueOf((double)Math.round(this.fps*100)/100));
		if (currentTime-this.startTime>1000) {
			this.fps = (double)this.frameCount/((currentTime-this.startTime)/1000.0);
			this.startTime = System.currentTimeMillis();
			this.frameCount = 0;
		}
		if (this.input.wKeyPressed()&&!Player.getFallingStatus()) {
			Player.setVelocityY(10);
		}
		if (this.input.aKeyPressed()) {
			Player.setVelocityX(-this.playerSpeed*delta/10);
		}
		if (this.input.dKeyPressed()) {
			Player.setVelocityX(this.playerSpeed*delta/10);
		}
		if (!this.input.aKeyPressed()&&!this.input.dKeyPressed()) {
			Player.setVelocityX(0);
		}
		if (this.input.inMotion()) {
			Player.isMoving();
		} else {
			Player.isNotMoving();
		}
		
		if (!CollisionHandler.isColliding(this.tileList,"y")) {
			Player.isFalling();
		} else {
			Player.isNotFalling();
		}
		
		if (!CollisionHandler.isColliding(this.tileList)) {
			Player.updateXCoordinates();
		}
		Player.updateYCoordinates();
		Player.fall(delta);
		if (this.input.spaceKeyPressed() && (!spitCooldown.isRunning())) {
			this.projectiles.add(new Projectile((new int[]{400,275}),10,1.0,1,1,"spit_ball (5x5).png",(new int[]{20,20})));
			this.spitCooldown = new Timer((1000),e->spitCooldown.stop());
			this.spitCooldown.start();
		}
		for (Projectile projectile : projectiles) {
			projectile.move();
		}
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(new Color(158, 250, 255));
		g.fillRect(0,0,600,600);
		try {
			this.tileList = this.tile.getViewingSlice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int j=0;j<this.tileList.get(0).length;j++) {
			for (int i=0;i<this.tileList.size();i++) {
				//g.drawImage(this.tile.getSandTile(),this.tile.getOffset()+50*(i-3),400-50*j,50,50,this);
				if (i<Player.getCoordinates()[0]/50+15 && i>Player.getCoordinates()[0]/50-3){//render distance
					if (this.tileList.get(i)[j].equals("S")) {
						g.drawImage(this.tile.getSandTile(),i*50-Player.getCoordinates()[0],j*50-150+Player.getCoordinates()[1],50,50,this);
					} else if (this.tileList.get(i)[j].equals("W")) {
						g.drawImage(this.tile.getWaterTile(),i*50-Player.getCoordinates()[0],j*50-150+Player.getCoordinates()[1],50,50,this);
					}
				}
			}
		}
		//g.drawImage(this.player.getCurrentAnimation(),100+Player.getCoordinates()[0],100-Player.getCoordinates()[1],220,160,this);
		g.drawImage(this.player.getCurrentAnimation(),this.getWidth()/2-110,240,220,160,this);
		for (Projectile proj : projectiles) {
			g.drawImage(proj.getSprite(), proj.getCoordinates()[0], proj.getCoordinates()[1], proj.getSize()[0], proj.getSize()[1], this);
		}	
		//hitboxes
		g.setColor(new Color(255,0,0,90));
		g.fillRect(200+25*5-2,260,14*5,11*5);
		g.fillRect(235,270,18*5-3,26*5);
	}
}
