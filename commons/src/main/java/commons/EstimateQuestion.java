package commons;

import java.util.Objects;

public class EstimateQuestion extends Question {

	private Activity activity;

	/**
	 * Constructor of the class.
	 * @param activity activity that will be used in the question.
	 */
	public EstimateQuestion(Activity activity){
		this.activity = activity;
	}

	/**
	 * Getter for the activity's title.
	 * @return the title of the activity in this question.
	 */
	public String getActivityTitle(){
		return this.activity.getTitle();
	}

	/**
	 * Getter for the activity's image path.
	 * @return the image path of the activity in this question.
	 */
	public String getActivityImagePath(){
		return this.activity.getImagePath();
	}

	/**
	 * Calculates the total points earned by the player in this question.
	 * It considers both the time took to answer and how close the given
	 * answer is to the actual one. It gives more importance, however,
	 * to being close to the actual answer.
	 * @param maxPoints maximum of points the player can earn.
	 * @param answerGiven answer given by the player.
	 * @param timeToAnswer time the player spent to answer the question.
	 * @param totalTime time player had to answer the question.
	 * @return the amount of points player have earned in the question.
	 */
	public long pointsEarned(int maxPoints, int answerGiven, float timeToAnswer, float totalTime){
		double partialPoints; //points calculated from the difference (RA - AG)
		float leftTime = totalTime - timeToAnswer;
		double t = ((double) this.activity.getConsumptionInWh()) / ((double) answerGiven);
		partialPoints = Math.abs(Math.log10(t));

		return (Math.round((1 / (partialPoints + 1)) * 1000 * (leftTime / totalTime)));
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
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EstimateQuestion that = (EstimateQuestion) o;
		return activity.equals(that.activity);
	}

	/**
	 * Generates a HashCode for this Question.
	 * @return the HashCode generated.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activity);
	}
}
