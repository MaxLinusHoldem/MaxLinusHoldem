import java.util.ArrayList;

public class Game {
	private ArrayList<Player> players;

	public Game() {
		players = new ArrayList<Player>();

		players.add(new Player("You"));

		for (int i = 0; i < 7; i++) {
			players.add(new Player("NPC" + i));
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
