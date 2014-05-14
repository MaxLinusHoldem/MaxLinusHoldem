import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
	private int currentBet;
	private int currentBetPlayer;

	public static void main(String[] args) {
		new TexasHoldem();
	}

	public TexasHoldem() {
		super("Texas Holdem");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1271, 800));
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

		createUserButtons();
		
		pack();
		setVisible(true);
		
		this.userAction = false;
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

			players.get((dealerID + 1) % 8).betSmallBlind();
			delay(500);

			players.get((dealerID + 2) % 8).bet(BIGBLIND);
			delay(500);
			currentBet = BIGBLIND;
			currentBetPlayer = (dealerID + 2) % 8;
			
			dealer.dealCards();

			if (!bettingRound(dealerID + 3)) {
				dealer.removeBoard();
				continue;
			}

			dealer.dealTheFlop();
			
			currentBet = 0;
			if (!bettingRound(dealerID + 1)) {
				dealer.removeBoard();
				continue;
			}
			
			currentBet = 0;
			dealer.dealTheTurn();

			if (!bettingRound(dealerID + 1)) {
				dealer.removeBoard();
				continue;
			}

			currentBet = 0;
			dealer.dealTheRiver();

			if (!bettingRound(dealerID + 1)) {
				dealer.removeBoard();
				continue;
			}

			Player temp = dealer.selectWinner();
			JOptionPane.showMessageDialog(this, temp.getName() + " won " + dealer.getPot() +" $ with a " + temp.getWinningHand());
			dealer.removePot();
			dealer.removeBoard();
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
			if (players.get(i).getMoney() == 0) {
				players.remove(players.get(i));
				i--;
			}
		}
		return true;
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
				bet = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter your bet:"));
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "You must enter a valid number!");
				continue;
			}
			
			try {
				players.get(0).bet(bet);
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
	
	public int getCurrentBet() {
		return this.currentBet;
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
	
	public Dealer getDealer() {
		return this.dealer;
	}
}
