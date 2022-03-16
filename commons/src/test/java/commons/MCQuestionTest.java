




package commons;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MCQuestionTest {

	private final Activity activity = new Activity("00-testing", "Activity X consumes",
			120,"somePath", "someSource");
	private final Activity activity1 = new Activity("00-testing1", "Activity X consumes ",
			121,"somePath1", "someSource1");
	private MCQuestion mcQuestion1;
	private MCQuestion mcQuestion2;
	private final MCQuestion mcQuestion3 = new MCQuestion(activity);
	private MCQuestion mcQuestion4;


	@BeforeEach
	void setUpQuestion() {
		mcQuestion1 = new MCQuestion();
		mcQuestion2 = new MCQuestion(activity);
		mcQuestion4 = new MCQuestion(activity, 120, 123, 117);
	}

	@Test
	void checkConstructor() {
		Assertions.assertNotNull(mcQuestion1);
		Assertions.assertNotNull(mcQuestion2);
		Assertions.assertNotNull(mcQuestion3);
		Assertions.assertNotNull(mcQuestion4);
	}

	@Test
	void getActivity() {
		Assertions.assertEquals(activity, mcQuestion2.getActivity());
		Assertions.assertEquals(activity, mcQuestion3.getActivity());
		Assertions.assertEquals(activity, mcQuestion4.getActivity());
		Assertions.assertNull(mcQuestion1.getActivity());
	}

	@Test
	void getAnswer1() {
		Assertions.assertEquals(120, mcQuestion4.getAnswer1());
	}

	@Test
	void getAnswer2() {
		Assertions.assertEquals(123, mcQuestion4.getAnswer2());
	}

	@Test
	void getAnswer3() {
		Assertions.assertEquals(117, mcQuestion4.getAnswer3());
	}

	@Test
	void getPicturePath() {
		Assertions.assertEquals(activity.getImagePath(), mcQuestion4.getPicturePath());
	}

	@Test
	void setActivity() {
		mcQuestion1.setActivity(activity1);
		Assertions.assertEquals(activity1, mcQuestion1.getActivity());
		mcQuestion4.setActivity(activity1);
		Assertions.assertEquals(activity1, mcQuestion4.getActivity());
	}

	@Test
	void setAnswer1() {
		mcQuestion4.setAnswer1(2);
		Assertions.assertEquals(2, mcQuestion4.getAnswer1());
	}

	@Test
	void setAnswer2() {
		mcQuestion4.setAnswer2(3);
		Assertions.assertEquals(3, mcQuestion4.getAnswer2());
	}

	@Test
	void setAnswer3() {
		mcQuestion4.setAnswer3(4);
		Assertions.assertEquals(4, mcQuestion4.getAnswer3());
	}

	@Test
	void equalsAndHash() {
		MCQuestion mcQuestion = new MCQuestion(activity, 120, 123, 117);
		Assertions.assertEquals(mcQuestion4, mcQuestion);
		Assertions.assertEquals(mcQuestion4.hashCode(), mcQuestion.hashCode());
	}

	@Test
	void printQuestion() {
		Assertions.assertEquals(activity.getTitle() + " takes:", mcQuestion3.printQuestion());
	}

	@Test
	void printAnswer1() {
		Assertions.assertEquals(mcQuestion3.getAnswer1() + " Wh", mcQuestion3.printAnswer1());
	}

	@Test
	void printAnswer2() {
		Assertions.assertEquals(mcQuestion3.getAnswer2() + " Wh", mcQuestion3.printAnswer2());
	}

	@Test
	void printAnswer3() {
		Assertions.assertEquals(mcQuestion3.getAnswer3() + " Wh", mcQuestion3.printAnswer3());
	}

	@Test
	void generateAnswer() {
		Assertions.assertNotEquals(mcQuestion4.getAnswer1(), mcQuestion4.getAnswer2());
		Assertions.assertNotEquals(mcQuestion4.getAnswer2(), mcQuestion4.getAnswer3());
		Assertions.assertNotEquals(mcQuestion4.getAnswer3(), mcQuestion4.getAnswer1());
	}

	@Test
	void getOrder() {
		Assertions.assertTrue(mcQuestion3.getOrder().contains(1));
		Assertions.assertTrue(mcQuestion3.getOrder().contains(2));
		Assertions.assertTrue(mcQuestion3.getOrder().contains(3));
	}

	@Test
	void generateAnswers() {
		if (mcQuestion3.getOrder().indexOf(1) == 0) {
			Assertions.assertEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer1());
			Assertions.assertNotEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer2());
			Assertions.assertNotEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer3());
		} else if (mcQuestion3.getOrder().indexOf(2) == 0) {
			Assertions.assertNotEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer1());
			Assertions.assertEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer2());
			Assertions.assertNotEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer3());
		} else if (mcQuestion3.getOrder().indexOf(3) == 0) {
			Assertions.assertNotEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer1());
			Assertions.assertNotEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer2());
			Assertions.assertEquals(activity.getConsumptionInWh(), mcQuestion3.getAnswer3());
		}
	}

	@Test
	void pointsEarned() {
		Assertions.assertEquals(1000, mcQuestion4.pointsEarned(1000, 120, 1.0));
	}


}