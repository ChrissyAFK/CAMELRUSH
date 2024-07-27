import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel {
	private Timer animateCamel;
	private Timer scrollTimer;
	private InputHandler input;
	private Player player;
	private TileCalculator tile;
	private int playerSpeed = 2;
	
	Display(InputHandler input,Player player) {
		this.setPreferredSize(new Dimension(600,600));
		setLayout(null); //44x32
		this.animateCamel = new Timer((1000/15),e->animate());
		this.animateCamel.start();
		this.scrollTimer = new Timer(1,e->scroll());
		this.scrollTimer.start();
		this.input = input;
		this.player = player;
		this.tile = new TileCalculator(System.currentTimeMillis());
	}
	
	private void animate() {
		this.player.changeCamelAnimation();
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
		ArrayList<String[]> tileList = tile.getViewingSlice();
		for (int i=0;i<tileList.size();i++) {
			g.drawImage(this.tile.getTile(),tile.getOffset()+50*(i-3),400,50,50,this);
		}
		//g.drawImage(this.player.getCurrentAnimation(),100+Player.getCoordinates()[0],100-Player.getCoordinates()[1],220,160,this);
		g.drawImage(this.player.getCurrentAnimation(),this.getWidth()/2-110,240,220,160,this);
	}
}
