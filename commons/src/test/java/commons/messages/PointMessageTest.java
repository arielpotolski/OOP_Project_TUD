package commons.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointMessageTest {

	PointMessage message;

	@BeforeEach
	void setUp() {
		this.message = new PointMessage(1200);
	}

	@Test
	void getType() {
		assertEquals(MessageType.POINTS, this.message.getType());
	}

	@Test
	void getPoints() {
		assertEquals(1200, this.message.getPoints());
	}
}
