import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

/**
 * A panel that will contain all the graphical elements of the game.
 * 
 * @author Max Wällstedt
 * @version 1.0 (2014.05.16)
 */
@SuppressWarnings("serial")
public class GamePanel extends JLayeredPane {
	private BufferedImage tableImg;
	private BufferedImage dealerImg;
	private BufferedImage smallBlindImg;
	private BufferedImage bigBlindImg;
	private JLabel dealerLabel;
	private JLabel smallBlindLabel;
	private JLabel bigBlindLabel;

	/**
	 * Creates the GamePanel and prints the background image of a Texas Hold 'em
	 * table on it.
	 */
	public GamePanel() {
		setLayout(null);

		String tableImgPath = "../res/table.png";

		try {
			tableImg = ImageIO.read(new File(tableImgPath));
		} catch (IOException e) {
			System.out.println("error: File \"" + tableImgPath
					+ "\" not found.");
			System.exit(1);
		}

		String dealerImgPath = "../res/buttons/dealer.png";

		try {
			dealerImg = ImageIO.read(new File(dealerImgPath));
		} catch (IOException e) {
			System.out.println("error: File \"" + dealerImgPath
					+ "\" not found.");
			System.exit(1);
		}

		dealerLabel = new JLabel(new ImageIcon(dealerImgPath));

		String smallBlindImgPath = "../res/buttons/small_blind.png";

		try {
			smallBlindImg = ImageIO.read(new File(smallBlindImgPath));
		} catch (IOException e) {
			System.out.println("error: File \"" + smallBlindImgPath
					+ "\" not found.");
			System.exit(1);
		}

		smallBlindLabel = new JLabel(new ImageIcon(smallBlindImg));

		String bigBlindImgPath = "../res/buttons/big_blind.png";

		try {
			bigBlindImg = ImageIO.read(new File(bigBlindImgPath));
		} catch (IOException e) {
			System.out.println("error: File \"" + bigBlindImgPath
					+ "\" not found.");
			System.exit(1);
		}

		bigBlindLabel = new JLabel(new ImageIcon(bigBlindImg));

		setPreferredSize(new Dimension(tableImg.getWidth(),
				tableImg.getHeight()));

		JLabel tableLabel = new JLabel(new ImageIcon(tableImg));
		tableLabel.setBounds(0, 0, tableImg.getWidth(), tableImg.getHeight());
		tableLabel.repaint();
		add(tableLabel);
	}

