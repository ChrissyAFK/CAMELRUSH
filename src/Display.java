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
	
	Display(InputHandler input,Player player) {
		this.setPreferredSize(new Dimension(600,600));
		setLayout(null); //44x32
		
		this.startTime = System.currentTimeMillis();
		this.prevTime = this.startTime;
		this.fpsCounter = new JLabel("");
		this.fpsCounter.setFont(new Font("Dialog",Font.BOLD,20));
		this.fpsCounter.setBounds(10,10,120,20);
		this.add(fpsCounter);
		
		this.animateCamel = new Timer((1000/15),e->animate());
		this.animateCamel.start();
		this.scrollTimer = new Timer(1,e->scroll());
		this.scrollTimer.start();
		this.spitCooldown = new Timer((0),e->spitCooldown.stop());
		this.input = input;
		this.player = player;
		this.tile = new TileCalculator();
		this.projectiles = new ArrayList<>();
	}
	
	private void animate() {
		this.player.changeCamelAnimation();
		repaint();
	}
	
	private void scroll() {
		long currentTime = System.currentTimeMillis();
		//long delta = currentTime - this.prevTime;
		this.prevTime = currentTime;
		this.frameCount++;
		this.fpsCounter.setText("FPS: "+String.valueOf((double)Math.round(this.fps*100)/100));
		if (currentTime-this.startTime>1000) {
			this.fps = (double)this.frameCount/((currentTime-this.startTime)/1000.0);
			this.startTime = System.currentTimeMillis();
			this.frameCount = 0;
		}
		if (this.input.wKeyPressed()) {
			Player.changeYcoord(this.playerSpeed);
		}
		if (this.input.aKeyPressed()) {
			Player.changeXcoord(-this.playerSpeed);
		}
		if (this.input.sKeyPressed()) {
			Player.changeYcoord(-this.playerSpeed);
		}
		if (this.input.dKeyPressed()) {
			Player.changeXcoord(this.playerSpeed);
		}
		if (this.input.inMotion()) {
			Player.isMoving();
		} else {
			Player.isNotMoving();
		}
		Player.fall();
		if (this.input.spaceKeyPressed() && (!spitCooldown.isRunning())) {
			this.projectiles.add(new Projectile((new int[]{400,275}), 10, 1.0, 1, 1, "spit_ball (5x5).png", (new int[]{20, 20})));
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
		g.setColor(new Color(18, 192, 227));
		g.fillRect(0,0,600,600);
		ArrayList<String[]> tileList = new ArrayList<>();
		try {
			tileList = this.tile.getViewingSlice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int j=0;j<tileList.get(0).length;j++) {
			for (int i=0;i<tileList.size();i++) {
				//g.drawImage(this.tile.getSandTile(),this.tile.getOffset()+50*(i-3),400-50*j,50,50,this);
				if (i<Player.getCoordinates()[0]/50+15 && i>Player.getCoordinates()[0]/50-3){//render distance
					if (tileList.get(i)[j].equals("S")||tileList.get(i)[j].equals("W")) {
						if (CollisionHandler.willCollide(new int[]{i*50-Player.getCoordinates()[0],j*50-150+Player.getCoordinates()[1]},new int[]{},"Camel",new int[]{0,0})) {
							//System.out.println("Colliding with something");
						}
					}
					if (tileList.get(i)[j].equals("S")) {
						g.drawImage(this.tile.getSandTile(),i*50-Player.getCoordinates()[0],j*50-150+Player.getCoordinates()[1],50,50,this);
					} else if (tileList.get(i)[j].equals("W")) {
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
		g.setColor(new Color(255,0,0,90));
		g.fillRect(235,270,18*5,26*5);
		g.fillRect(240+28*5,260,14*5,11*5);
	}
}
