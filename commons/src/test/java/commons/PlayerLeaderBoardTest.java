package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlayerLeaderBoardTest {

	PlayerLeaderboard playerLeaderboard;

	@BeforeEach
	void setUp() {
		this.playerLeaderboard = new PlayerLeaderboard(1, "Dimitar", 74);
	}

	@Test
	void constructorTest() {
		assertNotNull(new PlayerLeaderboard());
		assertNotNull(this.playerLeaderboard);
	}

	@Test
	void getName() {
		assertEquals("Dimitar", this.playerLeaderboard.getName());
	}

	@Test
	void getPosition() {
		assertEquals(1, this.playerLeaderboard.getPosition());
	}

	@Test
	void getPoints() {
		assertEquals(74, this.playerLeaderboard.getPoints());
	}
}
