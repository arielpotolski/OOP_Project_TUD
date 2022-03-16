package commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

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
	private Player() {} // For object mapper

	public int getPoint() {
		return this.points;
	}

	public void setPoint(int point) {
		this.points = point;
	}

	public Player(String nickname, int Point) {
		this.nickname = nickname;
		this.points = Point;
	}

	public String getNickName() {
		return this.nickname;
	}

	public void setNickName(String nickname) {
		this.nickname = nickname;
	}

	public Player(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
	}
}