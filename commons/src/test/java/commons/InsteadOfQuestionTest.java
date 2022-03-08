package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InsteadOfQuestionTest {

	private final Activity activityQuestion = new Activity("00-test-question",
			"Title for the question", 120, "pathToImageQuestion", "someSource");
	private final Activity answer1Activity = new Activity("00-test-answer-1",
			"Title for the answer 1", 30, "pathToImageAnswer1", "someSource");
	private final Activity answer1ActivityFaked = new Activity("00-test-answer-1",
			"Title for the answer 1", 30, "pathToImageAnswer1", "someSource");
	private final Activity answer2Activity = new Activity("00-test-answer-2",
			"Title for the answer 2", 20, "pathToImageAnswer2", "someSource");
	private final Activity answer2ActivityFaked = new Activity("00-test-answer-2",
			"Title for the answer 2", 10, "pathToImageAnswer2", "someSource");
	private final Activity answer3Activity = new Activity("00-test-answer-3",
			"Title for the answer 3", 40, "pathToImageAnswer3", "someSource");
	private final Activity answer3ActivityFaked = new Activity("00-test-answer-3",
			"Title for the answer 3", 4, "pathToImageAnswer3", "someSource");
	private final Activity activity = new Activity("00-test",
			"Title", 67, "pathToImage", "Source");

	private final InsteadOfQuestion insteadOfQuestionRandomized =
			new InsteadOfQuestion(activityQuestion, answer1Activity,
			answer2Activity, answer3Activity);
	private InsteadOfQuestion insteadOfQuestionFixed;
	private InsteadOfQuestion insteadOfQuestionFixed1;

	@BeforeEach
	void setUp() {
		insteadOfQuestionFixed1 = new InsteadOfQuestion(activityQuestion,
				answer1ActivityFaked, answer2ActivityFaked,
				answer3ActivityFaked, 0.25, 1.0 / 6.0, 1.0 / 3.0);
		insteadOfQuestionFixed = new InsteadOfQuestion();
	}

	@Test
	void constructorTest() {
		assertNotNull(insteadOfQuestionRandomized);
		assertNotNull(insteadOfQuestionFixed);
	}

	@Test
	void getQuestionActivity() {
		assertEquals(activityQuestion, insteadOfQuestionFixed1.getQuestionActivity());
		assertEquals(activityQuestion, insteadOfQuestionRandomized.getQuestionActivity());
	}

	@Test
	void getAnswer1() {
		assertEquals(answer1ActivityFaked, insteadOfQuestionFixed1.getAnswer1());
	}

	@Test
	void getAnswer2() {
		assertEquals(answer2ActivityFaked, insteadOfQuestionFixed1.getAnswer2());
	}

	@Test
	void getAnswer3() {
		assertEquals(answer3ActivityFaked, insteadOfQuestionFixed1.getAnswer3());
	}

	@Test
	void getRealCoefficient1() {
		assertEquals(0.25, insteadOfQuestionFixed1.getRealCoefficient1());
	}

	@Test
	void getRealCoefficient2() {
		assertEquals(1.0 / 6.0, insteadOfQuestionFixed1.getRealCoefficient2());
	}

	@Test
	void getRealCoefficient3() {
		assertEquals(1.0 / 3.0, insteadOfQuestionFixed1.getRealCoefficient3());
	}

	@Test
	void getImagePathQuestion() {
		assertEquals("pathToImageQuestion", insteadOfQuestionFixed1.getImagePathQuestion());
	}

	@Test
	void getImagePathAnswer() {
		assertEquals("pathToImageAnswer1", insteadOfQuestionFixed1.getImagePathAnswer(1));
		assertEquals("pathToImageAnswer2", insteadOfQuestionFixed1.getImagePathAnswer(2));
		assertEquals("pathToImageAnswer3", insteadOfQuestionFixed1.getImagePathAnswer(3));
		assertEquals("pathToImageAnswer1", insteadOfQuestionRandomized.getImagePathAnswer(1));
		assertEquals("pathToImageAnswer2", insteadOfQuestionRandomized.getImagePathAnswer(2));
		assertEquals("pathToImageAnswer3", insteadOfQuestionRandomized.getImagePathAnswer(3));
	}

	@Test
	void setQuestionActivity() {
		insteadOfQuestionFixed.setQuestionActivity(activity);
		assertEquals(activity, insteadOfQuestionFixed.getQuestionActivity());
	}

	@Test
	void setAnswer1() {
		insteadOfQuestionFixed.setAnswer1(activity);
		assertEquals(activity, insteadOfQuestionFixed.getAnswer1());
	}

	@Test
	void setAnswer2() {
		insteadOfQuestionFixed.setAnswer2(activity);
		assertEquals(activity, insteadOfQuestionFixed.getAnswer2());
	}

	@Test
	void setAnswer3() {
		insteadOfQuestionFixed.setAnswer3(activity);
		assertEquals(activity, insteadOfQuestionFixed.getAnswer3());
	}

	@Test
	void setRealCoefficient1() {
		insteadOfQuestionFixed.setRealCoefficient1(0.1);
		assertEquals(0.1, insteadOfQuestionFixed.getRealCoefficient1());
	}

	@Test
	void setRealCoefficient2() {
		insteadOfQuestionFixed.setRealCoefficient2(0.1);
		assertEquals(0.1, insteadOfQuestionFixed.getRealCoefficient2());
	}

	@Test
	void setRealCoefficient3() {
		insteadOfQuestionFixed.setRealCoefficient3(0.1);
		assertEquals(0.1, insteadOfQuestionFixed.getRealCoefficient3());
	}

	@Test
	void equalsAndHash() {
		//activityQuestion, answer1Activity, answer2ActivityFaked,
		//				answer3ActivityFaked, 0.25, 1.0 / 6.0, 1.0 / 3.0

		insteadOfQuestionFixed.setQuestionActivity(activityQuestion);
		insteadOfQuestionFixed.setAnswer1(answer1ActivityFaked);
		insteadOfQuestionFixed.setAnswer2(answer2ActivityFaked);
		insteadOfQuestionFixed.setAnswer3(answer3ActivityFaked);
		insteadOfQuestionFixed.setRealCoefficient1(0.25);
		insteadOfQuestionFixed.setRealCoefficient2(1.0 / 6.0);
		insteadOfQuestionFixed.setRealCoefficient3(1.0 / 3.0);
		assertEquals(insteadOfQuestionFixed, insteadOfQuestionFixed1);
		assertEquals(insteadOfQuestionFixed.hashCode(),
				insteadOfQuestionFixed1.hashCode());
	}

	@Test
	void toStringTest() {
		String expected = "InsteadOfQuestion{questionActivity=Activity{id='00-test-question'," +
				" title='Title for the question', consumptionInWh=120," +
				" imagePath='pathToImageQuestion'," +
				" source='someSource'}, answer1=Activity{id='00-test-answer-1'," +
				" title='Title for the answer 1'," +
				" consumptionInWh=30, imagePath='pathToImageAnswer1', source='someSource'}," +
				" realCoefficient1=0.25," +
				" answer2=Activity{id='00-test-answer-2', title='Title for the answer 2'," +
				" consumptionInWh=10," +
				" imagePath='pathToImageAnswer2', source='someSource'}," +
				" realCoefficient2=0.16666666666666666," +
				" answer3=Activity{id='00-test-answer-3', title='Title for the answer 3'," +
				" consumptionInWh=4," +
				" imagePath='pathToImageAnswer3', source='someSource'}," +
				" realCoefficient3=0.3333333333333333}";
		assertEquals(expected, insteadOfQuestionFixed1.toString());
	}

	@Test
	void answerString() {
		String expected1 = "Title for the answer 1 0.25 times";
		String expected2 = "Title for the answer 2 0.08 times";
		String expected3 = "Title for the answer 3 0.03 times";
		assertEquals(expected1, insteadOfQuestionFixed1.answerString(1));
		assertEquals(expected2, insteadOfQuestionFixed1.answerString(2));
		assertEquals(expected3, insteadOfQuestionFixed1.answerString(3));
	}

	@Test
	void questionString() {
		String expected = "Instead of title for the question you could:";
		assertEquals(expected, insteadOfQuestionFixed1.questionString());
	}

	@Test
	void pointsEarned() {
		assertEquals(500, insteadOfQuestionFixed1.pointsEarned(1000, 1, 15.0f, 30.0f));
		assertEquals(0, insteadOfQuestionFixed1.pointsEarned(1000, 2, 15.0f, 30.0f));
		assertEquals(0, insteadOfQuestionFixed1.pointsEarned(1000, 3, 15.0f, 30.0f));
	}

	@Test
	void singleCorrectAnswer() {
		int points = insteadOfQuestionRandomized.pointsEarned(1000, 1, 0, 1);
		points += insteadOfQuestionRandomized.pointsEarned(1000, 2, 0, 1);
		points += insteadOfQuestionRandomized.pointsEarned(1000, 3, 0, 1);
		assertEquals(1000, points);
	}
}