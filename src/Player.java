import java.util.ArrayList;

/**
 * The abstract class Player. 
 * 
 * @author Linus Wåreus
 * @version 2014.05.05
 */
public abstract class Player {
	protected String name;
	protected int IDNumber;
	protected int cash;
	protected ArrayList<Card> cards;
	
	Player() {
		cards = new ArrayList<Card>();
	}

	public abstract boolean act();
	
	/**
	 * Gets the name of the person.
	 * 
	 * @return name The name of the person.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the ID number of the player.
	 * 
	 * @return IDNumber The ID number of the player.
	 */
	public int getIDNumber() {
		return this.IDNumber;
	}
	
	/**
	 * Returns the players current cash balance.
	 * 
	 * @return cash The players current cash balance.
	 */
	public int getCash() {
		return this.cash;
	}
	
	/**
	 * Adds cash to the players cash balance.
	 * 
	 * @param profit The cash to be added.
	 */
	public void addCash(int profit) {
		this.cash += profit;
	}
	
	/**
	 * Lets the player bet in the game.
	 * 
	 * @param bet The cash to be bet.
	 * @throws IllegalArgumentException If the user tries to bet more money than he or she has. 
	 */
	public void bet(int bet) throws IllegalArgumentException {
		if (bet > this.cash) {
			throw new IllegalArgumentException ("You can't bet more maney than you have.");
		}
		this.cash -= bet;
	}
	
	/**
	 * Returns the player's cards.
	 * 
	 * @return The player's cards.
	 */
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	
	/**
	 * Gives the player a card.
	 */
	public void giveCard(Card card) {
		this.cards.add(card);
	}
	
	/**
	 * Removes all cards from the player.
	 */
	public void removeCards() {
		this.cards = new ArrayList<Card>();
	}
	
}