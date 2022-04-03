package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageModelTest {
	MessageModel firstMessageModel;
	MessageModel secondMessageModel;
	MessageModel thirdMessageModel;

	@BeforeEach
	public void setup() {
		this.firstMessageModel = new MessageModel("Hello", "Dimitar");
		this.secondMessageModel = new MessageModel("Hello", "Alex");
		this.thirdMessageModel = new MessageModel("Hello", "Dimitar");
	}

	@Test
	void constructorTest() {
		assertNotNull(this.firstMessageModel);
		assertNotNull(new MessageModel());
	}

	@Test
	void getMessage() {
		assertEquals("Hello", this.firstMessageModel.getMessage());
	}

	@Test
	void setMessage() {
		this.firstMessageModel.setMessage("GG");
		assertEquals("GG", this.firstMessageModel.getMessage());
	}

	@Test
	void getNickname() {
		assertEquals("Dimitar", this.firstMessageModel.getNickname());
	}

	@Test
	void setNickname() {
		this.firstMessageModel.setNickname("Viet Luong");
		assertEquals("Viet Luong", this.firstMessageModel.getNickname());
	}

	@Test
	void testEquals() {
		assertEquals(this.firstMessageModel, this.thirdMessageModel);
	}

	@Test
	void testEqualsToItSelf() {
		assertEquals(this.firstMessageModel, this.firstMessageModel);
	}

	@Test
	void equalsTestFalse() {
		assertNotEquals(this.firstMessageModel, this.secondMessageModel);
	}

	@Test
	void testHashCode() {
		assertEquals(this.firstMessageModel.hashCode(), this.thirdMessageModel.hashCode());
	}
}