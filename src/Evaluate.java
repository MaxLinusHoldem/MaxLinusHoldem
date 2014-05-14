import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The class Evaluate.
 * This class evaluates a players cards to find
 * if he or she has a hand or not.
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public class Evaluate {
	private List<Card> allCards; // All open cards plus the players cards.
	private List<Card> hand; // The players hand.
	private String result; // The type of hand represented by a string.
	
	// The hands represented by a string.
	public static final String STRAIGHTFLUSH = "Straight Flush";
	public static final String FOUROFAKIND = "Four of a Kind";
	public static final String FULLHOUSE = "Full House";
	public static final String FLUSH = "Flush";
	public static final String STRAIGHT = "Straight";
	public static final String THREEOFAKIND = "Tree of a Kind";
	public static final String TWOPAIRS = "Two Pairs";
	public static final String ONEPAIR = "One Pair";
	public static final String NOTHING = "Nothing";
	
	/**
	 * Default constructor for the class Evaluate.
	 * 
	 * @param playerCards The player's card.
	 * @param board The cards from the board.
	 * @throws IllegalArgumentException If playerCards is null.
	 */
	Evaluate(List<Card> playerCards, List<Card> board) throws IllegalArgumentException {
		if (playerCards == null) {
			throw new IllegalArgumentException ("playerCards must contain cards.");
		}
		if (board == null) {
			board = new ArrayList<Card>();
		}
		this.allCards = new ArrayList<Card>();
		this.hand = new ArrayList<Card>();
		this.allCards.addAll(playerCards);
		this.allCards.addAll(board);
	}
	
	/**
	 * Tests the players cards to see if he or she has a hand.
	 * Returns the hand represented by an integer to indicate
	 * which hand it is.
	 * 
	 * @return The hand represented by an integer to indicate
	 * 		   which hand it is.
	 * 			<ul>
	 * 				<li>8 = Straight Flush</li>
	 * 				<li>7 = Four of a Kind</li>
	 * 				<li>6 = Full House</li>
	 *				<li>5 = Flush</li>
	 * 		   		<li>4 = Straight</li>
	 * 		   		<li>3 = Three of a Kind</li>
	 * 		   		<li>2 = Two Pairs</li>
	 * 		   		<li>1 = One Pair</li>
	 * 		   		<li>0 = Nothing</li>
	 * 			</ul>
	 */
	public int testHand() {
		if (straightFlush(allCards)) {
			result = STRAIGHTFLUSH;
			return 8;
		}	
		if (fourOfAKind(allCards)) {
			result = FOUROFAKIND;
			return 7;
		}
		if (fullHouse(allCards)) {
			result = FULLHOUSE;
			return 6;
		}
		if (flush(allCards)) {
			result = FLUSH;
			return 5;
		}
		if (straight(allCards)) {
			result = STRAIGHT;
			return 4;
		}
		if (threeOfAKind(allCards)) {
			result = THREEOFAKIND;
			return 3;
		}
		if (twoPairs(allCards)) {
			result = TWOPAIRS;
			return 2;
		}
		if (onePair(allCards)) {
			result = ONEPAIR;
			return 1;
		}
		result = NOTHING;
		return 0;
	}

	/**
	 * 
	 * @param cards
	 * @return
	 */
	private boolean straightFlush(List<Card> cards) {
		if (flush(cards)) {
			return straight(hand);
		}
		return false;
	}

	/**
	 * 
	 * @param cards
	 * @return
	 */
	private boolean fourOfAKind(List<Card> cards) {
		for (int i = 0; i < cards.size() - 3; i++) {
			for (int j = i + 1; j < cards.size() - 2; j++) {
				if (cards.get(i).getValue() == cards.get(j).getValue()) {
					for (int k = j + 1; k < cards.size() - 1; k++) {
						if (cards.get(i).getValue() == cards.get(k).getValue()) {
							for (int l = k + 1; k < cards.size(); k++) {
								if (cards.get(i).getValue() == cards.get(l).getValue()) {
									hand.add(cards.get(i));
									hand.add(cards.get(j));
									hand.add(cards.get(k));
									hand.add(cards.get(l));
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param cards
	 * @return
	 */
	private boolean fullHouse(List<Card> cards) {
		if (threeOfAKind(cards)) {
			List<Card> threeOfAKind = hand;
			List<Card> temp = new ArrayList<Card>(cards);
			temp.removeAll(threeOfAKind);
			
			if (onePair(temp)) {
				hand.addAll(threeOfAKind);
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param cards
	 * @return
	 */
	private boolean flush(List<Card> cards) {
		List<String> suits = new ArrayList<String>();
		suits.add(Card.CLUBS);
		suits.add(Card.DIAMONDS);
		suits.add(Card.HEARTS);
		suits.add(Card.SPADES);
		
		for (int i = 0; i < suits.size(); i++) {
			List<Card> flush = new ArrayList<Card>();
			for (int j = 0; j < cards.size(); j++) {
				if (cards.get(j).getSuit().equals(suits.get(i))) {
					flush.add(cards.get(j));
				}
			}
			if (flush.size() >= 5) {
				hand = flush;
				return true;
			}
		}
		return false;	
	}

	/**
	 * 
	 * @param cards
	 * @return
	 */
	private boolean straight(List<Card> cards) {
		List<Card> temp = new ArrayList<Card>(cards);
		Collections.sort(temp);
		List<Card>  straight  = new ArrayList<Card>();
		for (int i = 0; i < temp.size() - 4; i++) {
			if (temp.get(i).getValue() + 1 == temp.get(i + 1).getValue() &&
				temp.get(i).getValue() + 2 == temp.get(i + 2).getValue() &&
				temp.get(i).getValue() + 3 == temp.get(i + 3).getValue() &&
				temp.get(i).getValue() + 4 == temp.get(i + 4).getValue()) {
				
				straight = new ArrayList<Card>();
				straight.add(temp.get(i));
				straight.add(temp.get(i + 1));
				straight.add(temp.get(i + 2));
				straight.add(temp.get(i + 3));
				straight.add(temp.get(i + 4));
			}
		}
		
		if (straight.size() == 5) {
			hand = straight;
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param cards
	 * @return
	 */
	private boolean threeOfAKind(List<Card> cards) {
		List<ArrayList<Card>> threeOfAKind = new ArrayList<ArrayList<Card>>();
		
		for (int i = 0; i < cards.size() - 2; i++) {
			for (int j = i + 1; j < cards.size() - 1; j++) {
				if (cards.get(i).getValue() == cards.get(j).getValue()) {
					for (int k = j + 1; k < cards.size(); k++) {
						if (cards.get(i).getValue() == cards.get(k).getValue()) {
							ArrayList<Card> temp = new ArrayList<Card>();
							temp.add(cards.get(i));
							temp.add(cards.get(j));
							temp.add(cards.get(k));
							threeOfAKind.add(temp);
							i++;
						}
					}
				}
			}
		}
		if (threeOfAKind.size() == 1) {
			hand = new ArrayList<Card>(threeOfAKind.get(0));
			return true;
		} else if (threeOfAKind.size() == 2) {
			if (threeOfAKind.get(0).get(0).getValue() > threeOfAKind.get(1).get(0).getValue()) {
				hand = new ArrayList<Card>(threeOfAKind.get(0));
			} else {
				hand = new ArrayList<Card>(threeOfAKind.get(1));
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param cards
	 * @return
	 */
	private boolean twoPairs(List<Card> cards) {
		if (onePair(cards)) {
			List<Card> pair = hand;
			List<Card> temp = new ArrayList<Card>(cards);
			temp.removeAll(pair);
			
			if (onePair(temp)) {
				hand.addAll(pair);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param cards
	 * @return
	 */
	private boolean onePair(List<Card> cards) {
		List<ArrayList<Card>> pairs = new ArrayList<ArrayList<Card>>();
		
		for (int i = 0; i < cards.size() - 1; i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				if (cards.get(i).getValue() == cards.get(j).getValue()) {
					ArrayList<Card> temp = new ArrayList<Card>();
					temp.add(cards.get(i));
					temp.add(cards.get(j));
					pairs.add(temp);
					i++;
				}
			}
		}
		
		if (pairs.size() != 0) {
			int max = 0;
			for (int i = 1; i < pairs.size(); i++) {
				if (pairs.get(i).get(0).getValue() > pairs.get(max).get(0).getValue()) {
					max = i;
				}
			}
			hand = new ArrayList<Card>();
			hand.addAll(pairs.get(max));
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the player's hand.
	 * 
	 * @return hand The player's hand.
	 */
	public List<Card> getHand() {
		return this.hand;
	}
	
	/**
	 * Returns the hand represented by a string.
	 * 
	 * @return The hand represented by a string.
	 */
	public String toString() {
		return this.result;
	}
}
