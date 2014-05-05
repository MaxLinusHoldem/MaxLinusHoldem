import java.util.ArrayList;

/**
 * The class Dealer.
 * This class acts as the dealer of the game.
 * 
 * @author Linus Wåreus
 * @version 2014.05.03
 */
public class Dealer {
	private Deck deck;
	private ArrayList<Card> board;
	private ArrayList<Player> persons;
	
	public Dealer(ArrayList<Player> persons) {
		this.deck = new Deck();
		this.board = new ArrayList<Card>();
		this.persons = persons;
		removeCards();
	}
	
	private void removeCards() {
		for (Player p : persons) {
			p.removeCards();
		}
	}
	public void dealCards() {
		for (int i = 0; i < 2; i++) {
			for (Player p : persons) {
				p.giveCard(deck.drawCard());
			}
		}
	}
	
	public void dealTheFlop() {
		deck.drawCard();
		for (int i = 0; i < 3; i++) {
			board.add(deck.drawCard());
		}
	}
	
	public void dealTheTurn() {
		deck.drawCard();
		board.add(deck.drawCard());
	}
	
	public void dealTheRiver() {
		dealTheTurn();
	}
	
	public void selectWinner(ArrayList<Player> activePlayers) {
		for (int i = 0; i < activePlayers.size(); i++) {
			ArrayList<Card> cards = activePlayers.get(i).getCards();
			Hand hand = new Hand(cards, board);
			System.out.println("The player got a " + hand.testHand() + ": " + hand);
		}
		System.out.println();
	}
}