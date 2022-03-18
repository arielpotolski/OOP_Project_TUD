package commons;

import java.io.IOException;
import java.util.Objects;

public class HighestConsumptionQuestion extends Question {
	private Activity choice1;
	private Activity choice2;
	private Activity choice3;

	/**
	 * constructor of the class.
	 * @param choice1 first activity.
	 * @param choice2 second activity.
	 * @param choice3 third activity.
	 */
	public HighestConsumptionQuestion(Activity choice1, Activity choice2, Activity choice3) {
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.choice3 = choice3;
	}

	/**
	 * gets the title of the first activity.
	 * @return title of the first activity.
	 */
	public String getActivity1Title() {
		return this.choice1.getTitle();
	}

	/**
	 * gets the title of the second activity.
	 * @return title of the second activity.
	 */
	public String getActivity2Title() {
		return this.choice2.getTitle();
	}

	/**
	 * gets the title of the third activity.
	 * @return the title of the third activity.
	 */
	public String getActivity3Title() {
		return this.choice3.getTitle();
	}

	/**
	 * gets the imagePath of the first activity.
	 * @return the imagePath of the first activity.
	 */
	public String getActivity1ImagePath() {
		return this.choice1.getImagePath();
	}

	/**
	 * Useful for sending the information about a picture to the user
	 * @return a byte array with information about the image for choice 1
	 * @throws IOException if there is something wrong with the file
	 */
	public byte[] imageInByteArrayActivity1() throws IOException {
		return this.choice1.castImageToByteArray();
	}

	/**
	 * gets the imagePath of the second activity.
	 * @return the imagePath of the second activity.
	 */
	public String getActivity2ImagePath() {
		return this.choice2.getImagePath();
	}

	/**
	 * Useful for sending the information about a picture to the user
	 * @return a byte array with information about the image for choice 2
	 * @throws IOException if there is something wrong with the file
	 */
	public byte[] imageInByteArrayActivity2() throws IOException {
		return this.choice2.castImageToByteArray();
	}

	/**
	 * gets the imagePath of the third activity.
	 * @return the imagePath of the third activity.
	 */
	public String getActivity3ImagePath() {
		return this.choice3.getImagePath();
	}

	/**
	 * Useful for sending the information about a picture to the user
	 * @return a byte array with information about the image for choice 3
	 * @throws IOException if there is something wrong with the file
	 */
	public byte[] imageInByteArrayActivity3() throws IOException {
		return this.choice1.castImageToByteArray();
	}

	/**
	 * Returns the amount of points players have earned for this question.
	 * It compares the selected answer by the player (its title)
	 * to the title of the correct answer, and see if it matches.
	 * @param maxPoints maximum of points that is possible to earn.
	 * @param answerGivenConsumption title of the option selected by the user.
	 * @param progress time left
	 * @return the amount of points user earned for this question.
	 */
	public int pointsEarned(int maxPoints, long answerGivenConsumption, double progress) {
		long maxConsumption;
		int positionHighest;
		maxConsumption = Math.max(
			this.choice1.getConsumptionInWh(),
			Math.max(
				this.choice2.getConsumptionInWh(),
				this.choice3.getConsumptionInWh()
			)
		);

		if (choice1.getConsumptionInWh() == maxConsumption) {
			positionHighest = 1;
		} else if (choice2.getConsumptionInWh() == maxConsumption) {
			positionHighest = 2;
		} else {
			positionHighest = 3;
		}

		if (answerGivenConsumption != positionHighest)
			return 0;
		return (int) Math.round(maxPoints * progress);
	}

	/**
	 * Getter for the correct answer.
	 *
	 * @return the activity which has the highest consumption.
	 */
	public Activity getCorrectAnswer() {
		long maxConsumption = Math.max(
			this.choice1.getConsumptionInWh(),
			Math.max(
				this.choice2.getConsumptionInWh(),
				this.choice3.getConsumptionInWh()
			)
		);

		if (this.choice1.getConsumptionInWh() == maxConsumption) {
			return this.choice1;
		} else if (this.choice2.getConsumptionInWh() == maxConsumption) {
			return this.choice2;
		}
		return this.choice3;
	}

	public long returnEnergyConsumption(String title) {
		if (title.equals(this.choice1.getTitle())) {
			return this.choice1.getConsumptionInWh();
		} else if (title.equals(this.choice2.getTitle())) {
			return this.choice2.getConsumptionInWh();
		}
		return this.choice3.getConsumptionInWh();
	}

	/**
	 * compares this HighestConsumptionQuestion to another one.
	 * @param o the object for comparison.
	 * @return true if this question is equal to o, and o is also a HighestConsumptionQuestion.
	 *  Returns false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		HighestConsumptionQuestion that = (HighestConsumptionQuestion) o;
		return this.choice1.equals(that.choice1) && this.choice2.equals(that.choice2)
				&& this.choice3.equals(that.choice3);
	}

	/**
	 * Generates a hashcode for this question.
	 * @return the hashcode that has been generated.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.choice1, this.choice2, this.choice3);
	}
}