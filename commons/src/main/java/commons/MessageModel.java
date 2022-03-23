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
	 * The constructor of the MessageModel
	 * @param message the message when the client send
	 * @param nickname the nickname of the player
	 */
	public MessageModel(String message, String nickname) {
		this.message = message;
		this.nickname = nickname;
	}

	public MessageModel() {}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.message,this.nickname);
	}
}
