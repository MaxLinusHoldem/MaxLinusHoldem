import java.util.Random;

/**
 * The class AI.
 * This class represents a AI player in the game.
 * 
 * @author Linus Wåreus & Max Wällstedt
 * @version 1.0 (2014.05.16)
 */
public class AI extends Player {
	// A random generator used to let the AI act randomly.
	private Random randomGenerator;

	/**
	 * Default constructor of the class AI.
	 * 
	 * @param name
	 *            The player's name.
	 * @param ID
	 *            The player's ID.
	 * @param gamePanel
	 *            The game panel of the game.
	 */
	public AI(String name, int ID, GamePanel gamePanel) {
		super(name, ID, gamePanel);
		randomGenerator = new Random();
	}

	/**
	 * Lets the AI act in the game.
	 * 
	 * @param gui
	 *            The GUI of the game.
	 */
	@Override
	public void act(TexasHoldem gui) {
		// If the AI is all-in, it wont act.
		if (isAllIn) {
			return;
		}

		// Simulates the AI thinking.
		TexasHoldem.delay(500);

		// Chooses how the AI will act.
		if (randomGenerator.nextInt(10) < 4) {
			try {
				bet(20, gui.getCurrentRaise());
			} catch (IllegalArgumentException e) {
				call(gui.getCurrentBet());
			}
		} else if (randomGenerator.nextInt(10) < 2) {
			fold(gui);
		} else {
			call(gui.getCurrentBet());
		}
	}
}