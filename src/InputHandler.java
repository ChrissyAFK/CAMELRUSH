import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class InputHandler extends JPanel implements KeyListener {
	// true if key is pressed, false if key is not pressed
	private boolean wKeyPressed = false;
	private boolean aKeyPressed = false;
	private boolean sKeyPressed = false;
	private boolean dKeyPressed = false;
	private boolean eKeyPressed = false;
	private boolean spaceKeyPressed = false;
	private boolean upKeyPressed = false;
	private boolean downKeyPressed = false;
	private boolean altKeyPressed = false;
	
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

	// returns if e is pressed
	public boolean eKeyPressed() {
		return this.eKeyPressed;
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
	public boolean altKeyPressed(){
		return this.altKeyPressed;
	}
	
	// return if the player is moving
	public boolean inMotion() {
		return this.wKeyPressed||this.aKeyPressed||this.dKeyPressed;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	// when a key is pressed, instance variable is set to true
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
        	case KeyEvent.VK_W -> this.wKeyPressed = true;
            case KeyEvent.VK_A -> this.aKeyPressed = true;
            case KeyEvent.VK_S -> this.sKeyPressed = true;
            case KeyEvent.VK_D -> this.dKeyPressed = true;
			case KeyEvent.VK_E -> this.eKeyPressed = true;
            case KeyEvent.VK_SPACE -> this.spaceKeyPressed = true;
			case KeyEvent.VK_UP -> this.upKeyPressed = true;
			case KeyEvent.VK_DOWN -> this.downKeyPressed = true;
			case KeyEvent.VK_ALT -> this.altKeyPressed = true;
        }
	}

	@Override
	// when a key is released, instance variable is set to false
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
    	case KeyEvent.VK_W -> this.wKeyPressed = false;
        case KeyEvent.VK_A -> this.aKeyPressed = false;
        case KeyEvent.VK_S -> this.sKeyPressed = false;
        case KeyEvent.VK_D -> this.dKeyPressed = false;
		case KeyEvent.VK_E -> this.eKeyPressed = false;
        case KeyEvent.VK_SPACE -> this.spaceKeyPressed = false;
		case KeyEvent.VK_UP -> this.upKeyPressed = false;
		case KeyEvent.VK_DOWN -> this.downKeyPressed = false;
		case KeyEvent.VK_ALT -> this.altKeyPressed = false;
        }
	}
}
