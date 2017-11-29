import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * The GameState representing our main menu.
 * @author Miro & War.
 *
 */
public class MenuState extends GameState {

	private BufferedImage backGround;

	private GameStateManager gsm;
	private String[] menuOptions = {"Start", "Quit"}; //Array containing the two menu options.
	private int currentOption = 0; //Current option is set to 0, which is the "Start" String in the above.

		public MenuState(GameStateManager gsm){
		this.gsm = gsm;	

		try{
			backGround = ImageIO.read(new File("Images/SnakeBG.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 640, 480);

		g.drawImage(backGround, 250,100,null);

		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.drawString("Snake", 80, 70);
		
		g.setFont(new Font("Arial",Font.BOLD,40));

		for(int i = 0; i < menuOptions.length;i++){
			if(currentOption==i){
				g.setColor(Color.RED);
				g.drawString(menuOptions[i], 140,140 +i*45 );
			}
			else{
				g.setColor(Color.GREEN);
			g.drawString(menuOptions[i], 140, 140 + i*45);
			}
				
		}
	}

	@Override
	public void keyPressed(int k) {

		if(k==KeyEvent.VK_UP){
			if(currentOption==0)
				currentOption = 1;
			else
				currentOption--;
		}

		if(k == KeyEvent.VK_DOWN)
			if(currentOption==1)
				currentOption = 0;
			else
				currentOption++;


		if(k == KeyEvent.VK_ENTER)
			if(currentOption==0)
				gsm.setState(1);
			else
				System.exit(0);
	}

}
