import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class Player {
	private String name;
	private int ID;
	private double cash;
	private double bet;
	private Card[] hand;

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
		cash = 100.0;
		this.gamePanel = gamePanel;
		hand = new Card[2];

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

		cashLabel = new JLabel(String.format("%.2f $", cash), SwingConstants.CENTER);
		cashLabel.setBounds(x, y + 175, 216, 11);
		cashLabel.setBackground(new Color(0x9F, 0x4F, 0x00));
		cashLabel.setOpaque(true);
		cashLabel.repaint();
		gamePanel.add(cashLabel);
		gamePanel.setPosition(cashLabel, 0);

		betLabel = new JLabel(String.format("Current bet: %.2f $", bet), SwingConstants.CENTER);
		betLabel.setBounds(x, y + 190, 216, 11);
		betLabel.setBackground(new Color(0x9F, 0x4F, 0x00));
		betLabel.setOpaque(true);
		betLabel.repaint();
		gamePanel.add(betLabel);
		gamePanel.setPosition(betLabel, 0);
	}

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

	public String getName() {
		return name;
	}

	public int getID() {
		return ID;
	}

	public double getCash() {
		return cash;
	}

	public double getBet() {
		return bet;
	}

	public void bet(double amount) {
		bet += amount;
		cash -= amount;
		cashLabel.setText(String.format("%.2f $", cash));
		betLabel.setText(String.format("Current bet: %.2f $", bet));
	}

	public double takeBet() {
		double ret = bet;
		bet = 0.0;
		betLabel.setText(String.format("Current bet: %.2f $", bet));
		return ret;
	}
}
