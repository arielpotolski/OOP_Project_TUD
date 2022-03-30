package client.scenes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionClassTest {
	@Test
	public void IsNumericNull() {
		assertFalse(QuestionClass.isNumeric(null));
	}

	@Test
	public void IsNumericHasChar() {
		assertFalse(QuestionClass.isNumeric("12Ad"));
	}

	@Test
	public void IsNumericTrue() {
		assertTrue(QuestionClass.isNumeric("1241"));
	}
}