/**
 * An abstract class that represents all
 * gamestates such as menu, gameplay & endscreen.
 * Used to enable storing menu, gameplay & endscreen
 * in the same datastructure, seen in GameStateManager class.
 * @author Miro, War  & Youtube-guy https://www.youtube.com/watch?v=9dzhgsVaiSo
 *
 */
public abstract class GameState {

	private GameStateManager gsm;

	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
}
