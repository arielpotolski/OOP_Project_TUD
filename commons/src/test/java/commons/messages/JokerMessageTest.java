package commons.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JokerMessageTest {

	JokerMessage messageDecrease;
	JokerMessage messageDouble;
	JokerMessage messageEliminate;

	@BeforeEach
	void setUp() {
		this.messageDecrease = new JokerMessage(JokerType.DECREASE);
		this.messageDouble = new JokerMessage(JokerType.DOUBLE);
		this.messageEliminate = new JokerMessage(JokerType.ELIMINATE);
	}

	@Test
	void getType() {
		assertEquals(MessageType.JOKER, this.messageDecrease.getType());
		assertEquals(MessageType.JOKER, this.messageDouble.getType());
		assertEquals(MessageType.JOKER, this.messageEliminate.getType());
	}

	@Test
	void getJokerType() {
		assertEquals(JokerType.DECREASE, this.messageDecrease.getJokerType());
		assertEquals(JokerType.DOUBLE, this.messageDouble.getJokerType());
		assertEquals(JokerType.ELIMINATE, this.messageEliminate.getJokerType());
	}
}
