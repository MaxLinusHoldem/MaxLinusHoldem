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
	private ArrayList<Card> allCards; // All open cards plus the players cards.
	private ArrayList<Card> hand; // The players hand.
	private String result; // The type of hand represented by a string.
	
	Evaluate(ArrayList<Card> playerCards, ArrayList<Card> board) {
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
	 * 
	 */
	public int testHand() {
		if (straightFlush(allCards)) {
			return 8;
		}	
		if (fourOfAKind(allCards)) {
			return 7;
		}
		if (fullHouse(allCards)) {
			return 6;
		}
		if (flush(allCards)) {
			return 5;
		}
		if (straight(allCards)) {
			return 4;
		}
		if (threeOfAKind(allCards)) {
			return 3;
		}
		if (twoPairs(allCards)) {
			return 2;
		}
		if (onePair(allCards)) {
			return 1;
		}
		return 0;
	}
	
	private boolean straightFlush(ArrayList<Card> cards) {
		if (flush(cards)) {
			return straight(hand);
		}
		return false;
	}

	private boolean fourOfAKind(ArrayList<Card> cards) {
		for (int i = 0; i < cards.size() - 3; i++) {
			for (int j = i + 1; j < cards.size() - 2; j++) {
				if (cards.get(i).getValue() == cards.get(j).getValue()) {
					for (int k = j + 1; k < cards.size() - 1; k++) {
						if (cards.get(i).getValue() == cards.get(k).getValue()) {
							for (int l = k + 1; k < cards.size(); k++) {
								if (cards.get(i).getValue() == cards.get(l).getValue()) {
									result = "Four of a Kind";
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

	private boolean fullHouse(ArrayList<Card> cards) {
		if (threeOfAKind(cards)) {
			ArrayList<Card> threeOfAKind = hand;
			ArrayList<Card> temp = new ArrayList<Card>(cards);
			temp.removeAll(threeOfAKind);
			
			if (onePair(temp)) {
				hand.addAll(threeOfAKind);
				result = "Full House";
				return true;
			}
		}
		return false;
	}

	private boolean flush(ArrayList<Card> cards) {
		ArrayList<String> suits = new ArrayList<String>();
		suits.add(Card.CLUBS);
		suits.add(Card.DIAMONDS);
		suits.add(Card.HEARTS);
		suits.add(Card.SPADES);
		
		for (int i = 0; i < suits.size(); i++) {
			ArrayList<Card> flush = new ArrayList<Card>();
			for (int j = 0; j < cards.size(); j++) {
				if (cards.get(j).getSuit().equals(suits.get(i))) {
					flush.add(cards.get(j));
				}
			}
			if (flush.size() >= 5) {
				hand = flush;
				result = "Flush";
				return true;
			}
		}
		return false;	
	}

	private boolean straight(ArrayList<Card> cards) {
		ArrayList<Card> temp = new ArrayList<Card>(cards);
		Collections.sort(cards);
		for (int i = 0; i < cards.size() - 1; i++) {
			if (temp.get(i).getValue() + 1 != temp.get(i + 1).getValue()) {
				temp.remove(i);
				i--;
			}
			if (temp.size() < 5) {
				return false;
			}
		}
		
		result = "Straight";
		if (temp.size() == 5) {
			hand = temp;
			return true;
		} else if (temp.size() == 6) {
			temp.remove(0);
			hand = temp;
			return true;
		} else if (temp.size() == 7) {
			temp.remove(0);
			temp.remove(1);
			hand = temp;
			return true;
		}
		return false;
	}

	private boolean threeOfAKind(ArrayList<Card> cards) {
		ArrayList<ArrayList<Card>> threeOfAKind = new ArrayList<ArrayList<Card>>();
		
		for (int i = 0; i < cards.size() - 2; i++) {
			for (int j = i + 1; j < cards.size() - 1; j++) {
				if (cards.get(i).getValue() == cards.get(j).getValue()) {
					for (int k = j + 1; k < cards.size(); k++) {
						if (cards.get(i).getValue() == cards.get(k).getValue()) {
							result = "Three of a Kind";
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

	private boolean twoPairs(ArrayList<Card> cards) {
		if (onePair(cards)) {
			ArrayList<Card> pair = hand;
			ArrayList<Card> temp = new ArrayList<Card>(cards);
			temp.removeAll(pair);
			
			if (onePair(temp)) {
				hand.addAll(pair);
				result = "Two Pair";
				return true;
			}
		}
		return false;
	}
	
	private boolean onePair(ArrayList<Card> cards) {
		ArrayList<ArrayList<Card>> pairs = new ArrayList<ArrayList<Card>>();
		
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
			result = "One Pair";
			return true;
		}
		return false;
	}
	
	public String toString() {
		String temp = "";
		for (int i = 0; i < hand.size(); i++) {
			temp += hand.get(i) + " ";
		}
		return temp;
	}
}
