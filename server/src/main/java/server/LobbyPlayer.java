package server;

public class LobbyPlayer {
	private final int id;
	private long timestamp;
	private boolean gameStarted;
	private int port;
	private int seed;

	public LobbyPlayer(int id) {
		this.id = id;
		this.timestamp = System.currentTimeMillis();
		this.gameStarted = false;
		this.port = -1;
		this.seed = -1;
	}

	public void updateTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}

	public void setGameStarted() {
		this.gameStarted = true;
	}

	public void setGamePort(int port) {
		this.port = port;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public int getId() {
		return this.id;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public boolean getGameStarted() {
		return this.gameStarted;
	}

	public int getPort() {
		return this.port;
	}

	public int getSeed() {
		return this.seed;
	}
}