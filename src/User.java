/**
 * The class Player.
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public class User extends Player {
	
	public User(String name, int ID, GamePanel gamePanel) {
		super(name, ID, gamePanel);
	}

	@Override
	public void act(TexasHoldem gui) {
		if (isAllIn) {
			return;
		}
		gui.userAction();
	}
}