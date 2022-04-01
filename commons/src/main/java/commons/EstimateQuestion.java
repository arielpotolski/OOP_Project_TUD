package commons;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes.Type(value = EstimateQuestion.class, name = "EstimateQuestion")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstimateQuestion extends Question {
	@JsonProperty("activity")
	private Activity activity;

	/**
	 * Constructor of the class.
	 * @param activity Activity that will be used in the question.
	 */
	public EstimateQuestion(Activity activity) {
		this.activity = activity;
	}

	/**
	 * This empty constructor helps for create the Json Object.
	 */
	public EstimateQuestion() {}

	/**
	 * Getter for the activity's title.
	 * @return The title of the activity in this question.
	 */
	@JsonProperty("activity_title")
	public String getActivityTitle() {
		return this.activity.getTitle();
	}

	/**
	 * Getter for the activity's image path.
	 * @return The image path of the activity in this question.
	 */
	@JsonProperty("activity_image_path")
	public String getActivityImagePath() {
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
	 * Calculates the total points earned by the player in this question.  It considers both the
	 * time took to answer and how close the given answer is to the actual one.  It gives more
	 * importance, however, to being close to the actual answer.
	 * @param maxPoints Maximum of points the player can earn.
	 * @param answerGiven Answer given by the player.
	 * @param progress Time left.
	 * @param doublePoints If the joker has been used.
	 * @return The amount of points player have earned in the question.
	 */
	public int pointsEarned(
		int maxPoints,
		long answerGiven,
		double progress,
		boolean doublePoints
	) {
		double t = this.activity.getConsumptionInWh() / (double) answerGiven;
		double partialPoints = Math.abs(Math.log10(t));
		int factor = doublePoints ? 2 : 1;
		return (int) Math.round(factor * 1000 * progress / (partialPoints + 1));
	}


	/**
	 * Compares this question with a given object and determines if they are equal or not.
	 * @param o Object to compare this question to.
	 * @return True if the objects have the same content and are of the same type; false otherwise.
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

	/**
	 * Getter method for activity.
	 * @return The activity that is enquired about.
	 */
	public Activity getActivity() {
		return this.activity;
	}
}