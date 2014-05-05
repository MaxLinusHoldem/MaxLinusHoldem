import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * The class Card.
 * This class creates a playing card.
 * 
 * @author Linus Wåreus
 * @version 2014.05.05
 */
public class Card {
	private int value; // The cards value.
	private String suit; // The cards suit.
	private String rank; // The cards rank.
	private BufferedImage image; // The card's image.
	
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
		if (value < 2 || value > 14) {
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
		try {
		    image = ImageIO.read(new File("../res/cards/" + toString() + ".png"));
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
	}
	
	/**
	 * Returns the cards value.
	 * 
	 * @return value The cards value.
	 */
	public int getValue() {
		return this.value;
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
	 * Returns a string of the card.
	 * 
	 * @return A string of the card.
	 */
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
}