import java.awt.image.*;
import javax.swing.*;

/**
 * A slot that holds the community card to be printed on a GamePanel.
 * 
 * @author Max Wällstedt
 * @version 1.0 (2014.05.16)
 */
@SuppressWarnings("serial")
public class CommunitySlot extends JLabel {
	int x;
	int y;
	Card card;
	GamePanel gamePanel;

	/**
	 * Creates a slot that can hold a community card.
	 * 
	 * @param card
	 *            The card to be held by this community slot.
	 * @param index
	 *            The index [0, 4] of the community slot.
	 * @param gamePanel
	 *            The GamePanel where this community slot will be drawn.
	 */
	public CommunitySlot(Card card, int index, GamePanel gamePanel) {
		if (index < 0 || index > 4) {
			throw new IllegalArgumentException(
					"error: Community card index must be within the range of [0, 4] (inclusive).");
		} else if (card == null) {
			throw new IllegalArgumentException(
					"error: Must specify card to add as community card.");
		} else if (gamePanel == null) {
			throw new IllegalArgumentException(
					"error: Must specify GamePanel to draw card on.");
		}

		this.card = card;
		this.gamePanel = gamePanel;

		y = 284;

		if (index == 0) {
			x = 454;
		} else if (index == 1) {
			x = 529;
		} else if (index == 2) {
			x = 603;
		} else if (index == 3) {
			x = 677;
		} else {
			x = 752;
		}

		BufferedImage cardImg = card.getImage();
		setIcon(new ImageIcon(cardImg));
		setBounds(x, y, cardImg.getWidth(), cardImg.getHeight());
		repaint();
		gamePanel.add(this);
		gamePanel.setPosition(this, 0);
	}

	/**
	 * Returns the community card held in this community slot.
	 * 
	 * @return The community card held in this community slot.
	 */
	public Card getCard() {
		return this.card;
	}
}