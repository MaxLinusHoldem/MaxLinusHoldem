import java.util.Random;

import javax.swing.JOptionPane;

/**
 * The class AI.
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public class AI extends Player {
	private Random randomGenerator;
	
	public AI(String name, int ID, GamePanel gamePanel) {
		super(name, ID, gamePanel);
		randomGenerator = new Random();
	}

	
	@Override
	public void act(TexasHoldem gui) {
		if (isAllIn) {
			return;
		}
		TexasHoldem.delay(500);
		
		if (randomGenerator.nextInt(10) < 4) {
			try {
				bet(20, gui.getCurrentRaise());
			} catch (IllegalArgumentException e) {
				try {
					call(gui.getCurrentBet());
				} catch (IllegalArgumentException ex) {
					allIn();
				}
			}
		} else if (randomGenerator.nextInt(10) < 2) {
			fold(gui);
		} else {
			try {
				call(gui.getCurrentBet());
			} catch (IllegalArgumentException e) {
				allIn();
			}
		}
	}
}