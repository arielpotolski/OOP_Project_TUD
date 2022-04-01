package commons;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;

public class MessageModel {
	@JsonProperty("message")
	private String message;

	@JsonProperty("nickname")
	private String nickname;


	/**
	 * The constructor of the MessageModel.
	 * @param message The message when the client send.
	 * @param nickname The nickname of the player.
	 */
	public MessageModel(String message, String nickname) {
		this.message = message;
		this.nickname = nickname;
	}

	public MessageModel() {}

	/**
	 * Getter for the message that client sends to other clients.
	 * @return The message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Setter for the message that client sends to other clients.
	 * @param message The message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter for the nickname of the client.
	 * @return The nickname of the client.
	 */
	public String getNickname() {
		return this.nickname;
	}

	/**
	 * Setter for the nickname of the client.
	 * @param nickname The nickname of the client.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof MessageModel) && EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.message, this.nickname);
	}
}