public class Player {
	private String name;
	private double cash;

	public Player(String name) {
		this.name = name;
		cash = 100.0;
	}

	public String getName() {
		return name;
	}

	public double getCash() {
		return cash;
	}
}
