package commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;

	@JsonProperty("nickname")
	private String nickname;

	@JsonProperty("points")
	private int points;

	@SuppressWarnings("unused")
	public Player() {} // For object mapper

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Player(String nickname, int points) {
		this.nickname = nickname;
		this.points = points;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Player(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Player) && EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return "Player{" +
				"id=" + this.id +
				", nickname='" + this.nickname + '\'' +
				", points=" + this.points +
				'}';
	}
}