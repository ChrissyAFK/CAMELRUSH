import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel {
	Timer animateCamel;
	InputHandler input;
	Player player;
	TileCalculator tile;
	
	Display(InputHandler input,Player player) {
		this.setPreferredSize(new Dimension(600,600));
		setLayout(null); //44x32
		this.animateCamel = new Timer((1000/15),e->animate());
		this.animateCamel.start();
		this.input = input;
		this.player = player;
		this.tile = new TileCalculator(System.currentTimeMillis());
	}
	
	private void animate() {
		if (this.input.wKeyPressed()) {
			Player.changeYcoord(3);
		}
		if (this.input.aKeyPressed()) {
			Player.changeXcoord(-3);
		}
		if (this.input.sKeyPressed()) {
			Player.changeYcoord(-3);
		}
		if (this.input.dKeyPressed()) {
			Player.changeXcoord(3);
		}
		if (this.input.inMotion()) {
			Player.isMoving();
		} else {
			Player.isNotMoving();
		}
		this.player.changeCamelAnimation();
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(Color.blue);
		g.fillRect(0,0,600,600);
		ArrayList<String[]> tileList = tile.getViewingSlice();
		for (int i=0;i<tileList.size();i++) {
			g.drawImage(this.tile.getTile(),tile.getOffset()+50*(i-3),400,50,50,this);
		}
		g.drawImage(this.player.getCurrentAnimation(),100+Player.getCoordinates()[0],100-Player.getCoordinates()[1],220,160,this);
	}
}
