package commons.messages;

public class KillerMessage extends Message {
	private boolean sendBack;

	public KillerMessage(boolean sendBack) {
		this.sendBack = sendBack;
	}

	@Override
	public MessageType getType() {
		return MessageType.KILLER;
	}

	public boolean shouldSendBack() {
		return this.sendBack;
	}
}