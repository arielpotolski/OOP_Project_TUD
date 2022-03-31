package commons.messages;

public class JokerMessage extends Message {
	@Override
	public MessageType getType() {
		return MessageType.JOKER;
	}

	private final JokerType jokerType;

	public JokerMessage(JokerType jokerType) {
		this.jokerType = jokerType;
	}

	public JokerType getJokerType() {
		return this.jokerType;
	}
}
