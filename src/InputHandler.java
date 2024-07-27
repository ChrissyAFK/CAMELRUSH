import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class InputHandler extends JPanel implements KeyListener {
	// true if key is pressed, false if key is not pressed
	private boolean wKeyPressed = false;
	private boolean aKeyPressed = false;
	private boolean sKeyPressed = false;
	private boolean dKeyPressed = false;
	private boolean spaceKeyPressed = false;
	private boolean upKeyPressed = false;
	private boolean downKeyPressed = false;
	
	// to ensure the class can get key input
	InputHandler() {
		setFocusable(true);
		addKeyListener(this);
	}
	
	// returns if w is pressed
	public boolean wKeyPressed() {
		return this.wKeyPressed;
	}
	
	// returns if a is pressed
	public boolean aKeyPressed() {
		return this.aKeyPressed;
	}
	
	// returns if s is pressed
	public boolean sKeyPressed() {
		return this.sKeyPressed;
	}
	
	// returns if d is pressed
	public boolean dKeyPressed() {
		return this.dKeyPressed;
	}

	// return if space is pressed
	public boolean spaceKeyPressed() {
		return this.spaceKeyPressed;
	}

	// return if up arrow key is pressed
	public boolean upKeyPressed() {
		return this.upKeyPressed;
	}

	// return if down arrow key is pressed
	public boolean downKeyPressed() {
		return this.downKeyPressed;
	}
	
	// return if the player is moving
	public boolean inMotion() {
		return this.wKeyPressed||this.aKeyPressed||this.sKeyPressed||this.dKeyPressed;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	// when a key is pressed, instance variable is set to true
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			this.wKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
			this.aKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
			this.sKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
			this.dKeyPressed = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.spaceKeyPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.upKeyPressed = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.downKeyPressed = true;
		}
	}

	@Override
	// when a key is released, instance variable is set to false
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			this.wKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
			this.aKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
			this.sKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
			this.dKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.spaceKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.upKeyPressed = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.downKeyPressed = false;
        }
	}
	
}
