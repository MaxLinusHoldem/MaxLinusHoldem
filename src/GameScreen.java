import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The screen that holds all graphical swing components, like the buttons and
 * the menu bar.
 * 
 * @author Max Wällstedt
 * @version 1.0 (2014.05.16)
 */
@SuppressWarnings("serial")
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
	private JMenuItem newItem;
	private JMenuItem quitItem;
	private JMenuItem aboutItem;

	/**
	 * Creates the GameScreen.
	 */
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

	/**
	 * Displays the action buttons that the user will use to determine their
	 * action.
	 * 
	 * @param facingBet
	 * 		True if the user is facing a bet otherwise false.
	 */
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

	/**
	 * Hides the action buttons.
	 */
	public void hideButtons() {
		buttonPanel.removeAll();
		buttonPanel.repaint();
	}

	/**
	 * Creates the menu bar and its contents.
	 */
	private void makeMenuBar() {
		menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");

		newItem = new JMenuItem("New Game");
		quitItem = new JMenuItem("Quit Game");
		aboutItem = new JMenuItem("About");

		fileMenu.add(newItem);
		fileMenu.add(quitItem);
		helpMenu.add(aboutItem);

		menubar.add(fileMenu);
		menubar.add(helpMenu);
	}

	/**
	 * Returns the GamePanel of the game.
	 * 
	 * @return The GamePanel of the game.
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	/**
	 * Returns the check button.
	 *
	 * @return The check button.
	 */
	public JButton getCheckButton() {
		return checkButton;
	}

	/**
	 * Returns the bet button.
	 *
	 * @return The bet button.
	 */
	public JButton getBetButton() {
		return betButton;
	}

	/**
	 * Returns the call button.
	 *
	 * @return The call button.
	 */
	public JButton getCallButton() {
		return callButton;
	}

	/**
	 * Returns the raise button.
	 *
	 * @return The raise button.
	 */
	public JButton getRaiseButton() {
		return raiseButton;
	}

	/**
	 * Returns the fold button.
	 *
	 * @return The fold button.
	 */
	public JButton getFoldButton() {
		return foldButton;
	}

	/**
	 * Returns the menu bar.
	 *
	 * @return The menu bar.
	 */
	public JMenuBar getJMenuBar() {
		return menubar;
	}

	/**
	 * Returns the all-in button.
	 *
	 * @return The all-in button.
	 */
	public JButton getAllInButton() {
		return allInButton;
	}

	/**
	 * Returns the new game item of the menu bar.
	 *
	 * @return The new game item of the menu bar.
	 */
	public JMenuItem getNewGameItem() {
		return newItem;
	}

	/**
	 * Returns the quit item of the menu bar.
	 *
	 * @return The quit item of the menu bar.
	 */
	public JMenuItem getQuitItem() {
		return quitItem;
	}

	/**
	 * Returns the about item of the menu bar.
	 *
	 * @return The about item of the menu bar.
	 */
	public JMenuItem getAboutItem() {
		return aboutItem;
	}
}