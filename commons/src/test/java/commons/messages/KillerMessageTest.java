package commons.messages;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KillerMessageTest {

	@Test
	void getType() {
		KillerMessage message = new KillerMessage(true);
		assertEquals(MessageType.KILLER, message.getType());
	}
}
