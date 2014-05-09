import java.awt.image.*;
import javax.swing.*;

public class CommunitySlot extends JLabel {
	int x;
	int y;
	Card card;

	public CommunitySlot(int index, Card card) {
		if (card == null) {
			throw new IllegalArgumentException("error: Adding null Card to community cards.");
		}

		this.card = card;

		y = 284;

		switch (index) {
		case 0:
			x = 454;
			break;

		case 1:
			x = 529;
			break;

		case 2:
			x = 603;
			break;

		case 3:
			x = 677;
			break;

		case 4:
			x = 752;
			break;

		default:
			throw new IllegalArgumentException("error: Game can only hold five community cards.");
		}
	}

	public void addToPanel(JLayeredPane panel) {
		BufferedImage img = card.getImage();
		setIcon(new ImageIcon(img));
		setBounds(x, y, img.getWidth(), img.getHeight());
		repaint();
		panel.add(this);
		panel.setPosition(this, 0);
	}
}
