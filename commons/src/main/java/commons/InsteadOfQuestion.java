package commons;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

public class InsteadOfQuestion {

	private Activity questionActivity;
	private Activity answer1;
	private double realCoefficient1;
	private Activity answer2;
	private double realCoefficient2;
	private Activity answer3;
	private double realCoefficient3;

	/**
	 * Empty constructor.
	 */
	public InsteadOfQuestion() {

	}

	/**
	 * Constructor for InsteadOfQuestion
	 * @param questionActivity the activity for the question
	 * @param answer1 answer at position 1
	 * @param answer2 answer at position 2
	 * @param answer3 answer at position 3
	 */
	public InsteadOfQuestion(Activity questionActivity, Activity answer1,
							Activity answer2, Activity answer3) {
		this.questionActivity = questionActivity;
		calculateRealCoefficients(answer1, answer2, answer3);

		// The following part generates exactly one of the answers to be correct
		// and guarantees that all the others are wrong
		double correctAnswer  = Math.random();
		if (correctAnswer < 1.0 / 3.0) {
			answer2.makeFake();
			answer3.makeFake();
		} else if (correctAnswer < 2.0 / 3.0) {
			answer1.makeFake();
			answer3.makeFake();
		} else {
			answer1.makeFake();
			answer2.makeFake();
		}

		// This sets the answers to their position
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
	}

	/**
	 * A method which calculates and sets the original coefficients for the answers with
	 * respect to the question
	 * @param answer1 the original activity for answer 1
	 * @param answer2 the original activity for answer 2
	 * @param answer3 the original activity for answer 3
	 */
	public void calculateRealCoefficients(Activity answer1, Activity answer2, Activity answer3) {
		this.realCoefficient1 = ((double) answer1.getConsumptionInWh())
				/ this.questionActivity.getConsumptionInWh();
		this.realCoefficient2 = ((double) answer2.getConsumptionInWh())
				/ this.questionActivity.getConsumptionInWh();
		this.realCoefficient3 = ((double) answer3.getConsumptionInWh())
				/ this.questionActivity.getConsumptionInWh();
	}

	/**
	 * Getter for the path to the image
	 * @param numberOfAnswer the sequential number of the answer for which the picture is
	 *                       required. It should be between 1 and 3 inclusive
	 * @return The path to the image for the answer
	 */
	public String getImagePathAnswer(int numberOfAnswer) {
		if (numberOfAnswer == 1) {
			return answer1.getImagePath();
		} else if (numberOfAnswer == 2) {
			return answer2.getImagePath();
		} else if (numberOfAnswer == 3) {
			return answer3.getImagePath();
		} else throw new
				IllegalArgumentException("The input number should be between 1 and 3 inclusive");
	}

	/**
	 * Getter for the image path of the question activity
	 * @return the image path of the question activity
	 */
	public String getImagePathQuestion() {
		return questionActivity.getImagePath();
	}

	/**
	 * Method for the Label with the question that is asked
	 * @return question activity in a user-friendly form as a string
	 */
	public String questionString() {
		String result = "";
		result += "Instead of " + questionActivity.getTitle().toLowerCase(Locale.ROOT);
		result += "you could:";
		return result;
	}

	/**
	 * Method for the Buttons with the answers in them
	 * @param numberOfAnswer the sequential number of the answer for which the text is
	 *                       required. It should be between 1 and 3 inclusive
	 * @return a ready to use string for the labels
	 */
	public String answerString(int numberOfAnswer) {
		String result = "";
		Activity current;
		if (numberOfAnswer == 1) {
			current = answer1;
		} else if (numberOfAnswer == 2) {
			current = answer2;
		} else if (numberOfAnswer == 3) {
			current = answer3;
		} else throw new IllegalArgumentException("The answer asked to be stringed does not exist");
		result += current.getTitle() + " ";
		DecimalFormat df = new DecimalFormat("0.00");
		result += df.format(((double) current.getConsumptionInWh()) /
				((double) questionActivity.getConsumptionInWh()));
		result += " times";
		return result;
	}

	/**
	 * Method used for creating a String with the activity so that it acts as if correct one
	 * @param numberOfAnswer the number of the answer given
	 * @return a string ready for comparison with the given answer
	 */
	private String correctAnswerString(int numberOfAnswer) {
		String result = "";
		double coefficient;
		Activity current;
		if (numberOfAnswer == 1) {
			coefficient = realCoefficient1;
			current = answer1;
		} else if (numberOfAnswer == 2) {
			coefficient = realCoefficient2;
			current = answer2;
		} else if (numberOfAnswer == 3) {
			coefficient = realCoefficient3;
			current = answer3;
		} else throw new IllegalArgumentException("The answer asked to be stringed does not exist");
		result += current.getTitle() + " ";
		DecimalFormat df = new DecimalFormat("0.00");
		result += df.format(coefficient);
		result += " times";
		return result;
	}

