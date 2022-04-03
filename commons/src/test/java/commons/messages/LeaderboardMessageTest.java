package commons.messages;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeaderboardMessageTest {

	private LeaderboardMessage message;

	@BeforeEach
	void setUp() {
		HashMap<String, Integer> leaderboard = new HashMap<>();
		leaderboard.put("Dimitar", 150);
		leaderboard.put("Ganio", 560);
		this.message = new LeaderboardMessage(leaderboard);
	}

	@Test
	void getType() {
		assertEquals(MessageType.LEADERBOARD, this.message.getType());
	}

	@Test
	void getPlayers() {
		HashMap<String, Integer> map = this.message.getPlayers();
		assertTrue(map.containsKey("Dimitar"));
		assertTrue(map.containsKey("Ganio"));
		assertEquals(150, map.get("Dimitar"));
		assertEquals(560, map.get("Ganio"));
	}
}
