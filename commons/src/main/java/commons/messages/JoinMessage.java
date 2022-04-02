package commons.messages;

/**
 * Sent by the client after they create the initial TCP connection so that the server has a name
 * to identify the connection with.
 */
public class JoinMessage extends Message {
	private final String name;

	public JoinMessage(String name) {
		this.name = name;
	}

	@Override
	public MessageType getType() {
		return MessageType.JOIN;
	}

	public String getName() {
		return this.name;
	}
}