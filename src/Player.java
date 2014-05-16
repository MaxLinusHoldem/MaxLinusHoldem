import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class Player.
 * This class represents a player in the game, either if it is
 * a user or a AI player.
 * 
 * @author Linus Wåreus & Max Wällstedt
 * @version 1.0 (2014.05.16)
 */
public abstract class Player {
	protected String name; // The player's name.
	protected int ID; // The player's ID.
	protected int money; // The player's money.
	protected int bet; // The player's bet in a current betting round.
	protected Card[] hand; // The player's hand.
	protected Evaluate winningHand; // A combination of the player's cards and
									// the community cards.
	protected boolean isAllIn; // Indicates if the player is all-in.
	public static final int STARTMONEY = 100; // The player's start money.

	// Variables that has to do with the GUI.
	private GamePanel gamePanel;
	private int x;
	private int y;
	private JLabel cardLabel1;
	private JLabel cardLabel2;
	private JLabel cashLabel;
	private JLabel betLabel;

	/**
	 * Default constructor of the class Player.
	 * 
	 * @param name
	 *            The player's name.
	 * @param ID
	 *            The player's ID.
	 * @param gamePanel
	 *            The game panel of the game.
	 * @throws IllegalArgumentException
	 *             If any parameter is wrong.
	 */
	public Player(String name, int ID, GamePanel gamePanel) throws IllegalArgumentException {
		// Throws an exception if the name is null or empty.
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("The player must have a name.");
		}
		// Throws an exception if the ID is less than 0 or larger than 7.
		if (ID < 0 || ID > 7) {
			throw new IllegalArgumentException("The player ID must be within the range of [0, 7] (inclusive).");
		}
		// Throws an exception if the game panel is null.
		if (gamePanel == null) {
			throw new IllegalArgumentException("The player must have a GamePanel to be printed on.");
		}

		this.name = name;
		this.ID = ID;
		this.money = STARTMONEY;
		this.gamePanel = gamePanel;
		this.hand = new Card[2];
		this.winningHand = null;

