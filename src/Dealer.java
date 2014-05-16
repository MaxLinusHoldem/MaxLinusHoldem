import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * The class Dealer.
 * This class acts as the dealer of the game.
 * 
 * @author Linus Wåreus & Max Wällstedt
 * @version 1.0 (2014.05.16)
 */
public class Dealer {
	private Deck deck; // The deck.
	private ArrayList<CommunitySlot> board; // The board with the community cards.
	private ArrayList<Player> activePlayers; // All active players in the game.
	private int pot; // The pot in the game.
	private int currentBet; // The current bet in the game.
	private GamePanel gamePanel; // The game panel in the GUI.
	private JLabel potLabel; // The pot label in the GUI.

	/**
	 * Default constructor of the class Dealer.
	 * 
	 * @param activePlayers All active players in the game.
	 * @param gamePanel The game panel in the GUI.
	 */
	public Dealer(ArrayList<Player> activePlayers, GamePanel gamePanel) {
		this.deck = new Deck();
		this.board = new ArrayList<CommunitySlot>();
		this.gamePanel = gamePanel;
		this.activePlayers = new ArrayList<Player>(activePlayers);
		this.currentBet = 0;
		removeCards();

		// Initiates the pot label where the pot is shown in the GUI.
		potLabel = new JLabel("Pot: " + pot + "$", SwingConstants.CENTER);
		potLabel.setBounds((1271 - 216) / 2, 385, 216, 11);
		potLabel.setBackground(new Color(0x9F, 0x4F, 0x00));
		potLabel.setOpaque(true);
		potLabel.repaint();
		gamePanel.add(potLabel);
		gamePanel.setPosition(potLabel, 0);
	}

	/**
	 * Removes all the cards from the players.
	 */
	private void removeCards() {
		for (Player p : activePlayers) {
			p.removeCards();
		}
	}

	/**
	 * Deals two cards to every player in the game.
	 */
	public void dealCards() {
		for (int i = 0; i < 2; i++) {
			for (Player p : activePlayers) {
				p.giveCard(deck.drawCard());
				TexasHoldem.delay(TexasHoldem.DELAY);
			}
		}
	}

	/**
	 * Deals the flop.
	 */
	public void dealTheFlop() {
		deck.drawCard();
		for (int i = 0; i < 3; i++) {
			board.add(new CommunitySlot(deck.drawCard(), i, gamePanel));
			TexasHoldem.delay(TexasHoldem.DELAY);
		}
	}

	/**
	 * Deals the turn.
	 */
	public void dealTheTurn() {
		deck.drawCard();
		board.add(new CommunitySlot(deck.drawCard(), 3, gamePanel));
		TexasHoldem.delay(TexasHoldem.DELAY);
	}

	/**
	 * Deals the river.
	 */
	public void dealTheRiver() {
		deck.drawCard();
		board.add(new CommunitySlot(deck.drawCard(), 4, gamePanel));
		TexasHoldem.delay(TexasHoldem.DELAY);
	}

	/**
	 * Places a bet in the pot.
	 * 
	 * @param bet The bet to be placed in the pot.
	 */
	public void addPot(int bet) {
		this.pot += bet;
		potLabel.setText("Pot: " + pot + "$");
	}

	/**
	 * Returns the pot's current balance.
	 * 
	 * @return pot The pot's current balance.
	 */
	public int getPot() {
		return this.pot;
	}

	/**
	 * Removes and returns the pot's current balance.
	 * 
	 * @return pot The pot's current balance.
	 */
	public int removePot() {
		int pot = this.pot;
		this.pot = 0;
		potLabel.setText("Pot: " + pot + "$");
		return pot;
	}

	/**
	 * Returns the current bet.
	 * 
	 * @return currentBet The current bet.
	 */
	public int getCurrentBet() {
		return this.currentBet;
	}

	/**
	 * Sets the current bet.
	 * 
	 * @param currentBet The new current bet.
	 */
	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}

	/**
	 * Returns all active players in the game.
	 * 
	 * @return activePlayers All active players in the game.
	 */
	public ArrayList<Player> getActivePlayers() {
		return this.activePlayers;
	}

	/**
	 * Removes a player from the current game.
	 * 
	 * @param p The player to be removed.
	 */
	public void removeActivePlayer(Player p) {
		this.activePlayers.remove(p);
	}

	/**
	 * Removes all community cards from the board.
	 */
	public void removeBoard() {
		for (CommunitySlot cs : board) {
			gamePanel.remove(cs);
		}
	}

	/**
	 * Selects a winner in a game round.
	 * 
	 * @return bestPlayer The best player in the game round.
	 */
	public Player selectWinner() {
		Player bestPlayer = null;
		int[] bestHand = new int[6]; // The player's best hand.
		
		// Selects a winner in the game round.
		for (int i = 0; i < activePlayers.size(); i++) {
			// Simulates the dealer thinking.
			TexasHoldem.delay(500);
			// Shows the player's cards.
			activePlayers.get(i).showCards();
			ArrayList<Card> cards = new ArrayList<Card>();
			
			// Gets the cards from the board.
			for (int j = 0; j < board.size(); j++) {
				cards.add(board.get(j).getCard());
			}
			// Evaluates the cards.
			Evaluate hand = new Evaluate(activePlayers.get(i).getCards(), cards);
			// Sets the winning hand of the player.
			activePlayers.get(i).setWinningHand(hand);
			
			int[] temp = hand.testHand();
			// Compares the player's hand to see if it is the best hand
			// in the game round so far.
			for (int j = 0; j < temp.length; j++) {
				if (temp[j] > bestHand[j]) {
					bestPlayer = activePlayers.get(i);
					bestHand = temp;
					break;
				} else if (temp[j] < bestHand[j]) {
					break;
				} else {
					// TODO: Implements split pot.
				}
			}
		}
		
		// Adds the money in the pot to the winning player.
		bestPlayer.addMoney(this.getPot());
		return bestPlayer;
	}
}