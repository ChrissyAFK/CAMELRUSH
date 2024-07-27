import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class InputHandler extends JPanel implements KeyListener {
	private boolean wKeyPressed = false;
	private boolean aKeyPressed = false;
	private boolean sKeyPressed = false;
	private boolean dKeyPressed = false;
	
	InputHandler() {
		this.setPreferredSize(new Dimension(600,600));
		setFocusable(true);
		addKeyListener(this);
	}
	
	public boolean wKeyPressed() {
		return this.wKeyPressed;
	}
	
	public boolean aKeyPressed() {
		return this.aKeyPressed;
	}
	
	public boolean sKeyPressed() {
		return this.sKeyPressed;
	}
	
	public boolean dKeyPressed() {
		return this.dKeyPressed;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			this.wKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
			this.aKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
			this.sKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
			this.dKeyPressed = true;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			this.wKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
			this.aKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
			this.sKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
			this.dKeyPressed = false;
        }
	}
	
}
