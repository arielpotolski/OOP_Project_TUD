package commons.messages;

public class JoinMessage extends Message {
	@Override
	public MessageType getType() {
		return MessageType.JOIN;
	}

	private final String name;

	public JoinMessage(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
