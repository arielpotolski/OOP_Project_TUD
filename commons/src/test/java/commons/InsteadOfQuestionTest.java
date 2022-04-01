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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InsteadOfQuestionTest {
	private final Activity activityQuestion = new Activity(
		"00-test-question",
		"Title for the question",
		120,
		"pathToImageQuestion",
		"someSource"
	);
	private final Activity answer1Activity = new Activity(
		"00-test-answer-1",
		"Title for the answer 1",
		30,
		"pathToImageAnswer1",
		"someSource"
	);
	private final Activity answer1ActivityFaked = new Activity(
		"00-test-answer-1",
		"Title for the answer 1",
		30,
		"pathToImageAnswer1",
		"someSource"
	);
	private final Activity answer2Activity = new Activity(
		"00-test-answer-2",
		"Title for the answer 2",
		20,
		"pathToImageAnswer2",
		"someSource"
	);
	private final Activity answer2ActivityFaked = new Activity(
		"00-test-answer-2",
		"Title for the answer 2",
		10,
		"pathToImageAnswer2",
		"someSource"
	);
	private final Activity answer3Activity = new Activity(
		"00-test-answer-3",
		"Title for the answer 3",
		30,
		"pathToImageAnswer3",
		"someSource"
	);
	private final Activity answer3ActivityFaked = new Activity(
		"00-test-answer-3",
		"Title for the answer 3",
		4,
		"pathToImageAnswer3",
		"someSource"
	);
	private final Activity activity = new Activity(
		"00-test",
		"Title",
		67,
		"pathToImage",
		"Source"
	);

	private final InsteadOfQuestion insteadOfQuestionRandomized = new InsteadOfQuestion(
		this.activityQuestion,
		this.answer1Activity,
		this.answer2Activity,
		this.answer3Activity
	);
	private InsteadOfQuestion insteadOfQuestionFixed;
	private InsteadOfQuestion insteadOfQuestionFixed1;

	public InsteadOfQuestionTest() throws IOException {}

	@BeforeEach
	void setup() {
		this.insteadOfQuestionFixed1 = new InsteadOfQuestion(
			this.activityQuestion,
			this.answer1ActivityFaked,
			this.answer2ActivityFaked,
			this.answer3ActivityFaked,
			0.25,
			1.0 / 6.0,
			1.0 / 3.0
		);
		this.insteadOfQuestionFixed = new InsteadOfQuestion();
	}

	@Test
	void constructorTest() {
		assertNotNull(this.insteadOfQuestionRandomized);
		assertNotNull(this.insteadOfQuestionFixed);
	}

	@Test
	void getQuestionActivity() {
		assertEquals(this.activityQuestion, this.insteadOfQuestionFixed1.getQuestionActivity());
		assertEquals(this.activityQuestion, this.insteadOfQuestionRandomized.getQuestionActivity());
	}

	@Test
	void getAnswer1() {
		assertEquals(this.answer1ActivityFaked, this.insteadOfQuestionFixed1.getAnswer1());
	}

	@Test
	void getAnswer2() {
		assertEquals(this.answer2ActivityFaked, this.insteadOfQuestionFixed1.getAnswer2());
	}

	@Test
	void getAnswer3() {
		assertEquals(this.answer3ActivityFaked, this.insteadOfQuestionFixed1.getAnswer3());
	}

	@Test
	void getRealCoefficient1() {
		assertEquals(0.25, this.insteadOfQuestionFixed1.getRealCoefficient1());
	}

	@Test
	void getRealCoefficient2() {
		assertEquals(1.0 / 6.0, this.insteadOfQuestionFixed1.getRealCoefficient2());
	}

	@Test
	void getRealCoefficient3() {
		assertEquals(1.0 / 3.0, this.insteadOfQuestionFixed1.getRealCoefficient3());
	}

	@Test
	void getImagePathQuestion() {
		assertEquals("pathToImageQuestion", this.insteadOfQuestionFixed1.getImagePathQuestion());
	}

	@Test
	void getImagePathAnswer() {
		assertEquals("pathToImageAnswer1", this.insteadOfQuestionFixed1.getImagePathAnswer(1));
		assertEquals("pathToImageAnswer2", this.insteadOfQuestionFixed1.getImagePathAnswer(2));
		assertEquals("pathToImageAnswer3", this.insteadOfQuestionFixed1.getImagePathAnswer(3));
		assertEquals("pathToImageAnswer1", this.insteadOfQuestionRandomized.getImagePathAnswer(1));
		assertEquals("pathToImageAnswer2", this.insteadOfQuestionRandomized.getImagePathAnswer(2));
		assertEquals("pathToImageAnswer3", this.insteadOfQuestionRandomized.getImagePathAnswer(3));
	}

	@Test
	void setQuestionActivity() {
		this.insteadOfQuestionFixed.setQuestionActivity(this.activity);
		assertEquals(this.activity, this.insteadOfQuestionFixed.getQuestionActivity());
	}

	@Test
	void setAnswer1() {
		this.insteadOfQuestionFixed.setAnswer1(this.activity);
		assertEquals(this.activity, this.insteadOfQuestionFixed.getAnswer1());
	}

	@Test
	void setAnswer2() {
		this.insteadOfQuestionFixed.setAnswer2(this.activity);
		assertEquals(this.activity, this.insteadOfQuestionFixed.getAnswer2());
	}

	@Test
	void setAnswer3() {
		this.insteadOfQuestionFixed.setAnswer3(this.activity);
		assertEquals(this.activity, this.insteadOfQuestionFixed.getAnswer3());
	}

	@Test
	void setRealCoefficient1() {
		this.insteadOfQuestionFixed.setRealCoefficient1(0.1);
		assertEquals(0.1, this.insteadOfQuestionFixed.getRealCoefficient1());
	}

	@Test
	void setRealCoefficient2() {
		this.insteadOfQuestionFixed.setRealCoefficient2(0.1);
		assertEquals(0.1, this.insteadOfQuestionFixed.getRealCoefficient2());
	}

	@Test
	void setRealCoefficient3() {
		this.insteadOfQuestionFixed.setRealCoefficient3(0.1);
		assertEquals(0.1, this.insteadOfQuestionFixed.getRealCoefficient3());
	}

	@Test
	void equalsAndHash() {
		this.insteadOfQuestionFixed.setQuestionActivity(this.activityQuestion);
		this.insteadOfQuestionFixed.setAnswer1(this.answer1ActivityFaked);
		this.insteadOfQuestionFixed.setAnswer2(this.answer2ActivityFaked);
		this.insteadOfQuestionFixed.setAnswer3(this.answer3ActivityFaked);
		this.insteadOfQuestionFixed.setRealCoefficient1(0.25);
		this.insteadOfQuestionFixed.setRealCoefficient2(1.0 / 6.0);
		this.insteadOfQuestionFixed.setRealCoefficient3(1.0 / 3.0);
		assertEquals(this.insteadOfQuestionFixed, this.insteadOfQuestionFixed1);
		assertEquals(
			this.insteadOfQuestionFixed.hashCode(),
			this.insteadOfQuestionFixed1.hashCode()
		);
	}

	@Test
	void toStringTest() {
		String expected =
			"""
			InsteadOfQuestion{questionActivity=ID: 00-test-question
			Title: Title for the question
			Consumption in Wh: 120
			Image Path: pathToImageQuestion
			Source: someSource, answer1=ID: 00-test-answer-1
			Title: Title for the answer 1
			Consumption in Wh: 30
			Image Path: pathToImageAnswer1
			Source: someSource, realCoefficient1=0.25, answer2=ID: 00-test-answer-2
			Title: Title for the answer 2
			Consumption in Wh: 10
			Image Path: pathToImageAnswer2
			Source: someSource, realCoefficient2=0.16666666666666666, answer3=ID: 00-test-answer-3
			Title: Title for the answer 3
			Consumption in Wh: 4
			Image Path: pathToImageAnswer3
			Source: someSource, realCoefficient3=0.3333333333333333}""";
		assertEquals(expected, this.insteadOfQuestionFixed1.toString());
	}

	@Test
	void answerString() {
		String expected1 = "Title for the answer 1 4.00 times";
		String expected2 = "Title for the answer 2 12.00 times";
		String expected3 = "Title for the answer 3 30.00 times";
		assertEquals(expected1, this.insteadOfQuestionFixed1.answerString(1));
		assertEquals(expected2, this.insteadOfQuestionFixed1.answerString(2));
		assertEquals(expected3, this.insteadOfQuestionFixed1.answerString(3));
	}

	@Test
	void questionString() {
		String expected = "Instead of title for the question you could: ";
		assertEquals(expected, this.insteadOfQuestionFixed1.questionString());
	}

	@Test
	void pointsEarned() {
		assertEquals(500, this.insteadOfQuestionFixed1.pointsEarned(1000, 1, 0.5));
		assertEquals(0, this.insteadOfQuestionFixed1.pointsEarned(1000, 2, 0.5));
		assertEquals(0, this.insteadOfQuestionFixed1.pointsEarned(1000, 3, 0.5));
	}

	@Test
	void singleCorrectAnswer() throws IOException {
		int points = this.insteadOfQuestionRandomized.pointsEarned(1000, 1, 1.0)
			+ this.insteadOfQuestionRandomized.pointsEarned(1000, 2, 1.0)
			+ this.insteadOfQuestionRandomized.pointsEarned(1000, 3, 1.0);
		assertEquals(1000, points);

		this.insteadOfQuestionFixed.setQuestionActivity(this.activityQuestion);
		this.insteadOfQuestionFixed.setAnswer1(new Activity(
			"00-test-answer-1",
			"Title for the answer 1",
			30,
			"pathToImageAnswer1",
			"someSource"
		));
		this.insteadOfQuestionFixed.setAnswer2(new Activity(
			"00-test-answer-1",
			"Title for the answer 1",
			7,
			"pathToImageAnswer1",
			"someSource"
		));
		this.insteadOfQuestionFixed.setAnswer3(new Activity(
			"00-test-answer-1",
			"Title for the answer 1",
			21,
			"pathToImageAnswer1",
			"someSource"
		));
		this.insteadOfQuestionFixed.setRealCoefficient1(30.0 / 120.0);
		this.insteadOfQuestionFixed.setRealCoefficient2(6.0 / 120.0);
		this.insteadOfQuestionFixed.setRealCoefficient3(20.0 / 120.0);
		points = this.insteadOfQuestionFixed.pointsEarned(1000, 1, 1.0)
			+ this.insteadOfQuestionFixed.pointsEarned(1000, 2, 1.0)
			+ this.insteadOfQuestionFixed.pointsEarned(1000, 3, 1.0);
		assertEquals(1000, points);
	}

	@Test
	void imageInByteArrayQuestion() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				Activity
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);

		assertArrayEquals(
			bos.toByteArray(),
			this.insteadOfQuestionRandomized.imageInByteArrayQuestion()
		);
	}

	@Test
	void imageInByteArray() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				Activity
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);

		assertArrayEquals(bos.toByteArray(), this.insteadOfQuestionRandomized.imageInByteArray(1));
		assertArrayEquals(bos.toByteArray(), this.insteadOfQuestionRandomized.imageInByteArray(2));
		assertArrayEquals(bos.toByteArray(), this.insteadOfQuestionRandomized.imageInByteArray(3));
	}

	@Test
	void correctAnswer() {
		assertEquals(this.answer1ActivityFaked, this.insteadOfQuestionFixed1.correctAnswer());
	}

	@Test
	void returnEnergyConsumption() {
		assertEquals(
			30L,
			this.insteadOfQuestionFixed1.returnEnergyConsumption("Title for the answer 1")
		);
	}
}