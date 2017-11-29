import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * New JPanel with main game-loop and a keyListener.
 * @author Miro, War  & youtube-guy https://www.youtube.com/watch?v=9dzhgsVaiSo
 *
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

	public static final int width = 640;
	public static final int height = 480;

	private Thread thread;
	private boolean running;

	private BufferedImage img; //Based to draw on.
	private Graphics2D g; //Background && content.

	private GameStateManager gsm;

	public GamePanel(){	
		super();
		setPreferredSize(new Dimension(width,height));
		setFocusable(true);
		requestFocus();
	}
	/**
	 * Runs automatically when class instantiated.
	 * 
	 */
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();			//Calls run, which calls init(), update(), draw() & drawToScreen().
		}
	}
	/**
	 * 
	 */
	private void init(){
		gsm = new GameStateManager(); 
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //Grab context from newly created image & paint on variable g, the context.
		g = (Graphics2D) img.getGraphics();
		running = true;
	}

	/**
	 * Calls gamestatemanager, which in turn
	 * calls the current gamestate and updates
	 * any variables.
	 */
	private void update(){
		gsm.update();
	}
	/**
	 * Calls gamestatemanager, which calls
	 * the current gamestate which draws a painting
	 * unique to the gamestate.
	 */
	private void draw(){
		gsm.draw(g);
	}
	/**
	 * Draws the Graphics object to the JPanel. 
	 */
	private void drawToScreen(){
		Graphics g2 = getGraphics(); 
		g2.drawImage(img,0,0,width,height,null);
		g2.dispose();
	}


	long start;
	long elapsed;

	@Override
	public void run() {

		init();

		while(running){
			update();
			draw();
			drawToScreen();			
			try {

				Thread.sleep(1000/60);   //60 updates per second, 60 FPS.

			} catch (InterruptedException e) {	
				e.printStackTrace();
			}
		}

	} 

	/**
	 * In Snake, only keyPresses are relevant.
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		gsm.KeyPressed(arg0.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

}
