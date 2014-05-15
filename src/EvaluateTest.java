import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public class EvaluateTest {
	private Evaluate evaluater1;
	private Evaluate evaluater2;
	private Evaluate evaluater3;
	private Evaluate evaluater4;
	private Evaluate evaluater5;
	private Evaluate evaluater6;
	private Evaluate evaluater7;
	private Evaluate evaluater8;
	private Evaluate evaluater9;
	private Evaluate evaluater10;
	private Evaluate evaluater11;
	private Evaluate evaluater12;
	private Evaluate evaluater13;
	private ArrayList<Card> playerCards1;
	private ArrayList<Card> playerCards2;
	private ArrayList<Card> playerCards3;
	private ArrayList<Card> playerCards4;
	private ArrayList<Card> playerCards5;
	private ArrayList<Card> playerCards6;
	private ArrayList<Card> playerCards7;
	private ArrayList<Card> playerCards8;
	private ArrayList<Card> playerCards9;
	private ArrayList<Card> playerCards10;
	private ArrayList<Card> playerCards11;
	private ArrayList<Card> playerCards12;
	private ArrayList<Card> playerCards13;
	private ArrayList<Card> board1;
	private ArrayList<Card> board2;
	private ArrayList<Card> board3;
	private ArrayList<Card> board4;
	private ArrayList<Card> board5;
	private ArrayList<Card> board6;
	private ArrayList<Card> board8;
	private ArrayList<Card> board9;
	private ArrayList<Card> board10;
	private ArrayList<Card> board11;
	private ArrayList<Card> board12;
	private ArrayList<Card> board13;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		playerCards1 = new ArrayList<Card>();
		board1 = new ArrayList<Card>();
		playerCards1.add(new Card(2, Card.HEARTS));
		playerCards1.add(new Card(2, Card.DIAMONDS));
		board1.add(new Card(3, Card.SPADES));
		board1.add(new Card(7, Card.DIAMONDS));
		board1.add(new Card(9, Card.DIAMONDS));
		board1.add(new Card(11, Card.HEARTS));
		board1.add(new Card(14, Card.CLUBS));
		evaluater1 = new Evaluate(playerCards1, board1);
		
		playerCards2 = new ArrayList<Card>();
		board2 = new ArrayList<Card>();
		playerCards2.add(new Card(2, Card.HEARTS));
		playerCards2.add(new Card(2, Card.DIAMONDS));
		board2.add(new Card(3, Card.SPADES));
		board2.add(new Card(3, Card.DIAMONDS));
		board2.add(new Card(9, Card.DIAMONDS));
		board2.add(new Card(11, Card.HEARTS));
		board2.add(new Card(14, Card.CLUBS));
		evaluater2 = new Evaluate(playerCards2, board2);
		
		playerCards3 = new ArrayList<Card>();
		board3 = new ArrayList<Card>();
		playerCards3.add(new Card(2, Card.HEARTS));
		playerCards3.add(new Card(2, Card.DIAMONDS));
		board3.add(new Card(3, Card.SPADES));
		board3.add(new Card(3, Card.DIAMONDS));
		board3.add(new Card(9, Card.DIAMONDS));
		board3.add(new Card(9, Card.HEARTS));
		board3.add(new Card(14, Card.CLUBS));
		evaluater3 = new Evaluate(playerCards3, board3);
		
		playerCards4 = new ArrayList<Card>();
		board4 = new ArrayList<Card>();
		playerCards4.add(new Card(2, Card.HEARTS));
		playerCards4.add(new Card(2, Card.DIAMONDS));
		board4.add(new Card(2, Card.SPADES));
		board4.add(new Card(3, Card.DIAMONDS));
		board4.add(new Card(9, Card.DIAMONDS));
		board4.add(new Card(11, Card.HEARTS));
		board4.add(new Card(14, Card.CLUBS));
		evaluater4 = new Evaluate(playerCards4, board4);
		
		playerCards5 = new ArrayList<Card>();
		board5 = new ArrayList<Card>();
		playerCards5.add(new Card(2, Card.HEARTS));
		playerCards5.add(new Card(5, Card.DIAMONDS));
		board5.add(new Card(7, Card.SPADES));
		board5.add(new Card(6, Card.DIAMONDS));
		board5.add(new Card(4, Card.DIAMONDS));
		board5.add(new Card(8, Card.HEARTS));
		board5.add(new Card(14, Card.CLUBS));
		evaluater5 = new Evaluate(playerCards5, board5);
		
		playerCards6 = new ArrayList<Card>();
		board6 = new ArrayList<Card>();
		playerCards6.add(new Card(2, Card.HEARTS));
		playerCards6.add(new Card(3, Card.DIAMONDS));
		board6.add(new Card(4, Card.SPADES));
		board6.add(new Card(5, Card.DIAMONDS));
		board6.add(new Card(6, Card.DIAMONDS));
		board6.add(new Card(7, Card.HEARTS));
		board6.add(new Card(8, Card.CLUBS));
		evaluater6 = new Evaluate(playerCards6, board6);
		
		playerCards7 = new ArrayList<Card>();
		playerCards7.add(new Card(2, Card.HEARTS));
		playerCards7.add(new Card(3, Card.DIAMONDS));
		evaluater7 = new Evaluate(playerCards6, null);
		
		playerCards8 = new ArrayList<Card>();
		board8 = new ArrayList<Card>();
		playerCards8.add(new Card(2, Card.HEARTS));
		playerCards8.add(new Card(11, Card.HEARTS));
		board8.add(new Card(4, Card.HEARTS));
		board8.add(new Card(5, Card.DIAMONDS));
		board8.add(new Card(14, Card.DIAMONDS));
		board8.add(new Card(7, Card.HEARTS));
		board8.add(new Card(12, Card.HEARTS));
		evaluater8 = new Evaluate(playerCards8, board8);
		
		playerCards9 = new ArrayList<Card>();
		board9 = new ArrayList<Card>();
		playerCards9.add(new Card(2, Card.HEARTS));
		playerCards9.add(new Card(2, Card.DIAMONDS));
		board9.add(new Card(11, Card.HEARTS));
		board9.add(new Card(11, Card.DIAMONDS));
		board9.add(new Card(11, Card.SPADES));
		board9.add(new Card(7, Card.HEARTS));
		board9.add(new Card(7, Card.CLUBS));
		evaluater9 = new Evaluate(playerCards9, board9);

		playerCards10 = new ArrayList<Card>();
		board10 = new ArrayList<Card>();
		playerCards10.add(new Card(4, Card.HEARTS));
		playerCards10.add(new Card(4, Card.DIAMONDS));
		board10.add(new Card(4, Card.CLUBS));
		board10.add(new Card(4, Card.SPADES));
		board10.add(new Card(7, Card.SPADES));
		board10.add(new Card(7, Card.HEARTS));
		board10.add(new Card(7, Card.CLUBS));
		evaluater10 = new Evaluate(playerCards10, board10);
		
		playerCards11 = new ArrayList<Card>();
		board11 = new ArrayList<Card>();
		playerCards11.add(new Card(11, Card.HEARTS));
		playerCards11.add(new Card(10, Card.HEARTS));
		board11.add(new Card(14, Card.HEARTS));
		board11.add(new Card(12, Card.HEARTS));
		board11.add(new Card(13, Card.HEARTS));
		board11.add(new Card(7, Card.HEARTS));
		board11.add(new Card(7, Card.CLUBS));
		evaluater11 = new Evaluate(playerCards11, board11);
		
		playerCards12 = new ArrayList<Card>();
		board12 = new ArrayList<Card>();
		playerCards12.add(new Card(10, Card.HEARTS));
		playerCards12.add(new Card(11, Card.HEARTS));
		board12.add(new Card(12, Card.HEARTS));
		board12.add(new Card(13, Card.HEARTS));
		board12.add(new Card(14, Card.HEARTS));
		board12.add(new Card(9, Card.HEARTS));
		board12.add(new Card(8, Card.HEARTS));
		evaluater12 = new Evaluate(playerCards12, board12);
		
		playerCards13 = new ArrayList<Card>();
		board13 = new ArrayList<Card>();
		playerCards13.add(new Card(2, Card.HEARTS));
		playerCards13.add(new Card(3, Card.SPADES));
		board13.add(new Card(5, Card.SPADES));
		board13.add(new Card(7, Card.DIAMONDS));
		board13.add(new Card(10, Card.DIAMONDS));
		board13.add(new Card(11, Card.HEARTS));
		board13.add(new Card(12, Card.HEARTS));
		evaluater13 = new Evaluate(playerCards13, board13);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTestHand() {
		assertArrayEquals(new int[] {1, 2, 14, 11, 9}, evaluater1.testHand());
		List<Card> hand = evaluater1.getHand();
		assertEquals(2, hand.get(0).getValue());
		assertEquals(2, hand.size());
		
		assertArrayEquals(new int[] {2, 3, 2, 14}, evaluater2.testHand());
		hand = evaluater2.getHand();
		assertEquals(4, hand.size());
		assertEquals(2, hand.get(0).getValue());
		assertEquals(3, hand.get(2).getValue());
		
		assertArrayEquals(new int[] {2, 9, 3, 14}, evaluater3.testHand());
		hand = evaluater3.getHand();
		assertEquals(4, hand.size());
		assertEquals(3, hand.get(0).getValue());
		assertEquals(9, hand.get(2).getValue());
		
		assertArrayEquals(new int[] {3, 2, 14, 11}, evaluater4.testHand());
		hand = evaluater4.getHand();
		assertEquals(3, hand.size());
		assertEquals(2, hand.get(0).getValue());
		
		assertArrayEquals(new int[] {4, 8}, evaluater5.testHand());
		hand = evaluater5.getHand();
		assertEquals(5, hand.size());
		assertEquals(4, hand.get(0).getValue());
		assertEquals(5, hand.get(1).getValue());
		assertEquals(6, hand.get(2).getValue());
		assertEquals(7, hand.get(3).getValue());
		assertEquals(8, hand.get(4).getValue());
		
		assertArrayEquals(new int[] {4, 8}, evaluater6.testHand());
		hand = evaluater6.getHand();
		assertEquals(5, hand.size());
		assertEquals(4, hand.get(0).getValue());
		assertEquals(5, hand.get(1).getValue());
		assertEquals(6, hand.get(2).getValue());
		assertEquals(7, hand.get(3).getValue());
		assertEquals(8, hand.get(4).getValue());
		
		assertArrayEquals(new int[] {0, 3, 2, 0, 0, 0}, evaluater7.testHand());
		hand = evaluater7.getHand();
		assertEquals(0, hand.size());
		
		assertArrayEquals(new int[] {5, 12, 11, 7, 4, 2}, evaluater8.testHand());
		hand = evaluater8.getHand();
		assertEquals(5, hand.size());
		assertEquals(Card.HEARTS, hand.get(0).getSuit());
		assertEquals(Card.HEARTS, hand.get(1).getSuit());
		assertEquals(Card.HEARTS, hand.get(2).getSuit());
		assertEquals(Card.HEARTS, hand.get(3).getSuit());
		assertEquals(Card.HEARTS, hand.get(4).getSuit());
		
		assertArrayEquals(new int[] {6, 11, 7}, evaluater9.testHand());
		hand = evaluater9.getHand();
		assertEquals(5, hand.size());
		assertEquals(7, hand.get(0).getValue());
		assertEquals(7, hand.get(1).getValue());
		assertEquals(11, hand.get(2).getValue());
		assertEquals(11, hand.get(3).getValue());
		assertEquals(11, hand.get(4).getValue());
		
		assertArrayEquals(new int[] {7, 4, 7}, evaluater10.testHand());
		hand = evaluater10.getHand();
		assertEquals(4, hand.size());
		assertEquals(4, hand.get(0).getValue());
		assertEquals(4, hand.get(1).getValue());
		assertEquals(4, hand.get(2).getValue());
		assertEquals(4, hand.get(3).getValue());
		
		assertArrayEquals(new int[] {8, 14}, evaluater11.testHand());
		hand = evaluater11.getHand();
		assertEquals(5, hand.size());
		assertEquals(10, hand.get(0).getValue());
		assertEquals(11, hand.get(1).getValue());
		assertEquals(12, hand.get(2).getValue());
		assertEquals(13, hand.get(3).getValue());
		assertEquals(14, hand.get(4).getValue());
		
		assertArrayEquals(new int[] {8, 14}, evaluater12.testHand());
		hand = evaluater12.getHand();
		assertEquals(5, hand.size());
		assertEquals(10, hand.get(0).getValue());
		assertEquals(11, hand.get(1).getValue());
		assertEquals(12, hand.get(2).getValue());
		assertEquals(13, hand.get(3).getValue());
		assertEquals(14, hand.get(4).getValue());
		
		assertArrayEquals(new int[] {0, 12, 11, 10, 7, 5}, evaluater13.testHand());
		hand = evaluater13.getHand();
		assertEquals(0, hand.size());
	}

}