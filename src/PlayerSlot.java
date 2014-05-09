import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class PlayerSlot {
	Player player;
	BufferedImage img;
	Card[] cards;
	int x;
	int y;
	JLabel bg;
	JLabel c1;
	JLabel c2;

	public PlayerSlot(int index, Player player) {
		try {
			img = ImageIO.read(new File("../res/orange_circle.png"));
		} catch (IOException e) {
			System.err.println("error: Couldn't open file \"../res/orange_circle.png\".");
		}

		switch (index) {
		case 0:
			x = 1271 - 351 - img.getWidth();
			y = 10;
			break;

		case 1:
			x = 1271 - 78 - img.getWidth();
			y = 98;
			break;

		case 2:
			x = 1271 - 78 - img.getWidth();
			y = 706 - 98 - img.getHeight();
			break;

		case 3:
			x = 1271 - 351 - img.getWidth();
			y = 706 - 10 - img.getHeight();
			break;

		case 4:
			x = 351;
			y = 706 - 10 - img.getHeight();
			break;

		case 5:
			x = 78;
			y = 706 - 98 - img.getHeight();
			break;

		case 6:
			x = 78;
			y = 98;
			break;

		case 7:
			x = 351;
			y = 10;
			break;

		default:
			throw new IllegalArgumentException("error: index has to be within the range [0, 7].");
		}

		if (player == null) {
			throw new IllegalArgumentException("error: player is null.");
		}

		this.player = player;
		cards = new Card[2];
	}

	public BufferedImage getImage() {
		return img;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void addCard(Card card) {
		if (cards[0] == null) {
			cards[0] = card;
		} else if (cards[1] == null) {
			cards[1] = card;
		} else {
			throw new IllegalStateException("PlayerSlot can only hold two cards!");
		}
	}

	public void draw(JLayeredPane gamePanel) {
		if (bg == null) {
			bg = new JLabel(new ImageIcon(img));
			bg.setBounds(x, y, img.getWidth(), img.getHeight());
			bg.repaint();
			gamePanel.add(bg);
			gamePanel.setPosition(bg, -1);
		}

		if (c1 == null && cards[0] != null) {
			c1 = new JLabel(new ImageIcon(cards[0].getImage()));
			c1.setBounds(x + 32, y + 60, 72, 96);
			c1.repaint();
			gamePanel.add(c1);
			gamePanel.setPosition(c1, 0);
		}

		if (c2 == null && cards[1] != null) {
			c2 = new JLabel(new ImageIcon(cards[1].getImage()));
			c2.setBounds(x + 112, y + 60, 72, 96);
			c2.repaint();
			gamePanel.add(c2);
			gamePanel.setPosition(c2, 0);
		}
	}
}
