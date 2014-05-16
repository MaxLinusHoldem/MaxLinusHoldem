import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The class Evaluate.
 * This class evaluates a players cards to find if he or she
 * has a hand or not.
 * 
 * @author Linus Wåreus
 * @version 1.0 (2014.05.16)
 */
public class Evaluate {
	private List<Card> allCards; // All community cards plus the players cards.
	private List<Card> hand; // The players hand.
	private String result; // The type of hand represented by a string.
	private int[] handIndex;

	// The hands represented by a string.
	public static final String STRAIGHTFLUSH = "Straight Flush";
	public static final String FOUROFAKIND = "Four of a Kind";
	public static final String FULLHOUSE = "Full House";
	public static final String FLUSH = "Flush";
	public static final String STRAIGHT = "Straight";
	public static final String THREEOFAKIND = "Tree of a Kind";
	public static final String TWOPAIRS = "Two Pairs";
	public static final String ONEPAIR = "One Pair";
	public static final String NOTHING = "Kicker";

	/**
	 * Default constructor for the class Evaluate.
	 * 
	 * @param playerCards
	 *            The player's card.
	 * @param board
	 *            The cards from the board.
	 * @throws IllegalArgumentException
	 *             If playerCards is null.
	 */
	Evaluate(List<Card> playerCards, List<Card> board)
			throws IllegalArgumentException {
		if (playerCards == null) {
			throw new IllegalArgumentException(
					"playerCards must contain cards.");
		}
		if (board == null) {
			board = new ArrayList<Card>();
		}
		this.allCards = new ArrayList<Card>();
		this.hand = new ArrayList<Card>();
		this.allCards.addAll(playerCards);
		this.allCards.addAll(board);
		this.handIndex = new int[6];
	}

	/**
	 * Tests the players cards to see if he or she has a hand. Returns the hand
	 * represented by an integer to indicate which hand it is.
	 * 
	 * @return The hand represented by an integer to indicate which hand it is.
	 *         <ul>
	 *         		<li>8 = Straight Flush</li>
	 *         		<li>7 = Four of a Kind</li>
	 *     		    <li>6 = Full House</li>
	 *     		    <li>5 = Flush</li>
	 *         		<li>4 = Straight</li>
	 *         		<li>3 = Three of a Kind</li>
	 *         		<li>2 = Two Pairs</li>
	 *         		<li>1 = One Pair</li>
	 *         		<li>0 = Nothing</li>
	 *         </ul>
	 */
	public int[] testHand() {
		if (straightFlush(allCards)) {
			result = STRAIGHTFLUSH;
			return handIndex;
		}
		if (fourOfAKind(allCards)) {
			result = FOUROFAKIND;
			return handIndex;
		}
		if (fullHouse(allCards)) {
			result = FULLHOUSE;
			return handIndex;
		}
		if (flush(allCards)) {
			result = FLUSH;
			return handIndex;
		}
		if (straight(allCards)) {
			result = STRAIGHT;
			return handIndex;
		}
		if (threeOfAKind(allCards)) {
			result = THREEOFAKIND;
			return handIndex;
		}
		if (twoPairs(allCards)) {
			result = TWOPAIRS;
			return handIndex;
		}
		if (onePair(allCards)) {
			result = ONEPAIR;
			return handIndex;
		}
		nothing(allCards);
		result = NOTHING;
		return handIndex;
	}

