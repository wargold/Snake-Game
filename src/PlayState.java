import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 * The GameState representing the Snake-gameplay itself.
 *
 * @author Miro, War  & Youtube-guy https://www.youtube.com/watch?v=9dzhgsVaiSo
 */
public class PlayState extends GameState {


	public enum foodType{
		ORDINARY, TRICK, RESET
	}

	public enum Direction{
		LEFT, UP, RIGHT, DOWN
	}

	private static final int bodySize = 15;
	private int speed = 10;
	private int score;
	private HighScore highScore;
	private foodType currentFood; //Keeps track of current foodType.
	private foodType nextFood; //Keeps track of next foodType.


	private static BufferedImage headIMG;
	private static BufferedImage bodyIMG;
	private static BufferedImage tailIMG;
	private static BufferedImage foodIMG;
	private static BufferedImage pupIMG; //Powerup image.
	private static BufferedImage resetIMG; //Powerup image.

	private Point foodLocation;
	private Point headLocation;
	private Direction direction;

	private Random gen;
	private GameStateManager gsm;
	private LinkedList<Point> bodyLocations;  //Stores all the Point objects that represent the (x,y) location of a bodypart.
	private MusicPlayer musicPlayer;

	public PlayState(GameStateManager gsm) {

		this.gsm = gsm;
		initiateImages();
		musicPlayer = new MusicPlayer();
		musicPlayer.playMainSong();
		musicPlayer.stopMusic();
		gen = new Random();
		bodyLocations = new LinkedList<Point>();
		highScore = new HighScore();

	}


	@Override
	public void init() {

		currentFood = foodType.ORDINARY;
		nextFood = foodType.ORDINARY;

		direction = Direction.LEFT;
		score = 0;

		bodyLocations.removeAll(bodyLocations);
		headLocation = new Point(300, 300);
		bodyLocations.add(headLocation);

		setFoodLocation();
		newBody();
		newBody();

	}

