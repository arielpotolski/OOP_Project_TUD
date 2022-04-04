package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerTest {

	private Player player;

	@BeforeEach
	void setUp() {
		this.player = new Player("Dimitar", 989);
	}

	@Test
	void constructorTest() {
		Player examplePlayer = new Player("Don");
		assertNotNull(examplePlayer);
		assertNotNull(this.player);
		assertNotNull(new Player());
	}

	@Test
	void getPoint() {
		assertEquals(989, this.player.getPoints());
	}

	@Test
	void setPoint() {
		this.player.setPoints(976);
		assertEquals(976, this.player.getPoints());
	}

	@Test
	void getNickname() {
		assertEquals("Dimitar", this.player.getNickname());
	}

	@Test
	void setNickname() {
		this.player.setNickname("Doko");
		assertEquals("Doko", this.player.getNickname());
	}

	@Test
	void equalsAndHash() {
		Player equalPl = new Player("Dimitar", 989);
		assertEquals(equalPl, this.player);
		assertEquals(equalPl.hashCode(), this.player.hashCode());
	}

	@Test
	void toStringTest() {
		String result = "Player{id=0, nickname='Dimitar', points=989}";
		assertEquals(result, this.player.toString());
	}
}
