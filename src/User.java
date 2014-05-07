/**
 * The class Player.
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public class User extends Player {
	
	User(int startMoney) {
		super(startMoney);
	}

	@Override
	public boolean act(int currentBet) {
		return true;
	}
}