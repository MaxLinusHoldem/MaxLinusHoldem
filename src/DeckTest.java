import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.EmptyStackException;

/**
 * The test class DeckTest.
 * This is the test class for the class Deck.
 * 
 * @author Linus Wåreus
 * @version 1.0 (2014.05.16)
 */
public class DeckTest {
	private Deck deck;

	/**
	 * Sets up the test fixture.
	 * Called before every test case method.
	 */
	@Before
	public void setUp() throws Exception {
		deck = new Deck();
	}

	/**
	 * Tears down the test fixture.
	 * Called after every test case method.
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests the drawCard method.
	 */
	@Test
	public void testDrawCard() {
		while (true) {
			try {
				assertNotNull(deck.drawCard());
			} catch (EmptyStackException e) {
				break;
			}
		}
	}
}