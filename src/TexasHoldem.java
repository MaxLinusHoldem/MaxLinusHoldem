import java.util.*;
import java.awt.*;
import javax.swing.*;

public class TexasHoldem extends JFrame {
	private final double smallBlind = 5.0;
	private final double bigBlind = 10.0;
	private Container contentPane;
	private GameScreen gameScreen;
	private ArrayList<Player> players;
	private ArrayList<CommunitySlot> communityCards;
	private Deck deck;
	private double pot;

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
		pot = 0.0;

		while (true) {
			dealerID++;

			for (CommunitySlot cs : communityCards) {
				gameScreen.getGamePanel().remove(cs);
			}

			communityCards.clear();

			for (Player p : players) {
				p.removeCards();
			}

			deck = new Deck();
			gameScreen.getGamePanel().setDealerAndBlinds(dealerID);

			repaint();

			players.get((dealerID + 1) % 8).bet(smallBlind);
			delay(500);

			players.get((dealerID + 2) % 8).bet(bigBlind);
			delay(500);

			dealHands(deck);

			/*if (bettingRound(dealerID + 3)) {
				continue;
			}*/

			dealCommunity(0);
			dealCommunity(1);
			dealCommunity(2);

			/*if (bettingRound(dealerID + 1)) {
				continue;
			}*/

			dealCommunity(3);

			/*if (bettingRound(dealerID + 1)) {
				continue;
			}*/

			dealCommunity(4);

			/*if (bettingRound(dealerID + 1)) {
				continue;
			}*/

			//showdown();
		}
	}

	private void dealHands(Deck deck) {
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
	}

	private void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
