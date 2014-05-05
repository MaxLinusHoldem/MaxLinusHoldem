import java.util.ArrayList;

/**
 * The class Game.
 * This class is the main game class of the game.
 * 
 * @author Linus Wåreus
 * @version 2014.05.05
 */
public class Game {
	private Dealer dealer;
	private ArrayList<Player> players;
	private ArrayList<Player> activePlayers;
	
	/**
	 * The default constructor of the class Game.
	 */
	public Game() {
		players = new ArrayList<Player>();
	}
	
	public void play() {
		players.add(new User());
		players.add(new AI());
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
			dealer.selectWinner(activePlayers);
		}
	}
	
	private void actPlayer() {
		for (Player p : activePlayers) {
			if(!p.act()) {
				activePlayers.remove(p);
			}
		}
	}
}