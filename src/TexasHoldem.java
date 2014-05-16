import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The main window of the game.
 * 
 * @author Linus Wåreus & Max Wällstedt
 * @version 1.0 (2014.05.16)
 */
@SuppressWarnings("serial")
public class TexasHoldem extends JFrame {
	public static final int BIGBLIND = 2; // The big blind in the game.
	public static final int SMALLBLIND = BIGBLIND / 2; // The small blind in the game.
	private Container contentPane;
	private GameScreen gameScreen;
	private ArrayList<Player> players; // All players in the game.
	private ArrayList<CommunitySlot> communityCards; // The community cards in the game.
	private Dealer dealer; // The dealer in the game.
	public static final long DELAY = 200; // The delay used in the games GUI.
	private boolean userAction; // Indicates if the user has act or not.
	private int currentRaise; // The current raise in the game.
	private int currentBet; // The current bet in the game.
	private int currentBetPlayer; // The current player who made the current bet in the game.
	private final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

	/**
	 * The main method that will make a new TexasHoldem object.
	 * 
	 * @param args
	 *            The command-line arguments. Will be ignored in the game.
	 */
	public static void main(String[] args) {
		new TexasHoldem();
	}

	/**
	 * Creates the main window of the game.
	 */
	public TexasHoldem() {
		super("MaxLinus Hold'em");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1271, 800));
		contentPane = getContentPane();

		gameScreen = new GameScreen();
		contentPane.add(gameScreen);
		setJMenuBar(gameScreen.getJMenuBar());

		createMenuItems();

		players = new ArrayList<Player>();
		communityCards = new ArrayList<CommunitySlot>();

		String name = "";
		while (name.isEmpty()) {
			name = JOptionPane.showInputDialog(this, "Enter your name:");
			if (name == null) {
				System.exit(0);
			}
		}
		players.add(new User(name, 0, gameScreen.getGamePanel()));

		for (int i = 0; i < 7; i++) {
			players.add(new AI("COM" + i, i + 1, gameScreen.getGamePanel()));
		}

		contentPane.add(gameScreen);

		createUserButtons();

		pack();
		setVisible(true);

		this.userAction = false;
		play();
	}

	/**
	 * Start the main loop of the game.
	 */
	private void play() {
		int dealerID = -1;

		while (true) {
			dealerID++;

			communityCards.clear();

			for (Player p : players) {
				p.removeCards();
				p.setIsAllIn(false);
			}

			dealer = new Dealer(players, gameScreen.getGamePanel());
			gameScreen.getGamePanel().setDealerAndBlinds(dealerID, players);

			repaint();

			if (players.size() == 2) {
				players.get(dealerID % players.size()).betSmallBlind();
				delay(500);

				players.get((dealerID + 1) % players.size()).bet(BIGBLIND, 0);
				delay(500);

				currentBetPlayer = (dealerID + 1) % players.size();
			} else {
				players.get((dealerID + 1) % players.size()).betSmallBlind();
				delay(500);

				players.get((dealerID + 2) % players.size()).bet(BIGBLIND, 0);
				delay(500);

				currentBetPlayer = (dealerID + 2) % players.size();
			}
			currentBet = BIGBLIND;
			currentRaise = BIGBLIND;

			dealer.dealCards();

			if (players.size() == 2) {
				if (!bettingRound(dealerID % dealer.getActivePlayers().size())) {
					dealer.removeBoard();
					continue;
				}
			} else {
				if (!bettingRound((dealerID + 3) % dealer.getActivePlayers().size())) {
					dealer.removeBoard();
					continue;
				}
			}

			dealer.dealTheFlop();

			currentBet = 0;
			currentRaise = 0;

			if (!bettingRound((dealerID + 1) % dealer.getActivePlayers().size())) {
				dealer.removeBoard();
				continue;
			}

			currentBet = 0;
			currentRaise = 0;
			dealer.dealTheTurn();

			if (!bettingRound((dealerID + 1) % dealer.getActivePlayers().size())) {
				dealer.removeBoard();
				continue;
			}

			currentBet = 0;
			currentRaise = 0;
			dealer.dealTheRiver();

			if (!bettingRound((dealerID + 1) % dealer.getActivePlayers().size())) {
				dealer.removeBoard();
				continue;
			}

			Player temp = dealer.selectWinner();
			JOptionPane.showMessageDialog(this, temp.getName() + " won "
					+ dealer.getPot() + " $ with a " + temp.getWinningHand());
			dealer.removePot();
			dealer.removeBoard();

			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getMoney() == 0) {
					players.get(i).removeCards();
					JOptionPane.showMessageDialog(this, players.get(i)
							.getName() + " lost the game!");
					players.remove(i);
					i--;
				}
			}
			if (players.size() == 1) {
				JOptionPane.showMessageDialog(this, players.get(0).getName()
						+ " won the game and " + players.get(0).getMoney()
						+ " $.\nCongratulations!");
				System.exit(0);
			}
		}
	}

	/**
	 * Starts a betting round.
	 * 
	 * @param firstPlayer
	 *            The player that start the betting round.
	 * @return true if the game is to go on and false if everyone but one player
	 *         folds.
	 */
	private boolean bettingRound(int firstPlayer) {
		int currentPlayer = firstPlayer;
		currentBetPlayer = currentPlayer;
		boolean finished = false;

		while (!finished) {
			currentPlayer = currentPlayer % dealer.getActivePlayers().size();
			int lastSize = dealer.getActivePlayers().size();
			dealer.getActivePlayers().get(currentPlayer).act(this);
			if (!(lastSize > dealer.getActivePlayers().size())) {
				int newCurrentBet = dealer.getActivePlayers().get(currentPlayer).isAllIn() ?
						      		currentBet : dealer.getActivePlayers().get(currentPlayer).getBet();
				
				if (currentBet == newCurrentBet && (currentPlayer + 1) % dealer.getActivePlayers().size() == currentBetPlayer) {
					finished = true;
				} else if (currentBet < newCurrentBet) {
					currentRaise = dealer.getActivePlayers().get(currentPlayer).getBet() - currentBet;
					currentBet = dealer.getActivePlayers().get(currentPlayer).getBet();
					currentBetPlayer = currentPlayer;
				}
			}
			
			if (dealer.getActivePlayers().size() == 1) {
				for (Player p : players) {
					dealer.addPot(p.removeBet());
				}
				JOptionPane.showMessageDialog(this, dealer.getActivePlayers().get(0).getName() + " won " + dealer.getPot() + " $.");
				dealer.getActivePlayers().get(0).addMoney(dealer.removePot());
				return false;
			}
			currentPlayer++;
		}

		for (int i = 0; i < players.size(); i++) {
			dealer.addPot(players.get(i).removeBet());
		}
		return true;
	}

	/**
	 * Adds action listeners to the JMenuItems in the menu bar.
	 */
	private void createMenuItems() {
		gameScreen.getNewGameItem().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		gameScreen.getNewGameItem().setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));

		gameScreen.getQuitItem().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		gameScreen.getQuitItem().setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));

		gameScreen.getAboutItem().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about();
			}
		});
		gameScreen.getAboutItem().setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, SHORTCUT_MASK));

	}

	/**
	 * Adds action listeners to the action buttons.
	 */
	private void createUserButtons() {

		gameScreen.getCheckButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				check();
			}
		});

		gameScreen.getCallButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				call();
			}
		});

		gameScreen.getBetButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bet();
			}
		});

		gameScreen.getRaiseButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bet();
			}
		});

		gameScreen.getFoldButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fold();
			}
		});

		gameScreen.getAllInButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				allIn();
			}
		});
	}

	/**
	 * Prompts the user for an action.
	 */
	public void userAction() {
		if (currentBet == 0) {
			gameScreen.showButtons(false);
		} else {
			gameScreen.showButtons(true);
		}

		pack();
		repaint();

		userAction = false;
		while (!userAction) {
			delay(1);
		}

		gameScreen.hideButtons();
	}

	/**
	 * The action "check".
	 */
	private void check() {
		userAction = true;
	}

	/**
	 * The action "call".
	 */
	private void call() {
		players.get(0).call(currentBet);
		userAction = true;
	}

	/**
	 * The action "bet".
	 */
	private void bet() {
		int bet;

		while (true) {
			try {
				String betStr = JOptionPane.showInputDialog(this,
						"Enter your bet:");

				if (betStr == null) {
					return;
				}

				bet = Integer.parseInt(betStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this,
						"You must enter a valid number!");
				continue;
			}

			try {
				players.get(0).bet(bet, currentRaise);
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
				continue;
			}
			break;
		}
		userAction = true;
	}

	/**
	 * The action "fold".
	 */
	private void fold() {
		players.get(0).fold(this);
		userAction = true;
	}

	/**
	 * The action "all-in".
	 */
	private void allIn() {
		players.get(0).allIn();
		userAction = true;
	}

	/**
	 * Returns the current bet of the game.
	 * 
	 * @return The current bet of the game.
	 */
	public int getCurrentBet() {
		return this.currentBet;
	}

	/**
	 * Returns the current raise of the game.
	 * 
	 * @return The current raise of the game.
	 */
	public int getCurrentRaise() {
		return this.currentRaise;
	}

	/**
	 * Returns the dealer of the game.
	 * 
	 * @return The dealer of the game.
	 */
	public Dealer getDealer() {
		return this.dealer;
	}

	/**
	 * Starts a new game.
	 */
	public void newGame() {
		// TODO: implement
	}

	/**
	 * Creates a delay in the runtime.
	 * 
	 * @param time
	 *            The amount of milliseconds to delay.
	 */
	public static void delay(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Displays an about window.
	 */
	private void about() {
		JOptionPane.showMessageDialog(this, "MaxLinusHold'em\nVersion 1.0",
				"About MaxLinusHoldem", JOptionPane.INFORMATION_MESSAGE);
	}
}