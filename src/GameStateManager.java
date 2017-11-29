/**
 * Handles the different GameStates such as Menu,
 * gamePlay & end-screen.
 * @author Miro, War  & Youtube-guy https://www.youtube.com/watch?v=9dzhgsVaiSo
 *
 */
public class GameStateManager {
	private GameState[] gameStates;
	private int currentState;
	
	/**
	 * Initiates the array holding all gamestates.
	 */
	public GameStateManager(){
		gameStates = new GameState[3];

		currentState = 0;			//CurrentState is 0, which is the MenuState.
		
		gameStates[0] = new MenuState(this);
		gameStates[1] = new PlayState(this);
		gameStates[2] = new EndState(this);
	}
	
	/**
	 * Let's a specific gameState change the current state.
	 * This allows the menuState to switch state to gameplay
	 * as a user presses "Start".
	 * @param stateIndex, which state to switch to.
	 */
	public void setState(int stateIndex){
		currentState = stateIndex;
		gameStates[currentState].init();
	}

	public void update(){
		gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g){
		gameStates[currentState].draw(g);
	}
	
	public void KeyPressed(int k){
		gameStates[currentState].keyPressed(k);
	}
}