	/**
	 * Tests if the cards is a straight flush.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true if the cards was a straight flush, false if the cards
	 *         wasn't.
	 */
	private boolean straightFlush(List<Card> cards) {
		if (flush(cards)) {
			if (straight(hand)) {
				handIndex = new int[2];
				handIndex[0] = 8;
				handIndex[1] = hand.get(4).getValue();
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if the cards is a four of a kind.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true if the cards was a four of a kind, false if the cards
	 *         wasn't.
	 */
	private boolean fourOfAKind(List<Card> cards) {
		for (int i = 0; i < cards.size() - 3; i++) {
			for (int j = i + 1; j < cards.size() - 2; j++) {
				if (cards.get(i).getValue() == cards.get(j).getValue()) {
					for (int k = j + 1; k < cards.size() - 1; k++) {
						if (cards.get(i).getValue() == cards.get(k).getValue()) {
							for (int l = k + 1; k < cards.size(); k++) {
								if (cards.get(i).getValue() == cards.get(l)
										.getValue()) {
									hand.add(cards.get(i));
									hand.add(cards.get(j));
									hand.add(cards.get(k));
									hand.add(cards.get(l));
									handIndex = new int[3];
									handIndex[0] = 7;
									handIndex[1] = hand.get(0).getValue();
									int[] tempHandIndex = kicker(cards);
									handIndex[2] = tempHandIndex[0];
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
	 * Tests if the cards is a full house.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true if the cards was a full house, false if the cards wasn't.
	 */
	private boolean fullHouse(List<Card> cards) {
		if (threeOfAKind(cards)) {
			List<Card> threeOfAKind = hand;
			List<Card> temp = new ArrayList<Card>(cards);
			temp.removeAll(threeOfAKind);

			if (onePair(temp)) {
				hand.addAll(threeOfAKind);
				handIndex = new int[3];
				handIndex[0] = 6;
				handIndex[1] = hand.get(2).getValue();
				handIndex[2] = hand.get(0).getValue();
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if the cards is a flush.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true if the cards was a flush, false if the cards wasn't.
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
				handIndex = new int[6];
				handIndex[0] = 5;
				Collections.sort(hand);
				Collections.reverse(hand);
				for (int j = 0; j < handIndex.length - 1; j++) {
					handIndex[j + 1] = hand.get(j).getValue();
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if the cards is a straight.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true if the cards was a straight, false if the cards wasn't.
	 */
	private boolean straight(List<Card> cards) {
		List<Card> temp = new ArrayList<Card>(cards);
		Collections.sort(temp);
		List<Card> straight = new ArrayList<Card>();
		for (int i = 0; i < temp.size() - 4; i++) {
			if (temp.get(i).getValue() + 1 == temp.get(i + 1).getValue()
					&& temp.get(i).getValue() + 2 == temp.get(i + 2).getValue()
					&& temp.get(i).getValue() + 3 == temp.get(i + 3).getValue()
					&& temp.get(i).getValue() + 4 == temp.get(i + 4).getValue()) {

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
			handIndex = new int[2];
			handIndex[0] = 4;
			handIndex[1] = hand.get(4).getValue();
			return true;
		}
		return false;
	}

	/**
	 * Tests if the cards is a three of a kind.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true if the cards was a three of a kind, false if the cards
	 *         wasn't.
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
		if (threeOfAKind.size() != 0) {
			if (threeOfAKind.size() == 1) {
				hand = new ArrayList<Card>(threeOfAKind.get(0));
			} else if (threeOfAKind.size() == 2) {
				if (threeOfAKind.get(0).get(0).getValue() > threeOfAKind.get(1)
						.get(0).getValue()) {
					hand = new ArrayList<Card>(threeOfAKind.get(0));
				} else {
					hand = new ArrayList<Card>(threeOfAKind.get(1));
				}
			}
			handIndex = new int[4];
			handIndex[0] = 3;
			handIndex[1] = hand.get(0).getValue();
			int[] tempHandIndex = kicker(cards);
			handIndex[2] = tempHandIndex[0];
			handIndex[3] = tempHandIndex[1];
			return true;
		}
		return false;
	}

	/**
	 * Tests if the cards is a two pair.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true if the cards was a two pair, false if the cards wasn't.
	 */
	private boolean twoPairs(List<Card> cards) {
		if (onePair(cards)) {
			List<Card> pair = hand;
			List<Card> temp = new ArrayList<Card>(cards);
			temp.removeAll(pair);

			if (onePair(temp)) {
				hand.addAll(pair);
				handIndex = new int[4];
				handIndex[0] = 2;
				handIndex[1] = hand.get(2).getValue();
				handIndex[2] = hand.get(0).getValue();
				int[] tempHandIndex = kicker(cards);
				handIndex[3] = tempHandIndex[0];
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if the cards is a one pair.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true if the cards was a one pair, false if the cards wasn't.
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
				if (pairs.get(i).get(0).getValue() > pairs.get(max).get(0)
						.getValue()) {
					max = i;
				}
			}
			hand = new ArrayList<Card>();
			hand.addAll(pairs.get(max));

			handIndex = new int[5];
			handIndex[0] = 1;
			handIndex[1] = hand.get(0).getValue();
			int[] tempHandIndex = kicker(cards);

			if (tempHandIndex.length >= 3) {
				handIndex[2] = tempHandIndex[0];
				handIndex[3] = tempHandIndex[1];
				handIndex[4] = tempHandIndex[2];
			}
			return true;
		}
		return false;
	}

	/**
	 * Takes out the five highest cards if the player has got nothing.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return true Indicates that the cards was nothing.
	 */
	private boolean nothing(List<Card> cards) {
		ArrayList<Card> temp = new ArrayList<Card>(cards);
		handIndex = new int[6];
		handIndex[0] = 0;
		int[] tempHandIndex = kicker(temp);
		for (int i = 0; i < tempHandIndex.length; i++) {
			handIndex[i + 1] = tempHandIndex[i];
			if (i == 4) {
				break;
			}
		}
		return true;
	}

	/**
	 * Takes out all the kickers from the cards.
	 * 
	 * @param cards
	 *            The cards to be tested.
	 * @return kickers An array with all the kickers.
	 */
	private int[] kicker(List<Card> cards) {
		ArrayList<Card> temp = new ArrayList<Card>(cards);
		temp.removeAll(hand);
		Collections.sort(temp);
		Collections.reverse(temp);
		int[] kickers = new int[temp.size()];
		for (int i = 0; i < kickers.length; i++) {
			kickers[i] = temp.get(i).getValue();
		}
		return kickers;
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
