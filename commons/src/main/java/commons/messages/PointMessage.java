package commons.messages;

public class PointMessage extends Message{
	private final String name;
	private final int points;

	public PointMessage(String name, int points) {
		this.name = name;
		this.points = points;
	}

	@Override
	public MessageType getType() {
		return MessageType.POINTS;
	}

	public String getName() {
		return this.name;
	}

	public int getPoints() {
		return this.points;
	}
}