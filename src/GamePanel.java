import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JLayeredPane {
	private BufferedImage tableImg;
	private BufferedImage dealerImg;
	private BufferedImage smallBlindImg;
	private BufferedImage bigBlindImg;
	private JLabel dealerLabel;
	private JLabel smallBlindLabel;
	private JLabel bigBlindLabel;

	public GamePanel() {
		setLayout(null);

		String tableImgPath = "../res/table.png";

		try {
			tableImg = ImageIO.read(new File(tableImgPath));
		} catch (IOException e) {
			System.out.println("error: File \"" + tableImgPath + "\" not found.");
			System.exit(1);
		}

		String dealerImgPath = "../res/buttons/dealer.png";

		try {
			dealerImg = ImageIO.read(new File(dealerImgPath));
		} catch (IOException e) {
			System.out.println("error: File \"" + dealerImgPath + "\" not found.");
			System.exit(1);
		}

		dealerLabel = new JLabel(new ImageIcon(dealerImgPath));

		String smallBlindImgPath = "../res/buttons/small_blind.png";

		try {
			smallBlindImg = ImageIO.read(new File(smallBlindImgPath));
		} catch (IOException e) {
			System.out.println("error: File \"" + smallBlindImgPath + "\" not found.");
			System.exit(1);
		}

		smallBlindLabel = new JLabel(new ImageIcon(smallBlindImg));

		String bigBlindImgPath = "../res/buttons/big_blind.png";

		try {
			bigBlindImg = ImageIO.read(new File(bigBlindImgPath));
		} catch (IOException e) {
			System.out.println("error: File \"" + bigBlindImgPath + "\" not found.");
			System.exit(1);
		}

		bigBlindLabel = new JLabel(new ImageIcon(bigBlindImg));

		setPreferredSize(new Dimension(tableImg.getWidth(), tableImg.getHeight()));

		JLabel tableLabel = new JLabel(new ImageIcon(tableImg));
		tableLabel.setBounds(0, 0, tableImg.getWidth(), tableImg.getHeight());
		tableLabel.repaint();
		add(tableLabel);
	}

	public void setDealerAndBlinds(int dealerID) {
		int horizontalDistance = 294;
		int horizontalDistanceLong = 424;

		int verticalDistance = 214;
		int verticalDistanceLong = 244;

		dealerID = dealerID % 8;

		int dealerX = 0, dealerY = 0;
		int smallBlindX = 0, smallBlindY = 0;
		int bigBlindX = 0, bigBlindY = 0;

		if (dealerID == 0) {
			dealerX = 1271 - horizontalDistanceLong - dealerImg.getWidth();
			dealerY = verticalDistance;

			smallBlindX = 1271 - horizontalDistance - smallBlindImg.getWidth();
			smallBlindY = verticalDistanceLong;

			bigBlindX = smallBlindX;
			bigBlindY = 706 - verticalDistanceLong - bigBlindImg.getHeight();
		} else if (dealerID == 1) {
			dealerX = 1271 - horizontalDistance - dealerImg.getWidth();
			dealerY = verticalDistance;

			smallBlindX = 1271 - horizontalDistance - smallBlindImg.getWidth();
			smallBlindY = 706 - verticalDistanceLong - smallBlindImg.getHeight();

			bigBlindX = 1271 - horizontalDistanceLong - bigBlindImg.getWidth();
			bigBlindY = 706 - verticalDistance - bigBlindImg.getHeight();
		} else if (dealerID == 2) {
			dealerX = 1271 - horizontalDistance - dealerImg.getWidth();
			dealerY = 706 - verticalDistanceLong - dealerImg.getHeight();

			smallBlindX = 1271 - horizontalDistanceLong - smallBlindImg.getWidth();
			smallBlindY = 706 - verticalDistance - smallBlindImg.getHeight();

			bigBlindX = horizontalDistanceLong;
			bigBlindY = 706 - verticalDistance - bigBlindImg.getHeight();
		} else if (dealerID == 3) {
			dealerX = 1271 - horizontalDistanceLong - dealerImg.getWidth();
			dealerY = 706 - verticalDistance - dealerImg.getHeight();

			smallBlindX = horizontalDistanceLong;
			smallBlindY = 706 - verticalDistance - smallBlindImg.getHeight();

			bigBlindX = horizontalDistance;
			bigBlindY = 706 - verticalDistanceLong - bigBlindImg.getHeight();
		} else if (dealerID == 4) {
			dealerX = horizontalDistanceLong;
			dealerY = 706 - verticalDistance - dealerImg.getHeight();

			smallBlindX = horizontalDistance;
			smallBlindY = 706 - verticalDistanceLong - smallBlindImg.getHeight();

			bigBlindX = horizontalDistance;
			bigBlindY = verticalDistanceLong;
		} else if (dealerID == 5) {
			dealerX = horizontalDistance;
			dealerY = 706 - verticalDistanceLong - dealerImg.getHeight();

			smallBlindX = horizontalDistance;
			smallBlindY = verticalDistanceLong;

			bigBlindX = horizontalDistanceLong;
			bigBlindY = verticalDistance;
		} else if (dealerID == 6) {
			dealerX = horizontalDistance;
			dealerY = verticalDistanceLong;

			smallBlindX = horizontalDistanceLong;
			smallBlindY = verticalDistance;

			bigBlindX = 1271 - horizontalDistanceLong - bigBlindImg.getWidth();
			bigBlindY = verticalDistance;
		} else {
			dealerX = horizontalDistanceLong;
			dealerY = verticalDistance;

			smallBlindX = 1271 - horizontalDistanceLong - smallBlindImg.getWidth();
			smallBlindY = verticalDistance;

			bigBlindX = 1271 - horizontalDistance - bigBlindImg.getWidth();
			bigBlindY = verticalDistanceLong;
		}

		remove(dealerLabel);
		remove(smallBlindLabel);
		remove(bigBlindLabel);

		dealerLabel.setBounds(dealerX, dealerY, dealerImg.getWidth(), dealerImg.getHeight());
		dealerLabel.repaint();
		add(dealerLabel);
		setPosition(dealerLabel, 0);

		smallBlindLabel.setBounds(smallBlindX, smallBlindY, smallBlindImg.getWidth(), smallBlindImg.getHeight());
		smallBlindLabel.repaint();
		add(smallBlindLabel);
		setPosition(smallBlindLabel, 0);

		bigBlindLabel.setBounds(bigBlindX, bigBlindY, bigBlindImg.getWidth(), bigBlindImg.getHeight());
		bigBlindLabel.repaint();
		add(bigBlindLabel);
		setPosition(bigBlindLabel, 0);
	}
}