	/**
	 * Initiates the images.
	 */
	private void initiateImages() {
		try {

			foodIMG = ImageIO.read(new File("Images/Food.png"));
			tailIMG = ImageIO.read(new File("Images/Tail.png"));
			bodyIMG = ImageIO.read(new File("Images/SnakeBody.png"));
			headIMG = ImageIO.read(new File("Images/SnakeHead.png"));
			pupIMG =  ImageIO.read(new File("Images/SnakePUP.png"));
			resetIMG = ImageIO.read(new File("Images/Lucky.png"));

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	/**
	 * Update position of snake
	 */
	private void updatePos() {
		for (int i = bodyLocations.size() - 1; 0 < i; i--) {
			bodyLocations.get(i).x = bodyLocations.get(i - 1).x;
			bodyLocations.get(i).y = bodyLocations.get(i - 1).y;
		}
		moveHead();
	}

	/**
	 * Returns the current score.
	 *
	 * @return Current score
	 */
	private int getScore() {
		return score;
	}
	/**
	 * Restricts Snake movement to gameboard.
	 *
	 * @param deltaX, distance in x-coordinates.
	 * @param deltaY, distance in y-coordinates.
	 */
	private void move(int deltaX, int deltaY) {
		int componentWidth = bodySize;
		int componentHeight = componentWidth;

		int parentWidth = 640;
		int parentHeight = 480;

		// Determine next X position
		int nextX = Math.max(headLocation.x + deltaX, 0);
		if (nextX + componentWidth > parentWidth) {
			nextX = parentWidth - componentWidth;
		}

		// Determine next Y position
		int nextY = Math.max(headLocation.y + deltaY, 0);
		if (nextY + componentHeight > parentHeight) {
			nextY = parentHeight - componentHeight;
		}
		headLocation.setLocation(nextX, nextY);  // Move the object
	}

	/**
	 * Moves the head depending on the current direction.
	 */
	private void moveHead() {

		switch (direction) {

			case LEFT:
				move(-speed, 0);
				break;
			case UP:
				move(0, -speed);
				break;
			case RIGHT:
				move(speed, 0);
				break;
			case DOWN:
				move(0, speed);
				break;
		}
	}

	/**
	 * Helper function, checks distance to Point p.
	 *
	 * @param p    given Point.
	 * @param dist given distance to Point p.
	 * @return true if distance is <= dist, false if not.
	 */
	private boolean distance(Point p, int dist) { if (Math.abs(headLocation.distance(p)) <= dist)
		return true;
		return false;
	}

	/**
	 * Distance to food. Increases the score with 10 points when food
	 * have been eaten and plays the impact song.
	 */
	private void food() {
		if (distance(foodLocation, bodySize)) {

			if(score<=100){
				setFoodLocation();
				newBody();
				score += 10;
				musicPlayer.playImpactSong();
			}

			else{
				currentFood = nextFood;
				randomFood();
				setFoodLocation();
				newBody();

				if(currentFood == foodType.TRICK)
					score+=100;

				else if(currentFood == foodType.RESET){
					for(int i = bodyLocations.size()-1;i>2;i--)
						bodyLocations.remove(i);
				}
	
				else{
					score+=10;
				}

				musicPlayer.playImpactSong();
			}
		}

	}

	/**
	 * If a player reaches a score of over 100,
	 * different foodtypes will be introduced that
	 * make the game slightly harder.
	 */
	private void randomFood(){

		switch(gen.nextInt(5)){

			case 1:
				nextFood = foodType.TRICK;
				break;

			case 2:
				if(gen.nextInt(5)==0)
					nextFood = foodType.RESET;
				break;
				
			default:
				nextFood = foodType.ORDINARY;
				break;
		}
	}

	/**
	 * Determines if snake has crashed.
	 */
	private boolean crash() {
		for (int i = 1; i < bodyLocations.size(); i++) {
			Point body = bodyLocations.get(i);
			if (distance(body, 0)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create a new point for a body.
	 */
	private void newBody() {
		Point lastBody = bodyLocations.getLast();

		switch (direction) {

			case LEFT:
				bodyLocations.addLast(new Point(lastBody.x + bodySize, lastBody.y));
				break;

			case UP:
				bodyLocations.addLast(new Point(lastBody.x, lastBody.y + bodySize));
				break;

			case DOWN:
				bodyLocations.addLast(new Point(lastBody.x, lastBody.y - bodySize));
				break;

			case RIGHT:
				bodyLocations.addLast(new Point(lastBody.x - bodySize, lastBody.y));
				break;
		}
	}

	/**
	 * Starts to play the main song after the score is equal to 30.
	 */
	private void checkHighScore() {
		if (getScore() == 30 && !musicPlayer.ifPlaying()) {
			musicPlayer.playMainSong();
		}
	}

	/**
	 * Adds the current score to the Highscore list, and also the user name.
	 */
	private void addScore() {

		String name = JOptionPane.showInputDialog(null, "What's your name?");

		highScore.addHighscore(name, getScore());
		highScore.saveHighscores();
		highScore.sortHighscores();
	}

	/**
	 * Returns the current High score.
	 *
	 * @return current High score
	 */
	private int getHighScore() {
		if (highScore.getHighscores().size() == 0) {
			return 0;
		} else {
			return highScore.getHighscores().get(0).getScore();
		}
	}

	/**
	 * Returns the name of that person who set the high score.
	 *
	 * @return name of a person
	 */
	private String getHighScoreName() {
		if (highScore.getHighscores().size() == 0) {
			return "No body";
		} else {
			return highScore.getHighscores().get(0).getName();
		}
	}

	/**
	 * Randomly set location of the food.
	 */
	private void setFoodLocation() {
		int x = gen.nextInt(500);
		int y = gen.nextInt(450);
		foodLocation = new Point(x, y);

	}

	@Override
	public void update() {
		updatePos();
		food();
		checkHighScore();
		if (crash()) {
			musicPlayer.stopMusic();
			addScore();
			gsm.setState(2);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 640, 480);
		for (int i = 1; i < bodyLocations.size() - 1; i++) {
			Point body = bodyLocations.get(i);
			g.drawImage(bodyIMG, body.x, body.y, null);
		}

		g.drawImage(tailIMG, bodyLocations.getLast().x, bodyLocations.getLast().y, null);
		g.drawImage(headIMG, headLocation.x, headLocation.y, null);

		switch(nextFood){

			case ORDINARY:
				g.drawImage(foodIMG, foodLocation.x, foodLocation.y, null);
				break;

			case TRICK:
				g.drawImage(pupIMG, foodLocation.x, foodLocation.y,null);
				break;
			case RESET:
				g.drawImage(resetIMG, foodLocation.x, foodLocation.y,null);
		}

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		g.drawString("Total Score: " + getScore(), 500, 450);
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		g.drawString("HighScore " + getHighScoreName() + " :" + getHighScore(), 500, 470);
	}

	@Override
	public void keyPressed(int k) {
		switch (k) {
			case KeyEvent.VK_UP:
				if(currentFood != foodType.TRICK){
					if(direction != Direction.DOWN)
						direction = Direction.UP;
				}

				else if (direction != Direction.UP) {
					direction = Direction.DOWN;
				}
				break;

			case KeyEvent.VK_DOWN:
				if(currentFood != foodType.TRICK){
					if(direction != Direction.UP)
						direction = Direction.DOWN;
				}

				else if (direction != Direction.DOWN) {
					direction = Direction.UP;				
				}
				break;

			case KeyEvent.VK_RIGHT:
				if(currentFood != foodType.TRICK){
					if(direction != Direction.LEFT)
						direction = Direction.RIGHT;
				}

				else if (direction != Direction.RIGHT) {
					direction = Direction.LEFT;
				}
				break;

			case KeyEvent.VK_LEFT:

				if(currentFood != foodType.TRICK){
					if(direction != Direction.RIGHT)
						direction = Direction.LEFT;
				}
				else if (direction != Direction.LEFT) {
					direction = Direction.RIGHT;
				}

				break;

		}
	}
}
