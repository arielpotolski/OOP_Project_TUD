package commons.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinMessageTest {

	private JoinMessage message;

	@BeforeEach
	void setUp() {
		this.message = new JoinMessage("Dimitar");
	}

	@Test
	void getName() {
		assertEquals("Dimitar", this.message.getName());
	}

	@Test
	void getType() {
		assertEquals(MessageType.JOIN, this.message.getType());
	}
}
