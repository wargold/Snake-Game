import javax.swing.*;
/**
 * Main game class, creates & sets JFrame
 * preferences, instantiates a new GamePanel
 * which will be used to draw on. 
 * @author Miro, War & Youtube-guy https://www.youtube.com/watch?v=9dzhgsVaiSo.
 *
 */
public class Game {
	public static void main(String[] args) {

		JFrame frame = new JFrame("Snake");
		frame.setContentPane(new GamePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
}