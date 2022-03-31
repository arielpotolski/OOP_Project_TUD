package client.scenes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionClassTest {
	@Test
	public void isNumericNull() {
		assertFalse(QuestionClass.isNumeric(null));
	}

	@Test
	public void isNumericHasChar() {
		assertFalse(QuestionClass.isNumeric("12Ad"));
	}

	@Test
	public void isNumericTrue() {
		assertTrue(QuestionClass.isNumeric("1241"));
	}
}