import java.util.ArrayList;

/**
 * The abstract class Player. 
 * 
 * @author Linus Wåreus
 * @version 2014.05.02
 */
public abstract class Player {
	protected String name;
	protected int IDNumber;
	protected int cash;
	protected ArrayList<Card> cards;
	
	Player() {
		cards = new ArrayList<Card>();
	}

	public abstract boolean act(GUI graficUserInterface);
	
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
	 * 
	 * @return
	 */
	public int getCash() {
		return this.cash;
	}
	
	/**
	 * 
	 * @param profit
	 */
	public void addCash(int profit) {
		this.cash += profit;
	}
	
	/**
	 * 
	 * @param loss
	 * @return
	 */
	public int removeCash(int loss) {
		this.cash -= loss;
		return loss;
	}
	
	/**
	 * Returns the person's cards.
	 * 
	 * @return The person's cards.
	 */
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	/**
	 * Gives the person a card.
	 */
	public void giveCard(Card card) {
		this.cards.add(card);
	}
	
	/**
	 * Removes all cards from the person.
	 */
	public void removeCards() {
		this.cards = new ArrayList<Card>();
	}
	
}