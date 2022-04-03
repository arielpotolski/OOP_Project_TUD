package commons.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorMessageTest {

	private ErrorMessage message;

	@BeforeEach
	void setUp() {
		this.message = new ErrorMessage("This is an error");
	}

	@Test
	void getError() {
		assertEquals("This is an error", this.message.getError());
	}

	@Test
	void getType() {
		assertEquals(MessageType.ERROR, this.message.getType());
	}
}
