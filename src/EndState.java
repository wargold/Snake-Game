import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author Miro, War  & Youtube-guy https://www.youtube.com/watch?v=9dzhgsVaiSo
 */
public class EndState extends GameState {

    private GameStateManager gsm;
    private HighScore highScore;

    public EndState(GameStateManager gsm) {

        this.gsm = gsm;
    }

    @Override
    public void init() {
        highScore = new HighScore();
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 640, 480);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", 90, 50);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
	g.drawString("Press enter to restart \n or space to quit", 90, 400);
	g.setColor(Color.BLUE);
	g.setFont(new Font("Arial", Font.BOLD, 30));
	g.drawString("HighScores", 90, 160);
	g.setFont(new Font("Arial", Font.BOLD, 20));

	int a = 0, b = 0, c = 0, pos = 1, y1 = 170, y2 = 170;

	ArrayList<HighScoreEntry> h = highScore.getHighscores();

	for (int i = 0; i<5;i++) { // Adds all the HighScores 
		try{
			g.drawString(i+1 + ". " + h.get(i).getName() + " :" + h.get(i).getScore(), 90, 195 + a);
		}catch(Exception e){
			break;
		}
                a += 25;
                y1 += 25;
    }
    }

    @Override
    public void keyPressed(int k) {
        switch (k) {
            case KeyEvent.VK_SPACE:
                System.exit(0);
                break;
            case KeyEvent.VK_ENTER:
                gsm.setState(1);
                break;
        }
    }

}
