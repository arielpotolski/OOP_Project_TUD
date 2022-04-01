package commons.messages;

/**
 * Sent by either the client or server when something unexpected happens.
 */
public class ErrorMessage extends Message {
	private final String error;

	public ErrorMessage(String error) {
		this.error = error;
	}

	@Override
	public MessageType getType() {
		return MessageType.ERROR;
	}

	public String getError() {
		return this.error;
	}
}