	/**
	 * Prints the dealer and blind buttons on the specified indices.
	 * 
	 * @param dealerID
	 *            The ID of the player that will hold the dealer button.
	 * @param players
	 *            The list of players that can be dealer or blinds.
	 */
	public void setDealerAndBlinds(int dealerID, ArrayList<Player> players) {
		//int horizontalDistance = 294;
		int horizontalDistance = 310;
		int horizontalDistanceLong = 424;

		int verticalDistance = 214;
		int verticalDistanceLong = 244;

		dealerID = dealerID % players.size();

		int dealerX = 0, dealerY = 0;
		int smallBlindX = 0, smallBlindY = 0;
		int bigBlindX = 0, bigBlindY = 0;

		int smallBlindID;
		int bigBlindID;

		if (players.size() == 2) {
			smallBlindID = players.get(dealerID).getID();
			bigBlindID = players.get((dealerID + 1) % players.size()).getID();
		} else {
			smallBlindID = players.get((dealerID + 1) % players.size()).getID();
			bigBlindID = players.get((dealerID + 2) % players.size()).getID();
		}

		dealerID = players.get(dealerID % players.size()).getID();

		if (dealerID == 0) {
			dealerX = 1271 - horizontalDistanceLong - dealerImg.getWidth();
			dealerY = verticalDistance;
		} else if (dealerID == 1) {
			dealerX = 1271 - horizontalDistance - dealerImg.getWidth();
			dealerY = verticalDistance;
		} else if (dealerID == 2) {
			dealerX = 1271 - horizontalDistance - dealerImg.getWidth();
			dealerY = 706 - verticalDistanceLong - dealerImg.getHeight();
		} else if (dealerID == 3) {
			dealerX = 1271 - horizontalDistanceLong - dealerImg.getWidth();
			dealerY = 706 - verticalDistance - dealerImg.getHeight();
		} else if (dealerID == 4) {
			dealerX = horizontalDistanceLong;
			dealerY = 706 - verticalDistance - dealerImg.getHeight();
		} else if (dealerID == 5) {
			dealerX = horizontalDistance;
			dealerY = 706 - verticalDistanceLong - dealerImg.getHeight();
		} else if (dealerID == 6) {
			dealerX = horizontalDistance;
			dealerY = verticalDistanceLong;
		} else {
			dealerX = horizontalDistanceLong;
			dealerY = verticalDistance;
		}

		if (smallBlindID == 0) {
			smallBlindX = 1271 - horizontalDistanceLong
					- smallBlindImg.getWidth();
			smallBlindY = verticalDistance;
		} else if (smallBlindID == 1) {
			smallBlindX = 1271 - horizontalDistance - smallBlindImg.getWidth();
			smallBlindY = verticalDistanceLong;
		} else if (smallBlindID == 2) {
			smallBlindX = 1271 - horizontalDistance - smallBlindImg.getWidth();
			smallBlindY = 706 - verticalDistanceLong
					- smallBlindImg.getHeight();
		} else if (smallBlindID == 3) {
			smallBlindX = 1271 - horizontalDistanceLong
					- smallBlindImg.getWidth();
			smallBlindY = 706 - verticalDistance - smallBlindImg.getHeight();
		} else if (smallBlindID == 4) {
			smallBlindX = horizontalDistanceLong;
			smallBlindY = 706 - verticalDistance - smallBlindImg.getHeight();
		} else if (smallBlindID == 5) {
			smallBlindX = horizontalDistance;
			smallBlindY = 706 - verticalDistanceLong
					- smallBlindImg.getHeight();
		} else if (smallBlindID == 6) {
			smallBlindX = horizontalDistance;
			smallBlindY = verticalDistanceLong;
		} else {
			smallBlindX = horizontalDistanceLong;
			smallBlindY = verticalDistance;
		}

		if (bigBlindID == 0) {
			bigBlindX = 1271 - horizontalDistanceLong - bigBlindImg.getWidth();
			bigBlindY = verticalDistance;
		} else if (bigBlindID == 1) {
			bigBlindX = 1271 - horizontalDistance - bigBlindImg.getWidth();
			bigBlindY = verticalDistanceLong;
		} else if (bigBlindID == 2) {
			bigBlindX = 1271 - horizontalDistance - bigBlindImg.getWidth();
			bigBlindY = 706 - verticalDistanceLong - bigBlindImg.getHeight();
		} else if (bigBlindID == 3) {
			bigBlindX = 1271 - horizontalDistanceLong - bigBlindImg.getWidth();
			bigBlindY = 706 - verticalDistance - bigBlindImg.getHeight();
		} else if (bigBlindID == 4) {
			bigBlindX = horizontalDistanceLong;
			bigBlindY = 706 - verticalDistance - bigBlindImg.getHeight();
		} else if (bigBlindID == 5) {
			bigBlindX = horizontalDistance;
			bigBlindY = 706 - verticalDistanceLong - bigBlindImg.getHeight();
		} else if (bigBlindID == 6) {
			bigBlindX = horizontalDistance;
			bigBlindY = verticalDistanceLong;
		} else {
			bigBlindX = horizontalDistanceLong;
			bigBlindY = verticalDistance;
		}

		remove(dealerLabel);
		remove(smallBlindLabel);
		remove(bigBlindLabel);

		dealerLabel.setBounds(dealerX, dealerY, dealerImg.getWidth(),
				dealerImg.getHeight());
		dealerLabel.repaint();
		add(dealerLabel);
		setPosition(dealerLabel, 0);

		smallBlindLabel.setBounds(smallBlindX, smallBlindY,
				smallBlindImg.getWidth(), smallBlindImg.getHeight());
		smallBlindLabel.repaint();
		add(smallBlindLabel);
		setPosition(smallBlindLabel, 0);

		bigBlindLabel.setBounds(bigBlindX, bigBlindY, bigBlindImg.getWidth(),
				bigBlindImg.getHeight());
		bigBlindLabel.repaint();
		add(bigBlindLabel);
		setPosition(bigBlindLabel, 0);
	}
}