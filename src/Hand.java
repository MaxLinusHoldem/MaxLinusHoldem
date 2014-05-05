import java.util.ArrayList;

/**
 * The class Hand.
 * 
 * @author Linus Wåreus
 * @version 2014.05.04
 */
public class Hand {
	private ArrayList<Card> cards;
	private ArrayList<Card> hand;
	private String result;
	private int points;
	
	Hand(ArrayList<Card> playerCards, ArrayList<Card> board) {
		cards = new ArrayList<Card>();
		hand = new ArrayList<Card>();
		points = 0;
		cards.addAll(playerCards);
		cards.addAll(board);
	}
	
	public String testHand() {
		if (pairs()) {
			return result;
		}
		return "Nothing";
	}
	
	private boolean pairs() {
		if (pair()) {
			ArrayList<Card> pairOne = hand;
			cards.removeAll(pairOne);
			
			if (pair()) {
				hand.addAll(pairOne);
				result = "Two pairs";
				return true;
			}
			hand = pairOne;
			return true;
		}
		return false;
	}
	
	private boolean pair() {
		hand = new ArrayList<Card>();
		for (int i = 0; i < cards.size() - 1; i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				if (cards.get(i).getValue() == cards.get(j).getValue()) {
					result = "Pair";
					hand.add(cards.get(i));
					hand.add(cards.get(j));
					return true;
				}
			}
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