		createPlayerSlot(); // Creates the player's slot.
	}

	/**
	 * Creates the player's slot in the game.
	 */
	private void createPlayerSlot() {
		BufferedImage chairImg = null;
		String chairImgPath = "../res/orange_circle.png";

		try {
			chairImg = ImageIO.read(new File(chairImgPath));
		} catch (IOException e) {
			System.err.println("error: File \"" + chairImgPath
					+ "\" not found.");
			System.exit(1);
		}

		int chairWidth = chairImg.getWidth();
		int chairHeight = chairImg.getHeight();

		if (ID == 0) {
			x = 920 - chairWidth;
			y = 10;
		} else if (ID == 1) {
			x = 1193 - chairWidth;
			y = 98;
		} else if (ID == 2) {
			x = 1193 - chairWidth;
			y = 608 - chairHeight;
		} else if (ID == 3) {
			x = 920 - chairWidth;
			y = 696 - chairHeight;
		} else if (ID == 4) {
			x = 351;
			y = 696 - chairHeight;
		} else if (ID == 5) {
			x = 78;
			y = 608 - chairHeight;
		} else if (ID == 6) {
			x = 78;
			y = 98;
		} else {
			x = 351;
			y = 10;
		}

		JLabel chairLabel = new JLabel(new ImageIcon(chairImg));
		chairLabel.setBounds(x, y, chairWidth, chairHeight);
		chairLabel.repaint();
		gamePanel.add(chairLabel);
		gamePanel.setPosition(chairLabel, -1);

		JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
		nameLabel.setBounds(x, y + 160, 216, 11);
		nameLabel.setBackground(new Color(0x9F, 0x4F, 0x00));
		nameLabel.setOpaque(true);
		nameLabel.repaint();
		gamePanel.add(nameLabel);
		gamePanel.setPosition(nameLabel, 0);

		cashLabel = new JLabel(String.format("%d $", money),
				SwingConstants.CENTER);
		cashLabel.setBounds(x, y + 175, 216, 11);
		cashLabel.setBackground(new Color(0x9F, 0x4F, 0x00));
		cashLabel.setOpaque(true);
		cashLabel.repaint();
		gamePanel.add(cashLabel);
		gamePanel.setPosition(cashLabel, 0);

		betLabel = new JLabel(String.format("Current bet: %d $", bet),
				SwingConstants.CENTER);
		betLabel.setBounds(x, y + 190, 216, 11);
		betLabel.setBackground(new Color(0x9F, 0x4F, 0x00));
		betLabel.setOpaque(true);
		betLabel.repaint();
		gamePanel.add(betLabel);
		gamePanel.setPosition(betLabel, 0);
	}

	/**
	 * Lets the player act in the game.
	 * 
	 * @param gui
	 *            The GUI of the game.
	 */
	public abstract void act(TexasHoldem gui);

	/**
	 * Gives the player a card.
	 * 
	 * @param card
	 *            The card to give to the player.
	 */
	public void giveCard(Card card) {
		if (hand[0] == null) {
			hand[0] = card;

			BufferedImage cardImg = this instanceof User ? card.getImage()
					: card.getBackImage();
			cardLabel1 = new JLabel(new ImageIcon(cardImg));
			cardLabel1.setBounds(x + 32, y + 60, cardImg.getWidth(),
					cardImg.getHeight());
			cardLabel1.repaint();
			gamePanel.add(cardLabel1);
			gamePanel.setPosition(cardLabel1, 0);
		} else if (hand[1] == null) {
			hand[1] = card;

			BufferedImage cardImg = this instanceof User ? card.getImage()
					: card.getBackImage();
			cardLabel2 = new JLabel(new ImageIcon(cardImg));
			cardLabel2.setBounds(x + 112, y + 60, cardImg.getWidth(),
					cardImg.getHeight());
			cardLabel2.repaint();
			gamePanel.add(cardLabel2);
			gamePanel.setPosition(cardLabel2, 0);
		} else {
			throw new IllegalStateException(
					"error: Player can only hold two cards.");
		}
	}

	/**
	 * Shows the card in the GUI.
	 */
	public void showCards() {
		cardLabel1.setIcon(new ImageIcon(hand[0].getImage()));
		cardLabel2.setIcon(new ImageIcon(hand[1].getImage()));
	}

	/**
	 * Removes the card from the player and from the GUI.
	 */
	public void removeCards() {
		hand[0] = null;
		hand[1] = null;

		if (cardLabel1 != null) {
			gamePanel.remove(cardLabel1);
		}

		if (cardLabel2 != null) {
			gamePanel.remove(cardLabel2);
		}
	}

	/**
	 * Gets the name of the player.
	 * 
	 * @return name The name of the player.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the ID number of the player.
	 * 
	 * @return ID The ID of the player.
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Returns the player's current money balance.
	 * 
	 * @return cash The player's current money balance.
	 */
	public int getMoney() {
		return this.money;
	}

	/**
	 * Adds money to the player's money balance.
	 * 
	 * @param profit
	 *            The money to be added.
	 */
	public void addMoney(int profit) {
		this.money += profit;
		cashLabel.setText(String.format("%d $", money));
	}

	/**
	 * Returns the player's cards.
	 * 
	 * @return The player's cards.
	 */
	public ArrayList<Card> getCards() {
		return new ArrayList<Card>(Arrays.asList(hand));
	}

	/**
	 * Returns the player's bet.
	 * 
	 * @return bet The player's bet.
	 */
	public int getBet() {
		return this.bet;
	}

	/**
	 * Removes the bet from the player.
	 * 
	 * @return bet The player's bet.
	 */
	public int removeBet() {
		int ret = bet;
		bet = 0;
		betLabel.setText(String.format("Current bet: %d $", bet));
		return ret;
	}

	/**
	 * Sets the winning hand of the player.
	 * 
	 * @param hand
	 *            The winning hand.
	 */
	public void setWinningHand(Evaluate hand) {
		this.winningHand = hand;
	}

	/**
	 * Returns the winning hand of the player.
	 * 
	 * @return winningHand The winning hand of the player.
	 */
	public Evaluate getWinningHand() {
		return this.winningHand;
	}

	/**
	 * Lets the player check.
	 */
	public void check() {
	}

	/**
	 * Lets the player call the current bet in the game.
	 * 
	 * @param currentBet
	 *            The current bet in the game.
	 */
	public void call(int currentBet) {
		// Lets the player go all-in if the call amount equals the player's money.
		if (currentBet - bet >= this.money) {
			allIn();
			return;
		}
		this.money -= currentBet - bet;
		this.bet = currentBet;
		cashLabel.setText(String.format("%d $", money));
		betLabel.setText(String.format("Current bet: %d $", bet));
	}

	/**
	 * Lets the player bet in the game.
	 * 
	 * @param bet
	 *            The money to be bet.
	 * @throws IllegalArgumentException
	 *             If the user tries to bet more money than he or she has.
	 */
	public void bet(int amount, int currentRaise) throws IllegalArgumentException {
		// Throws an exception if the amount is less than 0.
		if (amount < 0) {
			throw new IllegalArgumentException("You can't bet a negative amount.");
		}
		// Lets the player go all-in if the amount equals the player's money.
		if (amount == this.money) {
			allIn();
			return;
		}
		// Throws an exception if the amount is less than the big blind.
		if (amount < TexasHoldem.BIGBLIND) {
			throw new IllegalArgumentException("You can't bet less than the big blind.");
		}
		// Throws an exception if the player tries to bet more money than he or she has.
		if (amount > this.money) {
			throw new IllegalArgumentException("You can't bet more money than you have.");
		}
		// Throws an exception if the amount is less than double the previous bet.
		if (amount < currentRaise * 2) {
			throw new IllegalArgumentException("You need to raise at least double the previous raise.");
		}

		this.bet += amount;
		this.money -= amount;
		cashLabel.setText(String.format("%d $", money));
		betLabel.setText(String.format("Current bet: %d $", bet));
	}

	/**
	 * Lets the player fold in the game.
	 */
	public void fold(TexasHoldem gui) {
		gui.getDealer().getActivePlayers().remove(this);
		this.removeCards();
		gui.repaint();
	}

	/**
	 * Lets the player go all-in in the game.
	 */
	public void allIn() {
		isAllIn = true;
		this.bet += this.money;
		this.money = 0;
		cashLabel.setText(String.format("%d $", money));
		betLabel.setText(String.format("Current bet: %d $", bet));
	}

	/**
	 * Lets the player bet the small blind.
	 */
	public void betSmallBlind() {
		this.bet += TexasHoldem.SMALLBLIND;
		this.money -= TexasHoldem.SMALLBLIND;
		cashLabel.setText(String.format("%d $", money));
		betLabel.setText(String.format("Current bet: %d $", bet));

		if (this.money == 0) {
			this.isAllIn = true;
		}
	}

	/**
	 * Tells if the player is all-in or not.
	 * 
	 * @return true of the player is all-in, false if the player isn't.
	 */
	public boolean isAllIn() {
		return isAllIn;
	}

	/**
	 * Sets if the player is all-in or not.
	 * 
	 * @param isAllIn
	 *            true of the player is all-in, false if the player isn't.
	 */
	public void setIsAllIn(boolean isAllIn) {
		this.isAllIn = isAllIn;
	}
}