import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * The class Dealer.
 * This class acts as the dealer of the game.
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public class Dealer {
	private Deck deck;
	private ArrayList<CommunitySlot> board;
	private ArrayList<Player> activePlayers;
	private int pot;
	private int currentBet;
	private Player currentDealer;
	private GamePanel gamePanel;
	private JLabel potLabel;
	
	public Dealer(ArrayList<Player> activePlayers, GamePanel gamePanel) {
		this.deck = new Deck();
		this.board = new ArrayList<CommunitySlot>();
		this.gamePanel = gamePanel;
		this.activePlayers = new ArrayList<Player>(activePlayers);
		this.currentBet = 0;
		removeCards();
		
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
	 * 
	 */
	public void setCurrentDealer(Player p) {
		this.currentDealer = p;
	}
	
	/**
	 * 
	 */
	public Player getCurrentDealer() {
		return this.currentDealer;
	}
	
	/**
	 * 
	 * @return
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
	 * 
	 */
	public void removeBoard() {
		for (CommunitySlot cs : board) {
			gamePanel.remove(cs);
		}
	}
	
	/**
	 * Selects a winner in the game.
	 */
	public Player selectWinner() {
		Player bestPlayer = null;
		int bestHand = 0;
		for (int i = 0; i < activePlayers.size(); i++) {
			TexasHoldem.delay(500);
			activePlayers.get(i).showCards();
			ArrayList<Card> cards = new ArrayList<Card>();
			for (int j = 0; j < board.size(); j++) {
				cards.add(board.get(j).getCard());
			}
			Evaluate hand = new Evaluate(activePlayers.get(i).getCards(), cards);
			activePlayers.get(i).setWinningHand(hand);
			int temp = hand.testHand();
			if (temp > bestHand) {
				bestPlayer = activePlayers.get(i);
				bestHand = temp;
			}
			System.out.println("The player " + activePlayers.get(i).getName() + " got a " + hand + ": " + hand.getHand());
		}
		bestPlayer.addMoney(this.getPot());
		return bestPlayer;
	}
	
	/*
	public static void main(String[] args) {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new User("Linus", 0, 100, null));
		players.add(new User("Max", 1, 100, null));
		Dealer dealer = new Dealer(players);
		dealer.dealCards();
		dealer.dealTheFlop();
		dealer.dealTheTurn();
		dealer.dealTheRiver();
		dealer.selectWinner();
	}*/
}
