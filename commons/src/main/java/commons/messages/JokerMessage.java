package commons.messages;

public class JokerMessage extends Message {
	private final JokerType jokerType;

	public JokerMessage(JokerType jokerType) {
		this.jokerType = jokerType;
	}

	@Override
	public MessageType getType() {
		return MessageType.JOKER;
	}

	public JokerType getJokerType() {
		return this.jokerType;
	}
}