	/**
	 * Calculates the points earned depending on the answer given
	 * @param maxPoints the maximal amount of points possible
	 * @param answerGiven the answer given by the user
	 * @param timePassed the time passed before answering in seconds
	 * @param totalTime total time available for answer in seconds
	 * @return The total amount of points earned as an integer
	 */
	public int pointsEarned(int maxPoints, int answerGiven, float timePassed, float totalTime) {
		if (!answerString(answerGiven).equals(correctAnswerString(answerGiven))){
			return 0;
		}
		double coefficientTime = ((double) (totalTime - timePassed)) / ((double) totalTime);
		float pointsEarned = ((Double) (coefficientTime * maxPoints)).floatValue();
		return Math.round(pointsEarned);
	}

	/**
	 * Getter for the question activity
	 * @return the activity related to the question
	 */
	public Activity getQuestionActivity() {
		return questionActivity;
	}

	/**
	 * Getter for the answer activity at position 1
	 * @return the answer activity at position 1
	 */
	public Activity getAnswer1() {
		return answer1;
	}

	/**
	 * Getter for the real coefficient at position 1
	 * @return the original coefficient with respect to the question
	 */
	public double getRealCoefficient1() {
		return realCoefficient1;
	}

	/**
	 * Getter for the answer activity at position 2
	 * @return the answer activity at position 2
	 */
	public Activity getAnswer2() {
		return answer2;
	}

	/**
	 * Getter for the real coefficient at position 2
	 * @return the original coefficient with respect to the question
	 */
	public double getRealCoefficient2() {
		return realCoefficient2;
	}

	/**
	 * Getter for the answer activity at position 3
	 * @return the answer activity at position 3
	 */
	public Activity getAnswer3() {
		return answer3;
	}

	/**
	 * Getter for the real coefficient at position 3
	 * @return the original coefficient with respect to the question
	 */
	public double getRealCoefficient3() {
		return realCoefficient3;
	}

	/**
	 * Setter for the question activity
	 * @param questionActivity the new question activity
	 */
	public void setQuestionActivity(Activity questionActivity) {
		this.questionActivity = questionActivity;
	}

	/**
	 * Setter for the answer activity at position 1
	 * @param answer1 the new activity answer at position 1
	 */
	public void setAnswer1(Activity answer1) {
		this.answer1 = answer1;
	}

	/**
	 * Setter for the real coefficient at position 1
	 * @param realCoefficient1 the new real coefficient at position 1
	 */
	public void setRealCoefficient1(double realCoefficient1) {
		this.realCoefficient1 = realCoefficient1;
	}

	/**
	 * Setter for the answer activity at position 2
	 * @param answer2 the new answer activity at position 2
	 */
	public void setAnswer2(Activity answer2) {
		this.answer2 = answer2;
	}

	/**
	 * Setter for the real coefficients at position 2
	 * @param realCoefficient2 the new real coefficient at position 2
	 */
	public void setRealCoefficient2(double realCoefficient2) {
		this.realCoefficient2 = realCoefficient2;
	}

	/**
	 * Setter for the answer activity at position 3
	 * @param answer3 the new answer activity at position 3
	 */
	public void setAnswer3(Activity answer3) {
		this.answer3 = answer3;
	}

	/**
	 * Setter for the real coefficient at position 3
	 * @param realCoefficient3 the new real coefficient at position 3
	 */
	public void setRealCoefficient3(double realCoefficient3) {
		this.realCoefficient3 = realCoefficient3;
	}

	/**
	 * Equals method for the InsteadOfQuestion class
	 * @param o The object for comparison
	 * @return true if the objects are identical and false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InsteadOfQuestion that = (InsteadOfQuestion) o;
		return Double.compare(that.realCoefficient1, realCoefficient1) == 0
				&& Double.compare(that.realCoefficient2, realCoefficient2) == 0
				&& Double.compare(that.realCoefficient3, realCoefficient3) == 0
				&& questionActivity.equals(that.questionActivity)
				&& answer1.equals(that.answer1) && answer2.equals(that.answer2)
				&& answer3.equals(that.answer3);
	}

	/**
	 * Hashing function for the InsteadOfQuestion class
	 * @return the hash code of the object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(questionActivity, answer1, realCoefficient1,
				answer2, realCoefficient2, answer3, realCoefficient3);
	}

	/**
	 * To String method for an InsteadOfQuestion
	 * @return the object in the form of human-readable string format
	 */
	@Override
	public String toString() {
		return "InsteadOfQuestion{" +
				"questionActivity=" + questionActivity +
				", answer1=" + answer1 +
				", realCoefficient1=" + realCoefficient1 +
				", answer2=" + answer2 +
				", realCoefficient2=" + realCoefficient2 +
				", answer3=" + answer3 +
				", realCoefficient3=" + realCoefficient3 +
				'}';
	}
}
