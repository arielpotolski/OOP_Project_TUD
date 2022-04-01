package commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MCQuestionTest {
	private final Activity activity = new Activity(
		"00-testing",
		"Activity X consumes",
		120,
		"somePath",
		"someSource"
	);
	private final Activity activity1 = new Activity(
		"00-testing1",
		"Activity X consumes ",
		121,
		"somePath1",
		"someSource1"
	);
	private MCQuestion mcQuestion1;
	private MCQuestion mcQuestion2;
	private final MCQuestion mcQuestion3 = new MCQuestion(this.activity, 1);
	private MCQuestion mcQuestion4;

	public MCQuestionTest() throws IOException {}

	@BeforeEach
	void setup() {
		this.mcQuestion1 = new MCQuestion();
		this.mcQuestion2 = new MCQuestion(this.activity, 1);
		this.mcQuestion4 = new MCQuestion(this.activity, 120, 123, 117);
	}

	@Test
	void checkConstructor() {
		assertNotNull(this.mcQuestion1);
		assertNotNull(this.mcQuestion2);
		assertNotNull(this.mcQuestion3);
		assertNotNull(this.mcQuestion4);
	}

	@Test
	void getActivity() {
		assertEquals(this.activity, this.mcQuestion2.getActivity());
		assertEquals(this.activity, this.mcQuestion3.getActivity());
		assertEquals(this.activity, this.mcQuestion4.getActivity());
		assertNull(this.mcQuestion1.getActivity());
	}

	@Test
	void getAnswer1() {
		assertEquals(120, this.mcQuestion4.getAnswer1());
	}

	@Test
	void getAnswer2() {
		assertEquals(123, this.mcQuestion4.getAnswer2());
	}

	@Test
	void getAnswer3() {
		assertEquals(117, this.mcQuestion4.getAnswer3());
	}

	@Test
	void getPicturePath() {
		assertEquals(this.activity.getImagePath(), this.mcQuestion4.getPicturePath());
	}

	@Test
	void setActivity() {
		this.mcQuestion1.setActivity(this.activity1);
		assertEquals(this.activity1, this.mcQuestion1.getActivity());
		this.mcQuestion4.setActivity(this.activity1);
		assertEquals(this.activity1, this.mcQuestion4.getActivity());
	}

	@Test
	void setAnswer1() {
		this.mcQuestion4.setAnswer1(2);
		assertEquals(2, this.mcQuestion4.getAnswer1());
	}

	@Test
	void setAnswer2() {
		this.mcQuestion4.setAnswer2(3);
		assertEquals(3, this.mcQuestion4.getAnswer2());
	}

	@Test
	void setAnswer3() {
		this.mcQuestion4.setAnswer3(4);
		assertEquals(4, this.mcQuestion4.getAnswer3());
	}

	@Test
	void equalsAndHash() {
		MCQuestion mcQuestion = new MCQuestion(this.activity, 120, 123, 117);
		assertEquals(this.mcQuestion4, mcQuestion);
		assertEquals(this.mcQuestion4.hashCode(), mcQuestion.hashCode());
	}

	@Test
	void printQuestion() {
		assertEquals(this.activity.getTitle() + " takes: ", this.mcQuestion3.printQuestion());
	}

	@Test
	void printAnswer1() {
		assertEquals(this.mcQuestion3.getAnswer1() + " Wh", this.mcQuestion3.printAnswer1());
	}

	@Test
	void printAnswer2() {
		assertEquals(this.mcQuestion3.getAnswer2() + " Wh", this.mcQuestion3.printAnswer2());
	}

	@Test
	void printAnswer3() {
		assertEquals(this.mcQuestion3.getAnswer3() + " Wh", this.mcQuestion3.printAnswer3());
	}

	@Test
	void generateAnswer() {
		assertNotEquals(this.mcQuestion4.getAnswer1(), this.mcQuestion4.getAnswer2());
		assertNotEquals(this.mcQuestion4.getAnswer2(), this.mcQuestion4.getAnswer3());
		assertNotEquals(this.mcQuestion4.getAnswer3(), this.mcQuestion4.getAnswer1());
	}

	@Test
	void getOrder() {
		assertTrue(this.mcQuestion3.getOrder().contains(1));
		assertTrue(this.mcQuestion3.getOrder().contains(2));
		assertTrue(this.mcQuestion3.getOrder().contains(3));
	}

	@Test
	void generateAnswers() {
		if (this.mcQuestion3.getOrder().indexOf(1) == 0) {
			assertEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer1());
			assertNotEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer2());
			assertNotEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer3());
		} else if (this.mcQuestion3.getOrder().indexOf(2) == 0) {
			assertNotEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer1());
			assertEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer2());
			assertNotEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer3());
		} else if (this.mcQuestion3.getOrder().indexOf(3) == 0) {
			assertNotEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer1());
			assertNotEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer2());
			assertEquals(this.activity.getConsumptionInWh(), this.mcQuestion3.getAnswer3());
		}
	}

	@Test
	void pointsEarned() {
		assertEquals(1000, this.mcQuestion4.pointsEarned(1000, 120, 1.0, false));
	}

	@Test
	void imageInByteArrayQuestion() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				MCQuestion
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);

		assertArrayEquals(bos.toByteArray(), this.mcQuestion2.imageInByteArrayQuestion());
	}
}