import java.util.ArrayList;

/**
 * The abstract class Player. 
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public abstract class Player {
	protected String name; // The player's name.
	protected int IDNumber; // The player's ID Number.
	protected int money; // The player's money.
	protected ArrayList<Card> cards; // The player's cards.
	protected int myBet; // The player's current bet.
	
	Player(int startMoney) {
		cards = new ArrayList<Card>();
		this.money = startMoney;
		this.myBet = 0;
	}

	public abstract boolean act(int currentBet);
	
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
	 * Returns the players current money balance.
	 * 
	 * @return cash The players current money balance.
	 */
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Adds cash to the players money balance.
	 * 
	 * @param profit The money to be added.
	 */
	public void addMoney(int profit) {
		this.money += profit;
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
	
	/**
	 * Returns the varible myBet.
	 * 
	 * @return myBet The varible myBet.
	 */
	public int getBet() {
		return this.myBet;
	}
	/**
	 * Resets the variable myBet to zero.
	 */
	public void resetBet() {
		this.myBet = 0;
	}
	
	/**
	 * Lets the player cecked.
	 * 
	 * @return true Indicates that the play made no bet.
	 */
	public boolean ceck() {
		return true;
	}
	
	/**
	 * Lets the player call the current bet in the game.
	 * 
	 * @param currentBet The current bet in the game.
	 * @return true Indicates that the player is still in the game.
	 * @throws IllegalArgumentException If the user tries to bet more money than he or she has. 
	 */
	public boolean call(int currentBet) throws IllegalArgumentException {
		if (currentBet - myBet > this.money) {
			throw new IllegalArgumentException ("You can't bet more maney than you have.");
		}
		this.money -= currentBet + myBet;
		this.myBet = currentBet;
		return true;
	}
	
	/**
	 * Lets the player bet in the game.
	 * 
	 * @param bet The cash to be bet.
	 * @return true Indicates that the player is still in the game.
	 * @throws IllegalArgumentException If the user tries to bet more money than he or she has. 
	 */
	public boolean bet(int bet) throws IllegalArgumentException {
		if (bet > this.money) {
			throw new IllegalArgumentException ("You can't bet more maney than you have.");
		}
		this.money -= bet;
		this.myBet += bet;
		return true;
	}
	
	/**
	 * Lets the player fold the game.
	 * 
	 * @return false Indicates the the player wants to fold.
	 */
	public boolean fold() {
		return false;
	}
	
}