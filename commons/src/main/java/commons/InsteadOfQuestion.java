package commons;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
	 * Constructor for InsteadOfQuestion
	 * @param questionActivity the activity for the question
	 * @param answer1 initial answer activity at position 1
	 * @param answer2 initial answer activity at position 2
	 * @param answer3 initial answer activity at position 3
	 */
	public InsteadOfQuestion(
			Activity questionActivity,
			Activity answer1,
			Activity answer2,
			Activity answer3
	) {
		this.questionActivity = questionActivity;
		calculateRealCoefficients(answer1, answer2, answer3);
		List<Long> forbiddenValues = new ArrayList<>();
		forbiddenValues.add(answer1.getConsumptionInWh());
		forbiddenValues.add(answer2.getConsumptionInWh());
		forbiddenValues.add(answer3.getConsumptionInWh());

		// The following part generates exactly one of the answers to be correct
		// and guarantees that all the others are wrong
		double correctAnswer = Math.random();
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
	 * Constructor for customizing fields in test
	 * @param questionActivity the question Activity
	 * @param answer1 the answer activity at position 1
	 * @param answer2 the answer activity at position 2
	 * @param answer3 the answer activity at position 3
	 * @param c1 the custom coefficient
	 * @param c2 the custom coefficient
	 * @param c3 the custom coefficient
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
	 * respect to the question
	 * @param answer1 the original activity for answer 1
	 * @param answer2 the original activity for answer 2
	 * @param answer3 the original activity for answer 3
	 */
	private void calculateRealCoefficients(Activity answer1, Activity answer2, Activity answer3) {
		this.realCoefficient1 = ((double) answer1.getConsumptionInWh())
				/ ((double) this.questionActivity.getConsumptionInWh());
		this.realCoefficient2 = ((double) answer2.getConsumptionInWh())
				/ ((double) this.questionActivity.getConsumptionInWh());
		this.realCoefficient3 = ((double) answer3.getConsumptionInWh())
				/ ((double) this.questionActivity.getConsumptionInWh());
	}

	/**
	 * Getter for the path to the image
	 * @param numberOfAnswer the sequential number of the answer for which the picture is
	 *                       required. It should be between 1 and 3 inclusive
	 * @return The path to the image for the answer
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
	 * Useful for sending the information about a picture to the user
	 * @param numberOfAnswer the sequential number of the answer for which the picture is
	 *                       required. It should be between 1 and 3 inclusive
	 * @return a byte array with information about the image for choice made
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
	 * Getter for the image path of the question activity
	 * @return the image path of the question activity
	 */
	@JsonProperty("image_path_question")
	public String getImagePathQuestion() {
		return this.questionActivity.getImagePath();
	}

	/**
	 * Useful for sending the information about a picture to the user
	 * @return a byte array with information about the image for the question
	 */
	public byte[] imageInByteArrayQuestion() {
		return this.questionActivity.getImageInArray();
	}

	/**
	 * Method for the Label with the question that is asked
	 * @return question activity in a user-friendly form as a string
	 */
	public String questionString() {
		return "Instead of "
				+ this.questionActivity.getTitle().toLowerCase(Locale.ROOT)
				+ " you could: ";
	}

	/**
	 * Method for the Buttons with the answers in them
	 * @param numberOfAnswer the sequential number of the answer for which the text is
	 *                       required. It should be between 1 and 3 inclusive
	 * @return a ready to use string for the labels
	 */
	public String answerString(long numberOfAnswer) {
		DecimalFormat df = new DecimalFormat("0.00");
		Activity current = switch ((int) numberOfAnswer) {
			case 1 -> this.answer1;
			case 2 -> this.answer2;
			case 3 -> this.answer3;
			default ->
					throw new IllegalArgumentException(
							"This number of answers should be 0 < n < 4");
		};

		return current.getTitle() + " "
				+ df.format(((double) current.getConsumptionInWh())
				/ ((double) questionActivity.getConsumptionInWh()))
				+ " times";
	}

	/**
	 * Method used for creating a String with the activity so that it acts as if correct one
	 * @param numberOfAnswer the number of the answer given
	 * @return the original coefficient of the answer as if it was correct
	 */
	private double correctAnswerCoefficient(int numberOfAnswer) {
		return switch (numberOfAnswer) {
			case 1 -> this.realCoefficient1;
			case 2 -> this.realCoefficient2;
			case 3 -> this.realCoefficient3;
			default -> throw new IllegalArgumentException(
					"This number of answers should be 0 < n < 4");
		};
	}

	//incorrect
	public Activity correctAnswer() {
		double distanceFromOneOfCoefficient1 = 1 - this.realCoefficient1;
		double distanceFromOneOfCoefficient2 = 1 - this.realCoefficient2;
		double distanceFromOneOfCoefficient3 = 1 - this.realCoefficient3;
		double result = Math.min(
				distanceFromOneOfCoefficient1,
				Math.min(
						distanceFromOneOfCoefficient2,
						distanceFromOneOfCoefficient3
				)
		);

		if (result == distanceFromOneOfCoefficient1) {
			return this.answer1;
		} else if (result == distanceFromOneOfCoefficient2) {
			return this.answer2;
		}
		return this.answer3;
	}

	public long returnEnergyConsumption(String title) {
		if (title.equals(this.answer1.getTitle())) {
			return this.answer1.getConsumptionInWh();
		} else if (title.equals(answer2.getTitle())) {
			return this.answer2.getConsumptionInWh();
		}
		return this.answer3.getConsumptionInWh();
	}

	/**
	 * Calculates the points earned depending on the answer given
	 * @param maxPoints the maximal amount of points possible
	 * @param answerGiven the answer given by the user
	 * @param progress time left
	 * @return The total amount of points earned as an integer
	 */
	public int pointsEarned(int maxPoints, long answerGiven, double progress) {
		Activity answerActivity;
		switch ((int) answerGiven) {
			case 1 -> answerActivity = answer1;
			case 2 -> answerActivity = answer2;
			case 3 -> answerActivity = answer3;
			default -> throw new IllegalArgumentException("Answer given can be either 1, 2 or 3");
		}
		double currentCoefficient = ((double) answerActivity.getConsumptionInWh())
				/ ((double) questionActivity.getConsumptionInWh());
		if (Double.compare(currentCoefficient, correctAnswerCoefficient((int) answerGiven)) != 0) {
			return 0;
		}
		float pointsEarned = (float) progress * maxPoints;
		return Math.round(pointsEarned);
	}

	/**
	 * Getter for the question activity
	 * @return the activity related to the question
	 */
	public Activity getQuestionActivity() {
		return this.questionActivity;
	}

	/**
	 * Getter for the answer activity at position 1
	 * @return the answer activity at position 1
	 */
	public Activity getAnswer1() {
		return this.answer1;
	}

	/**
	 * Getter for the real coefficient at position 1
	 * @return the original coefficient with respect to the question
	 */
	public double getRealCoefficient1() {
		return this.realCoefficient1;
	}

	/**
	 * Getter for the answer activity at position 2
	 * @return the answer activity at position 2
	 */
	public Activity getAnswer2() {
		return this.answer2;
	}

	/**
	 * Getter for the real coefficient at position 2
	 * @return the original coefficient with respect to the question
	 */
	public double getRealCoefficient2() {
		return this.realCoefficient2;
	}

	/**
	 * Getter for the answer activity at position 3
	 * @return the answer activity at position 3
	 */
	public Activity getAnswer3() {
		return this.answer3;
	}

	/**
	 * Getter for the real coefficient at position 3
	 * @return the original coefficient with respect to the question
	 */
	public double getRealCoefficient3() {
		return this.realCoefficient3;
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		InsteadOfQuestion that = (InsteadOfQuestion) o;
		return Double.compare(that.realCoefficient1, this.realCoefficient1) == 0
				&& Double.compare(that.realCoefficient2, this.realCoefficient2) == 0
				&& Double.compare(that.realCoefficient3, this.realCoefficient3) == 0
				&& this.questionActivity.equals(that.questionActivity)
				&& this.answer1.equals(that.answer1) && this.answer2.equals(that.answer2)
				&& this.answer3.equals(that.answer3);
	}

	/**
	 * Hashing function for the InsteadOfQuestion class
	 * @return the hash code of the object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.questionActivity, this.answer1, this.realCoefficient1,
				this.answer2, this.realCoefficient2, this.answer3, this.realCoefficient3);
	}

	/**
	 * To String method for an InsteadOfQuestion
	 * @return the object in the form of human-readable string format
	 */
	@Override
	public String toString() {
		return "InsteadOfQuestion{" +
				"questionActivity=" + this.questionActivity +
				", answer1=" + this.answer1 +
				", realCoefficient1=" + this.realCoefficient1 +
				", answer2=" + this.answer2 +
				", realCoefficient2=" + this.realCoefficient2 +
				", answer3=" + this.answer3 +
				", realCoefficient3=" + this.realCoefficient3 +
				'}';
	}
}