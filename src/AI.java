/**
 * The class AI.
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public class AI extends Player {
	
	public AI(String name, int ID, GamePanel gamePanel) {
		super(name, ID, gamePanel);
	}

	
	@Override
	public void act(TexasHoldem gui) {
		if (isAllIn) {
			return;
		}
		TexasHoldem.delay(500);
		
		if (this.money < gui.getCurrentRaise()) {
			allIn();
		} else {
			this.call(gui.getCurrentBet());
		}
	}
}