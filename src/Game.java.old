import java.util.ArrayList;

/**
 * The class Game.
 * This class is the main game class of the game.
 * 
 * @author Linus Wåreus
 * @version 2014.05.07
 */
public class Game {
	private Dealer dealer;
	private ArrayList<Player> players;
	private ArrayList<Player> activePlayers;
	public final int STARTMONEY = 100;
	
	/**
	 * The default constructor of the class Game.
	 */
	public Game() {
		players = new ArrayList<Player>();
	}
	
	public void play() {
		players.add(new User(STARTMONEY));
		players.add(new AI(STARTMONEY));
		boolean gameFinished = false;
		while (!gameFinished) {
			dealer = new Dealer(players);
			activePlayers = new ArrayList<Player>(players);
			
			dealer.dealCards();
			actPlayer();
			dealer.dealTheFlop();
			actPlayer();
			dealer.dealTheTurn();
			actPlayer();
			dealer.dealTheRiver();
			actPlayer();
			dealer.selectWinner(activePlayers);
		}
	}
	
	private void actPlayer() {
		for (Player p : players) {
			p.resetBet();
		}
		
		for (Player p : activePlayers) {
			if (!p.act(dealer.getCurrentBet())) {
				activePlayers.remove(p);
			} else {
				dealer.setCurrentBet(p.getBet());
			}
		}
		
		for (Player p : activePlayers) {
			dealer.addPot(p.getBet());
		}
	}
}