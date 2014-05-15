import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class TexasHoldem extends JFrame {
	public static final int BIGBLIND = 2;
	public static final int SMALLBLIND = BIGBLIND / 2;
	private Container contentPane;
	private GameScreen gameScreen;
	private ArrayList<Player> players;
	private ArrayList<CommunitySlot> communityCards;
	private Dealer dealer;
	public static final long DELAY = 200;
	private boolean userAction;
	private int currentRaise;
	private int currentBet;
	private int currentBetPlayer;
	private final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

	public static void main(String[] args) {
		new TexasHoldem();
	}

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

	private void play() {
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

			players.get((dealerID + 1) % players.size()).betSmallBlind();
			delay(500);

			players.get((dealerID + 2) % players.size()).bet(BIGBLIND, 0);
			delay(500);
			currentBet = BIGBLIND;
			currentRaise = BIGBLIND;
			currentBetPlayer = (dealerID + 2) % players.size();
			
			dealer.dealCards();

			if (!bettingRound(dealerID + 3)) {
				dealer.removeBoard();
				continue;
			}

			dealer.dealTheFlop();
			
			currentBet = 0;
			currentRaise = 0;
			if (!bettingRound(dealerID + 1)) {
				dealer.removeBoard();
				continue;
			}
			
			currentBet = 0;
			currentRaise = 0;
			dealer.dealTheTurn();

			if (!bettingRound(dealerID + 1)) {
				dealer.removeBoard();
				continue;
			}

			currentBet = 0;
			currentRaise = 0;
			dealer.dealTheRiver();

			if (!bettingRound(dealerID + 1)) {
				dealer.removeBoard();
				continue;
			}

			Player temp = dealer.selectWinner();
			JOptionPane.showMessageDialog(this, temp.getName() + " won " + dealer.getPot() +" $ with a " + temp.getWinningHand());
			dealer.removePot();
			dealer.removeBoard();
			
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getMoney() == 0) {
					players.get(i).removeCards();
					JOptionPane.showMessageDialog(this, players.get(i).getName() + " lost the game!");
					players.remove(i);
					i--;
				}
			}
			if (players.size() == 1) {
				JOptionPane.showMessageDialog(this, players.get(0).getName() + " won the game and " + players.get(0).getMoney() +" $.\nCongratulations!");
				System.exit(0);
			}
		}
	}

	private boolean bettingRound(int firstPlayer) {
		int currentPlayer = firstPlayer;
		currentBetPlayer = currentPlayer;
		boolean finished = false;
		
		while (!finished) {
			currentPlayer = currentPlayer % dealer.getActivePlayers().size();
			dealer.getActivePlayers().get(currentPlayer).act(this);
			if (currentBet == dealer.getActivePlayers().get(currentPlayer).getBet() &&
			   (currentPlayer + 1) % dealer.getActivePlayers().size() == currentBetPlayer) {
				finished = true;
			} else if (currentBet < dealer.getActivePlayers().get(currentPlayer).getBet()) {
				currentRaise = dealer.getActivePlayers().get(currentPlayer).getBet() - currentBet;
				currentBet = dealer.getActivePlayers().get(currentPlayer).getBet();
				currentBetPlayer = currentPlayer;
			}
			
			if (dealer.getActivePlayers().size() == 1) {
				for (Player p : players) {
					dealer.addPot(p.removeBet());
				}
				JOptionPane.showMessageDialog(this, dealer.getActivePlayers().get(0).getName() + " won " + dealer.getPot() +" $.");
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
	
	private void createMenuItems() {
		gameScreen.getNewGameItem().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { newGame(); }
        });
		gameScreen.getNewGameItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
        
        gameScreen.getQuitItem().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { System.exit(0); }
        });
        gameScreen.getQuitItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
        
        gameScreen.getAboutItem().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { about(); }
        });
        gameScreen.getAboutItem().setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, SHORTCUT_MASK));
		
	}
	
	private void createUserButtons() {
		
        gameScreen.getCheckButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { check(); }
        });
        
        gameScreen.getCallButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { call(); }
        });
        
        gameScreen.getBetButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { bet(); }
        });
        
        gameScreen.getRaiseButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { bet(); }
        });
        
        gameScreen.getFoldButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { fold(); }
        });
        
        gameScreen.getAllInButton().addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { allIn(); }
        });
	}
	
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
	
	private void check() {
		userAction = true;
	}
	
	private void call() {
		players.get(0).call(currentBet);
		userAction = true;
	}

	private void bet() {
		int bet;
		
		while (true) {
			try {
				String betStr = JOptionPane.showInputDialog(this, "Enter your bet:");

				if (betStr == null) {
					return;
				}

				bet = Integer.parseInt(betStr);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "You must enter a valid number!");
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
	
	private void fold() {
		players.get(0).fold(this);
		repaint();
		userAction = true;
	}
	
	private void allIn() {
		players.get(0).allIn();
		userAction = true;
	}
	
	public int getCurrentBet() {
		return this.currentBet;
	}
	
	public int getCurrentRaise() {
		return this.currentRaise;
	}
	
	public Dealer getDealer() {
		return this.dealer;
	}
	
	public void newGame() {

	}
	
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
	
	private void about() {
		JOptionPane.showMessageDialog(this,
		                              "MaxLinusHold'em\nVersion 1.0",
		                              "About MaxLinusHoldem",
		                              JOptionPane.INFORMATION_MESSAGE);
	}
}
