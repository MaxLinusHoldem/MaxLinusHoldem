import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Player {
	private String name;
	private int ID;
	private int money;
	private int bet;
	private Card[] hand;
	private boolean isActive;

	private GamePanel gamePanel;
	private int x;
	private int y;
	private JLabel cardLabel1;
	private JLabel cardLabel2;
	private JLabel cashLabel;
	private JLabel betLabel;

	public Player(String name, int ID, GamePanel gamePanel) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("error: Player must have a name.");
		} else if (ID < 0 || ID > 7) {
			throw new IllegalArgumentException("error: Player ID must be within the range of [0, 7] (inclusive).");
		} else if (gamePanel == null) {
			throw new IllegalArgumentException("error: Player must have a GamePanel to be printed on.");
		}

		this.name = name;
		this.ID = ID;
		this.money = 100;
		this.gamePanel = gamePanel;
		this.hand = new Card[2];
		this.isActive = true;

		BufferedImage chairImg = null;
		String chairImgPath = "../res/orange_circle.png";

		try {
			chairImg = ImageIO.read(new File(chairImgPath));
		} catch (IOException e) {
			System.err.println("error: File \"" + chairImgPath + "\" not found.");
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

		cashLabel = new JLabel(String.format("%d $", money), SwingConstants.CENTER);
		cashLabel.setBounds(x, y + 175, 216, 11);
		cashLabel.setBackground(new Color(0x9F, 0x4F, 0x00));
		cashLabel.setOpaque(true);
		cashLabel.repaint();
		gamePanel.add(cashLabel);
		gamePanel.setPosition(cashLabel, 0);

		betLabel = new JLabel(String.format("Current bet: %d $", bet), SwingConstants.CENTER);
		betLabel.setBounds(x, y + 190, 216, 11);
		betLabel.setBackground(new Color(0x9F, 0x4F, 0x00));
		betLabel.setOpaque(true);
		betLabel.repaint();
		gamePanel.add(betLabel);
		gamePanel.setPosition(betLabel, 0);
	}
	
	public abstract boolean act(int currentBet);

	public void giveCard(Card card) {
		if (hand[0] == null) {
			hand[0] = card;

			BufferedImage cardImg = this instanceof User ? card.getImage() : card.getBackImage();
			cardLabel1 = new JLabel(new ImageIcon(cardImg));
			cardLabel1.setBounds(x + 32, y + 60, cardImg.getWidth(), cardImg.getHeight());
			cardLabel1.repaint();
			gamePanel.add(cardLabel1);
			gamePanel.setPosition(cardLabel1, 0);
		} else if (hand[1] == null) {
			hand[1] = card;

			BufferedImage cardImg = this instanceof User ? card.getImage() : card.getBackImage();
			cardLabel2 = new JLabel(new ImageIcon(cardImg));
			cardLabel2.setBounds(x + 112, y + 60, cardImg.getWidth(), cardImg.getHeight());
			cardLabel2.repaint();
			gamePanel.add(cardLabel2);
			gamePanel.setPosition(cardLabel2, 0);
		} else {
			throw new IllegalStateException("error: Player can only hold two cards.");
		}
	}

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
	 * Gets the name of the person.
	 * 
	 * @return name The name of the person.
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
	 * Returns the players current money balance.
	 * 
	 * @return cash The players current money balance.
	 */
	public int getMoney() {
		return this.money;
	}

	/**
	 * Adds money to the players money balance.
	 * 
	 * @param profit The money to be added.
	 */
	public void addMoney(int profit) {
		this.money += profit;
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
	 * Returns the varible myBet.
	 * 
	 * @return myBet The varible myBet.
	 */
	public int getBet() {
		return this.bet;
	}
	
	public int takeBet() {
		int ret = bet;
		bet = 0;
		betLabel.setText(String.format("Current bet: %d $", bet));
		return ret;
	}
	
	/**
	 * Lets the player cecked.
	 * 
	 * @return true Indicates that the play made no bet.
	 */
	public boolean check() {
		return true;
	}
	
	/**
	 * Lets the player call the current bet in the game.
	 * 
	 * @param currentBet The current bet in the game.
	 * @return true Indicates that the player is still in the game.
	 * @throws IllegalArgumentException If the user tries to bet more money than he or she has. 
	 */
	public boolean call(int currentBet) throws IllegalArgumentException {
		if (currentBet - bet > this.money) {
			throw new IllegalArgumentException ("You can't bet more maney than you have.");
		}
		this.money -= currentBet + bet;
		this.bet = currentBet;
		return true;
	}
	
	/**
	 * Lets the player bet in the game.
	 * 
	 * @param bet The cash to be bet.
	 * @return true Indicates that the player is still in the game.
	 * @throws IllegalArgumentException If the user tries to bet more money than he or she has. 
	 */
	public boolean bet(int amount) throws IllegalArgumentException {
		if (bet > this.money) {
			throw new IllegalArgumentException ("You can't bet more maney than you have.");
		}
		bet += amount;
		this.money -= amount;
		cashLabel.setText(String.format("%d $", money));
		betLabel.setText(String.format("Current bet: %d $", bet));
		return true;
	}
	
	/**
	 * Lets the player fold the game.
	 * 
	 * @return false Indicates the the player wants to fold.
	 */
	public boolean fold() {
		this.removeCards();
		this.isActive = false;
		return false;
	}
}