import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;
import java.util.EmptyStackException;

/**
 * The class Deck.
 * This class creates and shuffles a deck with cards.
 * 
 * @author Linus Wåreus
 * @version 2014.05.05
 */
public class Deck {
	private Stack<Card> deck; // The shuffled deck.
	private ArrayList<Card> sortedDeck; // The sorted deck.
	private Random randomGenerator;
	
	/**
	 * Default constructor of the class Deck.
	 */
	public Deck() {
		deck = new Stack<Card>();
		sortedDeck = new ArrayList<Card>();
		randomGenerator = new Random();
		createDeck();
		shuffleDeck();
	}
	
	/**
	 * Creates new a sorted deck.
	 */
	private void createDeck() {
		ArrayList<String> suits = new ArrayList<String>();
		suits.add(Card.CLUBS);
		suits.add(Card.DIAMONDS);
		suits.add(Card.HEARTS);
		suits.add(Card.SPADES);
		for (int i = 0; i < suits.size(); i++) {
			for (int j = 2; j <= 14; j++) {
				sortedDeck.add(new Card(j, suits.get(i)));
			}
		}
	}
	
	/**
	 * Shuffles the deck.
	 */
	public void shuffleDeck() {
		while (!sortedDeck.isEmpty()) {
			deck.push(sortedDeck.remove(randomGenerator.nextInt(sortedDeck.size())));
		}
	}
	
	/**
	 * Draws a card from the the top of the deck.
	 * 
	 * @return A card from the top of the deck.
	 * @throws EmptyStackException If the deck is empty.
	 */
	public Card drawCard() throws EmptyStackException {
		return deck.pop();
	}
	
	/**
	 * Returns true if the deck is empty, false otherwise.
	 * 
	 * @param true if the deck is empty, false otherwise.
	 */
	public boolean empty() {
		return deck.empty();
	}
	
	/**
	 * Produces a string with all the cards in the deck.
	 * 
	 * @return deckString A string with all the cards in the deck.
	 */
	@Override
	public String toString() {
		String deckString = "";
		while (!deck.empty()) {
			deckString += deck.pop() + "\n";
;		}
		return deckString;
	}
}
