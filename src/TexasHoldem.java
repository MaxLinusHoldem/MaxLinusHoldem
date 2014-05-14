import java.util.*;
import java.awt.*;
import javax.swing.*;

public class TexasHoldem extends JFrame {
	private int smallBlind = 1;
	private int bigBlind = 2;
	private Container contentPane;
	private GameScreen gameScreen;
	private ArrayList<Player> players;
	private ArrayList<CommunitySlot> communityCards;
	private Dealer dealer;
	public static final long DELAY = 200;

	public static void main(String[] args) {
		new TexasHoldem();
	}

	public TexasHoldem() {
		super("Texas Holdem");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = getContentPane();

		gameScreen = new GameScreen();
		contentPane.add(gameScreen);
		setJMenuBar(gameScreen.getJMenuBar());

		players = new ArrayList<Player>();
		communityCards = new ArrayList<CommunitySlot>();

		players.add(new User("Max", 0, gameScreen.getGamePanel()));

		for (int i = 0; i < 7; i++) {
			players.add(new AI("COM" + i, i + 1, gameScreen.getGamePanel()));
		}

		contentPane.add(gameScreen);

		pack();
		setVisible(true);

		newGame();
	}

	private void newGame() {
		int dealerID = -1;

		while (true) {
			dealerID++;

			communityCards.clear();

			for (Player p : players) {
				p.removeCards();
			}

			dealer = new Dealer(players, gameScreen.getGamePanel());
			gameScreen.getGamePanel().setDealerAndBlinds(dealerID);

			repaint();

			players.get((dealerID + 1) % 8).bet(smallBlind);
			delay(500);

			players.get((dealerID + 2) % 8).bet(bigBlind);
			delay(500);

			dealer.dealCards();

			/*if (bettingRound(dealerID + 3)) {
				continue;
			}*/

			dealer.dealTheFlop();

			/*if (bettingRound(dealerID + 1)) {
				continue;
			}*/

			dealer.dealTheTurn();

			/*if (bettingRound(dealerID + 1)) {
				continue;
			}*/

			dealer.dealTheRiver();

			/*if (bettingRound(dealerID + 1)) {
				continue;
			}*/

			//showdown();
			dealer.removeBoard();
		}
	}

/*	private void dealHands(Deck deck) {
		for (int i = 0; i < 2; i++) {
			for (Player p : players) {
				p.giveCard(deck.drawCard());
				delay(500);
			}
		}
	}

	private void dealCommunity(int index) {
		communityCards.add(new CommunitySlot(index, deck.drawCard(), gameScreen.getGamePanel()));
		delay(500);
	}*/

	/**
	 * 
	 * @param time
	 */
	public static void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
