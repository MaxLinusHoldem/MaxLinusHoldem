/**
 * The class User.
 * This class represents a user in the game.
 * 
 * @author Linus Wåreus
 * @version 1.0 (2014.05.16)
 */
public class User extends Player {

	/**
	 * Default constructor of the class User.
	 * 
	 * @param name
	 *            The player's name.
	 * @param ID
	 *            The player's ID.
	 * @param gamePanel
	 *            The game panel of the game.
	 */
	public User(String name, int ID, GamePanel gamePanel) {
		super(name, ID, gamePanel);
	}

	/**
	 * Lets the user act in the game.
	 * 
	 * @param gui
	 *            The GUI of the game.
	 */
	@Override
	public void act(TexasHoldem gui) {
		// If the user is all-in, he or she wont act.
		if (isAllIn) {
			return;
		}
		gui.userAction();
	}
}