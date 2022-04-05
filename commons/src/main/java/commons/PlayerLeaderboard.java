package commons;

public class PlayerLeaderboard {
	private String name;
	private int position;
	private int points;

	public PlayerLeaderboard() {}

	public PlayerLeaderboard(int position, String name, int points) {
		this.position = position;
		this.name = name;
		this.points = points;
	}

	/**
	 *  Getter method for name
	 *
	 * @return the name of the player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 *  Getter method for position
	 *
	 * @return the positon of the player in the global leaderboard
	 */
	public int getPosition() {
		return this.position;
	}

	/**
	 *  Getter method for points
	 *
	 * @return the points of the player
	 */
	public int getPoints() {
		return this.points;
	}
}
