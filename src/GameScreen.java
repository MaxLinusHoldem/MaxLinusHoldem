import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class GameScreen extends JPanel {
	private JMenuBar menubar;
	private GamePanel gamePanel;
	private JPanel buttonPanel;
	private JButton checkButton;
	private JButton betButton;
	private JButton callButton;
	private JButton raiseButton;
	private JButton foldButton;
	private JButton allInButton;

	public GameScreen() {
		setLayout(new BorderLayout());
		gamePanel = new GamePanel();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 0, 100, 0));
		buttonPanel.setBorder(new EmptyBorder(10, 100, 10, 100));

		checkButton = new JButton("Check");
		betButton = new JButton("Bet");
		callButton = new JButton("Call");
		raiseButton = new JButton("Raise");
		foldButton = new JButton("Fold");
		allInButton = new JButton("All-in");

		add(gamePanel, BorderLayout.CENTER);

		makeMenuBar();
	}

	public void showButtons(boolean facingBet) {
		buttonPanel.removeAll();

		if (facingBet) {
			buttonPanel.add(callButton);
			buttonPanel.add(raiseButton);
			buttonPanel.add(allInButton);
			buttonPanel.add(foldButton);
		} else {
			buttonPanel.add(checkButton);
			buttonPanel.add(betButton);
			buttonPanel.add(allInButton);
		}

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void hideButtons() {
		buttonPanel.removeAll();
		buttonPanel.repaint();
	}

	private void makeMenuBar() {
		menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");

		JMenuItem newItem = new JMenuItem("New Game");
		JMenuItem exitItem = new JMenuItem("Exit Game");
		JMenuItem quitItem = new JMenuItem("Quit Game");
		JMenuItem aboutItem = new JMenuItem("About");

		fileMenu.add(newItem);
		fileMenu.add(exitItem);
		fileMenu.add(quitItem);
		helpMenu.add(aboutItem);

		menubar.add(fileMenu);
		menubar.add(helpMenu);
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public JButton getCheckButton() {
		return checkButton;
	}

	public JButton getBetButton() {
		return betButton;
	}

	public JButton getCallButton() {
		return callButton;
	}

	public JButton getRaiseButton() {
		return raiseButton;
	}

	public JButton getFoldButton() {
		return foldButton;
	}

	public JMenuBar getJMenuBar() {
		return menubar;
	}
	
	public JButton getAllInButton() {
		return allInButton;
	}
}
