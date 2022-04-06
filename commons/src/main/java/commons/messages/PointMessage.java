package commons.messages;

public class PointMessage extends Message{
	private final int points;

	public PointMessage(int points) {
		this.points = points;
	}

	@Override
	public MessageType getType() {
		return MessageType.POINTS;
	}

	public int getPoints() {
		return this.points;
	}
}