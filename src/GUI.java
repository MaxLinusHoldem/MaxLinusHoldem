import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The grafic user interface for the game MaxLinusHold'em.
 * 
 * @author Linus Wåreus
 * @version 2014.05.05
 */
public class GUI {
	private Game game;
	private JFrame frame;
	private JPanel contentPane;
	private ImagePanel mainPanel;
	private JButton newGameButton;
	private JButton exitButton;
	private JButton ceckButton;
	private JButton callButton;
	private JButton betButton;
	private JButton foldButton;
	
	/**
	 * 
	 */
	public GUI() {
		makeFrame();
	}

	/**
     * Create the Swing frame and its content.
     */
	private void makeFrame() {
		frame = new JFrame("MaxLinusHold'em");
		
		makeMenuBar();
		makeStartScreen();
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * 
	 */
	private void makeStartScreen() {
		contentPane = (JPanel)frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(200, 200, 200, 200));
		
		JPanel startScreen = new JPanel();
		startScreen.setLayout(new GridLayout(1, 0));
		
		contentPane.add(new JLabel(welcomeMessage()), BorderLayout.CENTER);
		
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { newGame(); }
        });
        startScreen.add(newGameButton);
        
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { System.exit(0); }
        });
        startScreen.add(exitButton);
        
        JPanel flow = new JPanel();
        flow.add(startScreen);
        
        contentPane.add(flow, BorderLayout.SOUTH);
        
		frame.pack();
		frame.setVisible(true);
	}
	
    /**
     * Create the main frame's menu bar.
     * 
     * @param frame The frame that the menu bar should be added to.
     */
    private void makeMenuBar() {
    	 final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    	 JMenuBar menubar = new JMenuBar();
    	 frame.setJMenuBar(menubar);
    	        
    	 JMenu menu;
    	 JMenuItem item;
    	 
    	 // Create the File menu.
         menu = new JMenu("File");
         menubar.add(menu);
         
         item = new JMenuItem("Quit");
         item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
         item.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) { System.exit(0); }
         });
         menu.add(item);
    }
    
    /**
     * Create the Swing frame and its content.
     */
	public void makeGamePlan() {
		contentPane.removeAll();

		BufferedImage table = null;
		try {
		    table = ImageIO.read(new File("../res/table.png"));
		} catch (IOException e) {
			System.err.println(e);
		}
		
		mainPanel = new ImagePanel(table);
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.paintComponent(table.getGraphics());
		
		JPanel actions = new JPanel();
		actions.setLayout(new GridLayout(1, 0));
		
        ceckButton = new JButton("Ceck");
        ceckButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {  }
        });
        actions.add(ceckButton);
        
        callButton = new JButton("Call");
        callButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {  }
        });
        actions.add(callButton);
        
        betButton = new JButton("Bet");
        betButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {  }
        });
        actions.add(betButton);
        
        foldButton = new JButton("Fold");
        foldButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {  }
        });
        actions.add(foldButton);
		
        JPanel flow = new JPanel();
        flow.add(actions);
        
        contentPane.add(flow, BorderLayout.SOUTH);
        
		frame.pack();
		frame.setVisible(true);
	}
    
    /**
     * 
     */
    private String welcomeMessage() {
    	return "Welcome to MaxLinus Hold 'em!";
    }
    
    /**
     * 
     */
	private void newGame() {
		makeGamePlan();
	}
	
	@SuppressWarnings("serial")
	private class ImagePanel extends JComponent {
		private Image image;
		
	   ImagePanel(Image image) {
	    	this.image = image;
	    	setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
	    }
	
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image != null) {
				g.drawImage(image, 0, 0, null);
			}
		}
	}
}