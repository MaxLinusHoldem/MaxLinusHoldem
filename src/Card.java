import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * The class Card.
 * This class creates a playing card.
 * 
 * @author Linus Wåreus & Max Wällstedt
 * @version 2014.05.15
 */
public class Card implements Comparable<Card> {
	private int value; // The cards value.
	private String suit; // The cards suit.
	private String rank; // The cards rank.
	private BufferedImage image; // The card's image.
	private BufferedImage backImage; // The card's back image.
	
	// The approved suits for a card.
	public static final String CLUBS = "Clubs";
	public static final String DIAMONDS = "Diamonds";
	public static final String HEARTS = "Hearts";
	public static final String SPADES = "Spades";
	
	/**
	 * Default constructor of the class Card.
	 * 
	 * @param value The cards value.
	 * @param suit The cards suit.
	 * @throws IllegalArgumentException If  value < 2 or value > 14 or
	 * 									suit isn't CLUBS, DIAMONDS, HEARTS or SPADES.
	 */
	public Card(int value, String suit) throws IllegalArgumentException {
		if(value < 2 || value > 14) {
			throw new IllegalArgumentException ("Value must be between 2 and 14.");
		}
		if (suit != CLUBS && suit != DIAMONDS && suit != HEARTS && suit != SPADES) {
			throw new IllegalArgumentException ("Invalid value for suit.");
		}
		this.value = value;
		this.suit = suit;
		this.rank = calculateRank(value);
		loadImage();
	}

	/**
	 * This method calculates the cards rank by evaluating
	 * the cards value.
	 * 
	 * @param value The cards value.
	 * @return The cards rank.
	 */
	private String calculateRank(int value) {
		if (value == 11) {
			return "Jack";
		} else if (value == 12) {
			return "Queen";
		} else if (value == 13) {
			return "King";
		} else if (value == 14) {
			return "Ace";
		}
		return "" + value;
	}
	
	/**
	 * Tries to load the card's image from the file system.
	 */
	private void loadImage() {
		String frontPath = "../res/cards/" + toString() + ".png";
		String backPath = "../res/cards/Red back standing.png";

		try {
			image = ImageIO.read(new File(frontPath));
		} catch (IOException e) {
			System.err.println("error: File \"" + frontPath + "\" not found.");
			System.exit(1);
		}

		try {
			backImage = ImageIO.read(new File(backPath));
		} catch (IOException e) {
			System.err.println("error: File \"" + backPath + "\" not found.");
			System.exit(1);
		}
	}
	
	/**
	 * Returns the card's value.
	 * 
	 * @return value The cards value.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Returns the card's suit
	 * 
	 * @return suit The card's suit.
	 */
	public String getSuit() {
		return this.suit;
	}
	
	/**
	 * Returns the card's image.
	 * 
	 * @return image The card's image.
	 */
	public BufferedImage getImage() {
		return this.image;
	}

	/**
	 * Returns the back image of the card.
	 * 
	 * @return backImage The back image of the card.
	 */
	public BufferedImage getBackImage() {
		return this.backImage;
	}
	
	/**
	 * Returns a string of the card.
	 * 
	 * @return A string of the card.
	 */
	@Override
	public String toString() {
		return this.rank + " of " + this.suit;
	}

	/**
	 * Compares two cards values to each other.
	 * 
	 * @param card The card to compare this card to.
	 * @return A negative integer, zero, or a positive integer as this cards
	 *         value is less than, equal to, or greater than the specified cards
	 *         value.
	 */
	@Override
	public int compareTo(Card card) {
		return this.value - card.getValue();
	}
}