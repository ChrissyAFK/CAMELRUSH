import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel {
	private Timer animateCamel;
	private Timer scrollTimer;
	private InputHandler input;
	private Player player;
	private TileCalculator tile;
	private int playerSpeed = 2;
	private int frameCount;
	private double fps;
	private long delta;
	private long startTime;
	private long prevTime;
	private long currentTime;
	private JLabel fpsCounter;
	
	Display(InputHandler input,Player player) {
		this.setPreferredSize(new Dimension(600,600));
		setLayout(null); //44x32
		this.animateCamel = new Timer((1000/15),e->animate());
		this.animateCamel.start();
		this.scrollTimer = new Timer(1,e->scroll());
		this.scrollTimer.start();
		this.input = input;
		this.player = player;
		this.tile = new TileCalculator();
		
		this.startTime = System.currentTimeMillis();
		this.prevTime = this.startTime;
		this.fpsCounter = new JLabel("");
		this.fpsCounter.setBounds(10,10,120,20);
		this.add(fpsCounter);
	}
	
	private void animate() {
		this.player.changeCamelAnimation();
		this.currentTime = System.currentTimeMillis();
		this.delta = this.currentTime - this.prevTime;
		this.prevTime = this.currentTime;
		this.frameCount++;
		this.fpsCounter.setText(String.valueOf(this.fps));
		if (this.frameCount%10==0) {
			this.fps = this.frameCount/this.delta;
			this.startTime = System.currentTimeMillis();
			this.frameCount = 0;
		}
		repaint();
	}
	
	private void scroll() {
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
		for (int i=0;i<tileList.size();i++) {
			for (int j=0;j<tileList.get(i).length;j++) {
				//g.drawImage(this.tile.getSandTile(),this.tile.getOffset()+50*(i-3),400-50*j,50,50,this);
				if (tileList.get(i)[j].equals("S")) {
					g.drawImage(this.tile.getSandTile(),j*50-Player.getCoordinates()[0],i*50-150-Player.getCoordinates()[1],50,50,this);
				} else if (tileList.get(i)[j].equals("W")) {
					g.drawImage(this.tile.getWaterTile(),j*50-Player.getCoordinates()[0],i*50-150-Player.getCoordinates()[1],50,50,this);
				}
			}
		}
		//g.drawImage(this.player.getCurrentAnimation(),100+Player.getCoordinates()[0],100-Player.getCoordinates()[1],220,160,this);
		g.drawImage(this.player.getCurrentAnimation(),this.getWidth()/2-110,240,220,160,this);
	}
}
