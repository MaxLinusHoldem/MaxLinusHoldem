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
		TexasHoldem.delay(500);
		this.call(gui.getCurrentBet());
	}
}