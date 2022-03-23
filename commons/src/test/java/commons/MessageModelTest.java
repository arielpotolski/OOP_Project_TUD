package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageModelTest {

	MessageModel firstMessageModel;
	MessageModel secondMessageModel;
	MessageModel thirdMessageModel;

	@BeforeEach
	public void setUp() throws Exception {
		firstMessageModel = new MessageModel("Hello", "Dimitar");
		secondMessageModel = new MessageModel("Hello", "Alex");
		thirdMessageModel = new MessageModel("Hello", "Dimitar");
	}

	@Test
	void constructorTest() {
		assertNotNull(firstMessageModel);
	}

	@Test
	void getMessage() {
		assertEquals("Hello",firstMessageModel.getMessage());
	}

	@Test
	void setMessage() {
		firstMessageModel.setMessage("GG");
		assertEquals("GG",firstMessageModel.getMessage());
	}

	@Test
	void getNickname() {
		assertEquals("Dimitar", firstMessageModel.getNickname());
	}

	@Test
	void setNickname() {
		firstMessageModel.setNickname("Viet Luong");
		assertEquals("Viet Luong", firstMessageModel.getNickname());
	}

	@Test
	void testEquals() {
		assertEquals(firstMessageModel, thirdMessageModel);
	}

	@Test
	void testEqualsToItSelf() {
		assertEquals(firstMessageModel, firstMessageModel);
	}

	@Test
	void equalsTestFalse() {
		assertNotEquals(firstMessageModel, secondMessageModel);
	}

	@Test
	void testHashCode() {
		assertEquals(firstMessageModel.hashCode(),thirdMessageModel.hashCode());
	}
}