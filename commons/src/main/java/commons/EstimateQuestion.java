package commons;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeName("EstimateQuestion")
public class EstimateQuestion extends Question {
	private Activity activity;

	/**
	 * Constructor of the class.
	 * @param activity activity that will be used in the question.
	 */
	public EstimateQuestion(Activity activity) {
		this.activity = activity;
	}

	/**
	 * Getter for the activity's title.
	 * @return the title of the activity in this question.
	 */
	public String getActivityTitle() {
		return this.activity.getTitle();
	}

	/**
	 * Getter for the activity's image path.
	 * @return the image path of the activity in this question.
	 */
	public String getActivityImagePath() {
		return this.activity.getImagePath();
	}

	/**
	 * Calculates the total points earned by the player in this question.
	 * It considers both the time took to answer and how close the given
	 * answer is to the actual one. It gives more importance, however,
	 * to being close to the actual answer.
	 * @param maxPoints maximum of points the player can earn.
	 * @param answerGiven answer given by the player.
	 * @param progress time left
	 * @return the amount of points player have earned in the question.
	 */
	public int pointsEarned(int maxPoints, long answerGiven, double progress) {
		double t = ((double) this.activity.getConsumptionInWh()) / ((double) answerGiven);
		double partialPoints = Math.abs(Math.log10(t));
		return (int) Math.round(1000 * progress / (partialPoints + 1));
	}


	/**
	 * Compares this question with a given object and
	 * determines if they are equal or not.
	 * @param o object to compare this question to.
	 * @return true if the objects have the same content and are of the same type;
	 * false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EstimateQuestion that = (EstimateQuestion) o;
		return this.activity.equals(that.activity);
	}

	/**
	 * Generates a HashCode for this Question.
	 * @return the HashCode generated.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.activity);
	}
}


