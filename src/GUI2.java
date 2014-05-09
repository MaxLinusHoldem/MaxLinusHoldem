import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.*;

public class GUI2 extends JFrame {
	private Container contentPane;
	private JLayeredPane gamePanel;
	private JPanel controlPanel;

	public static void main(String[] args) {
		new GUI2();
	}

	public GUI2() {
		super("MaxLinusHoldem");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		contentPane = getContentPane();
		makeGamePanel();
		makeControlPanel();

		gameScreen(new Game());
	}

	private void makeControlPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0, 10, 0));

		JButton button;

		button = new JButton("Check");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Check");
			}
		});
		panel.add(button);

		button = new JButton("Bet");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Bet");
			}
		});
		panel.add(button);

		button = new JButton("Call");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Call");
			}
		});
		panel.add(button);

		button = new JButton("Fold");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Fold");
			}
		});
		panel.add(button);

		controlPanel = panel;
	}

	private void makeGamePanel() {
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File("../res/table.png"));
		} catch (IOException e) {
			System.err.println("error: Couldn't find file \"../res/table.png\"");
			System.exit(1);
		}

		gamePanel = new JLayeredPane();
		gamePanel.setLayout(null);
		gamePanel.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));

		JLabel label = new JLabel(new ImageIcon(img));
		label.setBounds(0, 0, img.getWidth(), img.getHeight());
		label.repaint();

		gamePanel.add(label);
	}

	private void gameScreen(Game g) {
		makeGameMenuBar();

		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());

		contentPane.add(gamePanel, BorderLayout.CENTER);
		contentPane.add(controlPanel, BorderLayout.SOUTH);

		ArrayList<PlayerSlot> playerSlots = new ArrayList<PlayerSlot>();
		int index = 0;

		for (Player p : g.getPlayers()) {
			playerSlots.add(new PlayerSlot(index, p));
			index++;
		}

		for (PlayerSlot ps : playerSlots) {
			ps.addCard(new Card(2, "Spades"));
			ps.addCard(new Card(11, "Hearts"));
			ps.draw(gamePanel);
		}

		for (int i = 0; i < 5; i++) {
			CommunitySlot community = new CommunitySlot(i, new Card(3, "Diamonds"));
			community.addToPanel(gamePanel);
		}

		pack();
		setVisible(true);
	}

	private void makeGameMenuBar() {
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu menu;
		JMenuItem item;

		// Create the File menu
		menu = new JMenu("File");
		menubar.add(menu);

		item = new JMenuItem("Save Game");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, SHORTCUT_MASK));
		item.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			}
		);

		menu.add(item);

		item = new JMenuItem("Exit Game");
		item.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			}
		);
		menu.add(item);

		item = new JMenuItem("Quit");
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
		item.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			}
		);

		menu.add(item);

		// Create the Help menu
		menu = new JMenu("Help");
		menubar.add(menu);

		item = new JMenuItem("About");
		item.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			}
		);

		menu.add(item);
	}
}
