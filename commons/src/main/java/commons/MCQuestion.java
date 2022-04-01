package commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes.Type(value = MCQuestion.class, name = "MCQuestion")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MCQuestion extends Question{
	@JsonProperty("activity")
	private Activity activity;

	@JsonProperty("answer1")
	private long answer1;

	@JsonProperty("answer2")
	private long answer2;

	@JsonProperty("answer3")
	private long answer3;

	@JsonProperty("order")
	private ArrayList<Integer> order;

	/**
	 * An empty constructor.
	 */
	public MCQuestion() {}

	/**
	 * Constructor of a MCQuestion.
	 * @param activity The activity related to the question.
	 * @param answer1 The answer at position 1.
	 * @param answer2 The answer at position 2.
	 * @param answer3 The answer at position 3.
	 */
	public MCQuestion(Activity activity, int answer1, int answer2, int answer3) {
		this.activity = activity;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
	}

	/**
	 * Constructor for the Multiple Choice Question.
	 * @param activity The activity on which the generation is based.
	 * @param seed Seed that dictates the random shuffle order.
	 */
	public MCQuestion(Activity activity, long seed) {
		this.activity = activity;
		HashMap<Integer, Long> answers = this.generateAnswers(seed);
		this.answer1 = answers.get(1);
		this.answer2 = answers.get(2);
		this.answer3 = answers.get(3);
	}

	/**
	 * Generates a random sequence for the order of answers which consists of the numbers 1, 2, 3.
	 * @param seed Seed that dictates the order.
	 */
	private void generateSequence(long seed) {
		ArrayList<Integer> result = new ArrayList<>(Arrays.asList(1, 2, 3));
		Collections.shuffle(result, new Random(seed));
		// Sets the order so that the other methods could be tested
		this.order = result;
	}

	/**
	 * Generates a wrong answer for the question.
	 * @param seed Seed that magnitude of the answer.
	 * @return A wrong answer.
	 */
	private long generateAnswer(long seed) {
		long result;
		Random random = new Random(seed);
		do {
			// At random principle answer is generated and this answer is of the same magnitude
			// At least in most of the time
			double moreOrLess = random.nextDouble() * 2;
			result = Math.round(this.activity.getConsumptionInWh() * moreOrLess);
		} while (result == this.activity.getConsumptionInWh());
		return result;
	}

	/**
	 * Generates the answers and their sequence.
	 * @param seed Seed that dictates the order of the answers.
	 * @return A map containing the sequence of answers as keys and the answers themselves.
	 */
	private HashMap<Integer, Long> generateAnswers(long seed) {
		this.generateSequence(seed);
		HashMap<Integer, Long> result = new HashMap<>();
		for (Integer i : this.order) {
			result.put(
				i,
				this.order.indexOf(i) == 0
					? this.activity.getConsumptionInWh()
					: this.generateAnswer(seed)
			);
		}
		return result;
	}

	/**
	 * Calculates the amount of points based on the provided criteria.  Works linearly with the
	 * times provided which can be changed.
	 * @param maxPoints The maximum amount of points.
	 * @param answerGiven The answer given by the user.
	 * @param progress Time left.
	 * @param doublePoints If the joker has been used.
	 * @return The amount of points the user achieved.
	 */
	public int pointsEarned(
		int maxPoints,
		long answerGiven,
		double progress,
		boolean doublePoints
	) {
		if (answerGiven != this.activity.getConsumptionInWh())
			return 0;
		int factor = doublePoints ? 2 : 1;
		return (int) Math.round(maxPoints * progress * factor);
	}

	/**
	 * Printing method for the question.
	 * @return The title of the activity asked as a question.
	 */
	public String printQuestion() {
		return this.activity.getTitle() + " takes: ";
	}

	/**
	 * Printing method for the first of the generated answers.
	 * @return The first answer in a form ready for output.
	 */
	public String printAnswer1() {
		return this.answer1 + " Wh";
	}

	/**
	 * Printing method for the second of the generated answers.
	 * @return The second answer in a form ready for output.
	 */
	public String printAnswer2() {
		return this.answer2 + " Wh";
	}

	/**
	 * Printing method for the third of the generated answers.
	 * @return The third answer in a form ready for output.
	 */
	public String printAnswer3() {
		return this.answer3 + " Wh";
	}

	/**
	 * Getter for the activity.
	 * @return The activity related with this question.
	 */
	public Activity getActivity() {
		return this.activity;
	}

	/**
	 * Getter for the answer at position 1.
	 * @return The answer at position 1.
	 */
	public long getAnswer1() {
		return this.answer1;
	}

	/**
	 * Getter for the answer at position 2.
	 * @return The answer at position 2.
	 */
	public long getAnswer2() {
		return this.answer2;
	}

	/**
	 * Getter for the answer at position 3.
	 * @return The answer at position 3.
	 */
	public long getAnswer3() {
		return this.answer3;
	}

	/**
	 * Return the image path related to this question.
	 * @return The path of the image.
	 */
	@JsonProperty("picture_path")
	public String getPicturePath() {
		return this.activity.getImagePath();
	}

	/**
	 * Useful for sending the information about a picture to the user.
	 * @return A byte array with information about the image for the question.
	 */
	public byte[] imageInByteArrayQuestion() {
		return this.activity.getImageInArray();
	}

	/**
	 * Getter for the order of the answers.
	 * @return The order of the answers.
	 */
	public List<Integer> getOrder() {
		return this.order;
	}

	/**
	 * Setter for the activity related with this question.
	 * @param activity The activity related with this question.
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Setter for the answer at position 1.
	 * @param answer1 The new answer at position 1.
	 */
	public void setAnswer1(int answer1) {
		this.answer1 = answer1;
	}

	/**
	 * Setter for the answer at position 2.
	 * @param answer2 The new answer at position 2.
	 */
	public void setAnswer2(int answer2) {
		this.answer2 = answer2;
	}

	/**
	 * Setter for the answer at position 3.
	 * @param answer3 The new answer at position 3.
	 */
	public void setAnswer3(int answer3) {
		this.answer3 = answer3;
	}

	/**
	 * Equals method for the Multiple Choice Questions.
	 * @param o The object for comparison.
	 * @return True if the objects are equal and false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MCQuestion that = (MCQuestion) o;
		return this.answer1 == that.answer1
			&& this.answer2 == that.answer2
			&& this.answer3 == that.answer3
			&& this.activity.equals(that.activity);
	}

	/**
	 * Hashing method for the MCQuestions.
	 * @return The hash code of the instance.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.activity, this.answer1, this.answer2, this.answer3);
	}
}