package commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MCQuestion extends Question{

	private Activity activity;
	private int answer1;
	private int answer2;
	private int answer3;
	private ArrayList<Integer> order;

	/**
	 * An empty constructor
	 */
	public MCQuestion() {

	}

	/**
	 * Constructor of a MCQuestion
	 * @param activity the activity related to the question
	 * @param answer1 the answer at position 1
	 * @param answer2 the answer at position 2
	 * @param answer3 the answer at position 3
	 */
	public MCQuestion(Activity activity, int answer1, int answer2, int answer3) {
		this.activity = activity;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
	}

	/**
	 * Constructor for the Multiple Choice Question
	 * @param activity the activity on which the generation is based
	 */
	public MCQuestion(Activity activity) {
		this.activity = activity;
		HashMap<Integer, Integer> answers = generateAnswers();
		this.answer1 = answers.get(1);
		this.answer2 = answers.get(2);
		this.answer3 = answers.get(3);
	}

	/**
	 * Generates a random sequence for the order of answers which consists of the numbers
	 * 1, 2 and 3
	 */
	private void generateSequence() {
		ArrayList<Integer> result = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		Collections.shuffle(result);
		// Sets the order so that the other methods could be tested
		this.order = result;
	}

	/**
	 * Generates a wrong answer for the question
	 * @return a wrong answer
	 */
	private int generateAnswer() {
		int result;
		do {
			// At random principle answer is generated and this answer is of the same magnitude
			// At least in most of the time
			float moreOrLess = ((Double) (Math.random() * 2)).floatValue();
			result = Math.round(this.activity.getConsumptionInWh() * moreOrLess);
		}
		while (result == this.activity.getConsumptionInWh());
		return  result;
	}

	/**
	 * Generates the answers and their sequence
	 * @return a map containing the sequence of answers as keys and the answers themselves
	 */
	private HashMap<Integer, Integer> generateAnswers() {
		generateSequence();
		HashMap<Integer, Integer> result = new HashMap<>();
		for(Integer i : order) {
			if (order.indexOf(i) == 0) {
				result.put(order.get(0), this.activity.getConsumptionInWh());
				continue;
			}
			result.put(i, generateAnswer());
		}
		return result;
	}

	/**
	 * Calculates the amount of points based on the provided criteria
	 * Works linearly with the times provided which can be changed
	 * @param maxPoints the maximum amount of points
	 * @param answerGiven the answer given by the user
	 * @param timeTakenToAnswer the time that took the user to answer in seconds
	 * @param totalTime the total time that the user had to answer in seconds
	 * @return the amount of points the user achieved
	 */
	public int pointsEarned(int maxPoints,
			int answerGiven, float timeTakenToAnswer, float totalTime) {
		if (answerGiven != activity.getConsumptionInWh()) return 0;
		return Math.round((maxPoints * (totalTime - timeTakenToAnswer)) / totalTime);
	}

	/**
	 * Printing method for the question
	 * @return the title of the activity asked as a question
	 */
	public String printQuestion() {
		return activity.getTitle() + " takes:";
	}

	/**
	 * Printing method for the first of the generated answers
	 * @return the first answer in a form ready for output
	 */
	public String printAnswer1() {
		return answer1 + " Wh";
	}

	/**
	 * Printing method for the second of the generated answers
	 * @return the second answer in a form ready for output
	 */
	public String printAnswer2() {
		return answer2 + " Wh";
	}

	/**
	 * Printing method for the third of the generated answers
	 * @return the third answer in a form ready for output
	 */
	public String printAnswer3() {
		return answer3 + " Wh";
	}

	/**
	 * Getter for the activity
	 * @return the activity related with this question
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Getter for the answer at position 1
	 * @return the answer at position 1
	 */
	public int getAnswer1() {
		return answer1;
	}

	/**
	 * Getter for the answer at position 2
	 * @return the answer at position 2
	 */
	public int getAnswer2() {
		return answer2;
	}

	/**
	 * Getter for the answer at position 3
	 * @return the answer at position 3
	 */
	public int getAnswer3() {
		return answer3;
	}

	/**
	 * Return the image path related to this question
	 * @return the path of the image
	 */
	public String getPicturePath() {
		return activity.getImagePath();
	}

	/**
	 * Getter for the order of the answers
	 * @return the order of the answers
	 */
	public List<Integer> getOrder() {
		return this.order;
	}

	/**
	 * Setter for the activity related with this question
	 * @param activity the activity related with this question
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Setter for the answer at position 1
	 * @param answer1 the new answer at position 1
	 */
	public void setAnswer1(int answer1) {
		this.answer1 = answer1;
	}

	/**
	 * Setter for the answer at position 2
	 * @param answer2 the new answer at position 2
	 */
	public void setAnswer2(int answer2) {
		this.answer2 = answer2;
	}

	/**
	 * Setter for the answer at position 3
	 * @param answer3 the new answer at position 3
	 */
	public void setAnswer3(int answer3) {
		this.answer3 = answer3;
	}

	/**
	 * Equals method for the Multiple Choice Questions
	 * @param o the object for comparison
	 * @return true if the objects are equal and false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MCQuestion that = (MCQuestion) o;
		return answer1 == that.answer1 && answer2 == that.answer2 && answer3 == that.answer3 &&
				activity.equals(that.activity);
	}

	/**
	 * Hashing method for the MCQuestions
	 * @return the hash code of the instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activity, answer1, answer2, answer3);
	}
}
