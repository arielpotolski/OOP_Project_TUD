package commons.messages;

/**
 * Sent by either the client or server when something unexpected happens.
 */
public class ErrorMessage extends Message {
	@Override
	public MessageType getType() {
		return MessageType.ERROR;
	}

	private final String error;

	// Could add error code or other information here in the future.

	public ErrorMessage(String error) {
		this.error = error;
	}

	public String getError() {
		return this.error;
	}
}