package commons;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static commons.Utility.doubleEquals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes.Type(value = InsteadOfQuestion.class, name = "InsteadOfQuestion")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InsteadOfQuestion extends Question {
	@JsonProperty("question_activity")
	private Activity questionActivity;

	@JsonProperty("answer1")
	private Activity answer1;

	@JsonProperty("real_coefficient_1")
	private double realCoefficient1;

	@JsonProperty("answer2")
	private Activity answer2;

	@JsonProperty("real_coefficient_2")
	private double realCoefficient2;

	@JsonProperty("answer3")
	private Activity answer3;

	@JsonProperty("real_coefficient_3")
	private double realCoefficient3;

	/**
	 * Empty constructor.
	 */
	public InsteadOfQuestion() {}

	/**
	 * Constructor for InsteadOfQuestion.
	 * @param questionActivity The activity for the question.
	 * @param answer1Real Initial answer activity at position 1.
	 * @param answer2Real Initial answer activity at position 2.
	 * @param answer3Real Initial answer activity at position 3.
	 */
	public InsteadOfQuestion(
		Activity questionActivity,
		Activity answer1Real,
		Activity answer2Real,
		Activity answer3Real
	) {
		Activity answer1 = answer1Real.deepCopy();
		Activity answer2 = answer2Real.deepCopy();
		Activity answer3 = answer3Real.deepCopy();
		this.questionActivity = questionActivity;
		this.calculateRealCoefficients(answer1, answer2, answer3);
		List<Long> forbiddenValues = new ArrayList<>();
		forbiddenValues.add(answer1.getConsumptionInWh());
		forbiddenValues.add(answer2.getConsumptionInWh());
		forbiddenValues.add(answer3.getConsumptionInWh());

		// The following part generates exactly one of the answers to be correct
		// and guarantees that all the others are wrong
		double correctAnswer = (new Random(answer1.getConsumptionInWh()).nextDouble());
		if (correctAnswer < 1.0 / 3.0) {
			answer2.makeFake(forbiddenValues);
			answer3.makeFake(forbiddenValues);
		} else if (correctAnswer < 2.0 / 3.0) {
			answer1.makeFake(forbiddenValues);
			answer3.makeFake(forbiddenValues);
		} else {
			answer1.makeFake(forbiddenValues);
			answer2.makeFake(forbiddenValues);
		}

		// This sets the answers to their position
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
	}

	/**
	 * Constructor for customizing fields in test.
	 * @param questionActivity The question Activity.
	 * @param answer1 The answer activity at position 1.
	 * @param answer2 The answer activity at position 2.
	 * @param answer3 The answer activity at position 3.
	 * @param c1 The custom coefficient.
	 * @param c2 The custom coefficient.
	 * @param c3 The custom coefficient.
	 */
	public InsteadOfQuestion(
		Activity questionActivity,
		Activity answer1,
		Activity answer2,
		Activity answer3,
		double c1,
		double c2,
		double c3
	) {
		this.questionActivity = questionActivity;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.realCoefficient1 = c1;
		this.realCoefficient2 = c2;
		this.realCoefficient3 = c3;
	}

	/**
	 * A method which calculates and sets the original coefficients for the answers with
	 * respect to the question.
	 * @param answer1 The original activity for answer 1.
	 * @param answer2 The original activity for answer 2.
	 * @param answer3 The original activity for answer 3.
	 */
	private void calculateRealCoefficients(Activity answer1, Activity answer2, Activity answer3) {
		this.realCoefficient1 = answer1.getConsumptionInWh()
			/ (double) this.questionActivity.getConsumptionInWh();
		this.realCoefficient2 = answer2.getConsumptionInWh()
			/ (double) this.questionActivity.getConsumptionInWh();
		this.realCoefficient3 = answer3.getConsumptionInWh()
			/ (double) this.questionActivity.getConsumptionInWh();
	}

	/**
	 * Getter for the path to the image.
	 * @param numberOfAnswer The sequential number of the answer for which the picture is required.
	 *                       It should be between 1 and 3 inclusive.
	 * @return The path to the image for the answer.
	 */
	public String getImagePathAnswer(int numberOfAnswer) {
		return switch (numberOfAnswer) {
			case 1 -> this.answer1.getImagePath();
			case 2 -> this.answer2.getImagePath();
			case 3 -> this.answer3.getImagePath();
			default -> throw new IllegalArgumentException("The input number should be 0 < n < 4");
		};
	}

	/**
	 * Useful for sending the information about a picture to the user.
	 * @param numberOfAnswer The sequential number of the answer for which the picture is required.
	 *                       It should be between 1 and 3 inclusive.
	 * @return A byte array with information about the image for choice made.
	 */
	public byte[] imageInByteArray(int numberOfAnswer) {
		return switch (numberOfAnswer) {
			case 1 -> this.answer1.getImageInArray();
			case 2 -> this.answer2.getImageInArray();
			case 3 -> this.answer3.getImageInArray();
			default -> throw new IllegalArgumentException("The input number should be 0 < n < 4");
		};
	}

	/**
	 * Getter for the image path of the question activity.
	 * @return The image path of the question activity.
	 */
	@JsonProperty("image_path_question")
	public String getImagePathQuestion() {
		return this.questionActivity.getImagePath();
	}

	/**
	 * Useful for sending the information about a picture to the user.
	 * @return A byte array with information about the image for the question.
	 */
	public byte[] imageInByteArrayQuestion() {
		return this.questionActivity.getImageInArray();
	}

	/**
	 * Method for the Label with the question that is asked.
	 * @return Question activity in a user-friendly form as a string.
	 */
	public String questionString() {
		return "Instead of "
			+ this.questionActivity.getTitle().toLowerCase(Locale.ROOT)
			+ " you could: ";
	}

	/**
	 * Method for the Buttons with the answers in them.
	 * @param numberOfAnswer The sequential number of the answer for which the text is required.  It
	 *                       should be between 1 and 3 inclusive.
	 * @return A ready to use string for the labels.
	 */
	public String answerString(long numberOfAnswer) {
		DecimalFormat df = new DecimalFormat("0.00");
		Activity current = switch ((int) numberOfAnswer) {
			case 1 -> this.answer1;
			case 2 -> this.answer2;
			case 3 -> this.answer3;
			default ->
				throw new IllegalArgumentException("This number of answers should be 0 < n < 4");
		};

		return current.getTitle()
			+ " "
			+ df.format(
				this.questionActivity.getConsumptionInWh()
					/ (double) current.getConsumptionInWh()
			)
			+ " times";
	}

	/**
	 * Method used for creating a String with the activity so that it acts as if correct one.
	 * @param numberOfAnswer The number of the answer given.
	 * @return The original coefficient of the answer as if it was correct.
	 */
	private double correctAnswerCoefficient(int numberOfAnswer) {
		return switch (numberOfAnswer) {
			case 1 -> this.realCoefficient1;
			case 2 -> this.realCoefficient2;
			case 3 -> this.realCoefficient3;
			default ->
				throw new IllegalArgumentException("This number of answers should be 0 < n < 4");
		};
	}

	/**
	 * Returns the activity with the correct answer.
	 * @return The correct answer activity.
	 */
	public Activity correctAnswer() {
		double calculatedCoefficient1 = this.answer1.getConsumptionInWh()
			/ (double) this.questionActivity.getConsumptionInWh();
		double calculatedCoefficient2 = this.answer2.getConsumptionInWh()
			/ (double) this.questionActivity.getConsumptionInWh();

		if (doubleEquals(this.realCoefficient1, calculatedCoefficient1)) {
			return this.answer1;
		} else if (doubleEquals(this.realCoefficient2, calculatedCoefficient2)) {
			return this.answer2;
		}
		return this.answer3;
	}

	/**
	 * Finds the consumption of an activity based on title.
	 * @param title The title of the activity.
	 * @return The consumption of the activity.
	 */
	public long returnEnergyConsumption(String title) {
		if (title.equals(this.answer1.getTitle())) {
			return this.answer1.getConsumptionInWh();
		} else if (title.equals(this.answer2.getTitle())) {
			return this.answer2.getConsumptionInWh();
		}
		return this.answer3.getConsumptionInWh();
	}

	/**
	 * Calculates the points earned depending on the answer given.
	 * @param maxPoints The maximal amount of points possible.
	 * @param answerGiven The answer given by the user.
	 * @param progress Time left.
	 * @param doublePoints If the joker has been used.
	 * @return The total amount of points earned as an integer.
	 */
	public int pointsEarned(
		int maxPoints,
		long answerGiven,
		double progress,
		boolean doublePoints
	) {
		Activity answerActivity;
		switch ((int) answerGiven) {
			case 1 -> answerActivity = this.answer1;
			case 2 -> answerActivity = this.answer2;
			case 3 -> answerActivity = this.answer3;
			default -> throw new IllegalArgumentException("Answer given can be either 1, 2 or 3");
		}
		double currentCoefficient = answerActivity.getConsumptionInWh()
			/ (double) this.questionActivity.getConsumptionInWh();
		if (!doubleEquals(currentCoefficient, this.correctAnswerCoefficient((int) answerGiven))) {
			return 0;
		}
		int factor = doublePoints ? 2 : 1;
		float pointsEarned = (float) progress * maxPoints * factor;
		return Math.round(pointsEarned);
	}

	/**
	 * Getter for the question activity.
	 * @return The activity related to the question.
	 */
	public Activity getQuestionActivity() {
		return this.questionActivity;
	}

	/**
	 * Getter for the answer activity at position 1.
	 * @return The answer activity at position 1.
	 */
	public Activity getAnswer1() {
		return this.answer1;
	}

	/**
	 * Getter for the real coefficient at position 1.
	 * @return The original coefficient with respect to the question.
	 */
	public double getRealCoefficient1() {
		return this.realCoefficient1;
	}

	/**
	 * Getter for the answer activity at position 2.
	 * @return The answer activity at position 2.
	 */
	public Activity getAnswer2() {
		return this.answer2;
	}

	/**
	 * Getter for the real coefficient at position 2.
	 * @return The original coefficient with respect to the question.
	 */
	public double getRealCoefficient2() {
		return this.realCoefficient2;
	}

	/**
	 * Getter for the answer activity at position 3.
	 * @return The answer activity at position 3.
	 */
	public Activity getAnswer3() {
		return this.answer3;
	}

	/**
	 * Getter for the real coefficient at position 3.
	 * @return The original coefficient with respect to the question.
	 */
	public double getRealCoefficient3() {
		return this.realCoefficient3;
	}

	/**
	 * Setter for the question activity.
	 * @param questionActivity Ahe new question activity.
	 */
	public void setQuestionActivity(Activity questionActivity) {
		this.questionActivity = questionActivity;
	}

	/**
	 * Setter for the answer activity at position 1.
	 * @param answer1 The new activity answer at position 1.
	 */
	public void setAnswer1(Activity answer1) {
		this.answer1 = answer1;
	}

	/**
	 * Setter for the real coefficient at position 1.
	 * @param realCoefficient1 The new real coefficient at position 1.
	 */
	public void setRealCoefficient1(double realCoefficient1) {
		this.realCoefficient1 = realCoefficient1;
	}

	/**
	 * Setter for the answer activity at position 2.
	 * @param answer2 The new answer activity at position 2.
	 */
	public void setAnswer2(Activity answer2) {
		this.answer2 = answer2;
	}

	/**
	 * Setter for the real coefficients at position 2.
	 * @param realCoefficient2 The new real coefficient at position 2.
	 */
	public void setRealCoefficient2(double realCoefficient2) {
		this.realCoefficient2 = realCoefficient2;
	}

	/**
	 * Setter for the answer activity at position 3.
	 * @param answer3 The new answer activity at position 3.
	 */
	public void setAnswer3(Activity answer3) {
		this.answer3 = answer3;
	}

	/**
	 * Setter for the real coefficient at position 3.
	 * @param realCoefficient3 The new real coefficient at position 3.
	 */
	public void setRealCoefficient3(double realCoefficient3) {
		this.realCoefficient3 = realCoefficient3;
	}

	/**
	 * Equals method for the InsteadOfQuestion class.
	 * @param o The object for comparison.
	 * @return True if the objects are identical and false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		InsteadOfQuestion that = (InsteadOfQuestion) o;
		return doubleEquals(that.realCoefficient1, this.realCoefficient1)
			&& doubleEquals(that.realCoefficient2, this.realCoefficient2)
			&& doubleEquals(that.realCoefficient3, this.realCoefficient3)
			&& this.questionActivity.equals(that.questionActivity)
			&& this.answer1.equals(that.answer1)
			&& this.answer2.equals(that.answer2)
			&& this.answer3.equals(that.answer3);
	}

	/**
	 * Hashing function for the InsteadOfQuestion class.
	 * @return The hash code of the object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(
			this.questionActivity,
			this.answer1,
			this.realCoefficient1,
			this.answer2,
			this.realCoefficient2,
			this.answer3,
			this.realCoefficient3
		);
	}

	/**
	 * To String method for an InsteadOfQuestion.
	 * @return The object in the form of human-readable string format.
	 */
	@Override
	public String toString() {
		return "InsteadOfQuestion{"
			+ "questionActivity=" + this.questionActivity
			+ ", answer1=" + this.answer1
			+ ", realCoefficient1=" + this.realCoefficient1
			+ ", answer2=" + this.answer2
			+ ", realCoefficient2=" + this.realCoefficient2
			+ ", answer3=" + this.answer3
			+ ", realCoefficient3=" + this.realCoefficient3
			+ '}';
	}
}