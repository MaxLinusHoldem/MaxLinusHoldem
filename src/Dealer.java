import java.util.ArrayList;

/**
 * The class Dealer.
 * This class acts as the dealer of the game.
 * 
 * @author Linus Wåreus
 * @version 2014.05.05
 */
public class Dealer {
	private Deck deck;
	private ArrayList<Card> board;
	private ArrayList<Player> players;
	private int pot;
	
	public Dealer(ArrayList<Player> players) {
		this.deck = new Deck();
		this.board = new ArrayList<Card>();
		this.players = players;
		removeCards();
	}
	
	/**
	 * Removes all the cards from the players.
	 */
	private void removeCards() {
		for (Player p : players) {
			p.removeCards();
		}
	}
	
	/**
	 * Deals two cards to every player in the game.
	 */
	public void dealCards() {
		for (int i = 0; i < 2; i++) {
			for (Player p : players) {
				p.giveCard(deck.drawCard());
			}
		}
	}
	
	/**
	 * Deals the flop.
	 */
	public void dealTheFlop() {
		deck.drawCard();
		for (int i = 0; i < 3; i++) {
			board.add(deck.drawCard());
		}
	}
	
	/**
	 * Deals the turn.
	 */
	public void dealTheTurn() {
		deck.drawCard();
		board.add(deck.drawCard());
	}
	
	/**
	 * Deals the river.
	 */
	public void dealTheRiver() {
		dealTheTurn();
	}
	
	/**
	 * Places a bet in the pot.
	 * 
	 * @param bet The bet to be placed in the pot.
	 */
	public void bet(int bet) {
		this.pot += bet;
	}
	
	/**
	 * Returns the pot's current balance.
	 * 
	 * @return pot The pot's current balance.
	 */
	public int getPot() {
		return this.pot;
	}
	
	/**
	 * Removes and returns the pot's current balance.
	 * 
	 * @return pot The pot's current balance.
	 */
	public int removePot() {
		int pot = this.pot;
		this.pot = 0;
		return pot;
	}
	
	/**
	 * Selects a winner in the game.
	 * 
	 * @param activePlayers All active players in the game.
	 */
	public void selectWinner(ArrayList<Player> activePlayers) {
		for (int i = 0; i < activePlayers.size(); i++) {
			ArrayList<Card> cards = activePlayers.get(i).getCards();
			Hand hand = new Hand(cards, board);
			System.out.println("The player got a " + hand.testHand() + ": " + hand);
		}
		System.out.println();
	}
}