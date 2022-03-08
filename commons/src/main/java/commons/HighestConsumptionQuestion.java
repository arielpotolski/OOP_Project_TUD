package commons;

import java.util.Objects;

public class HighestConsumptionQuestion {

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
	public String getActivity1Title(){
		return choice1.getTitle();
	}

	/**
	 * gets the title of the second activity.
	 * @return title of the second activity.
	 */
	public String getActivity2Title(){
		return choice2.getTitle();
	}

	/**
	 * gets the title of the third activity.
	 * @return the title of the third activity.
	 */
	public String getActivity3Title(){
		return choice3.getTitle();
	}

	/**
	 * gets the imagePath of the first activity.
	 * @return the imagePath of the first activity.
	 */
	public String getActivity1ImagePath(){
		return choice1.getImagePath();
	}

	/**
	 * gets the imagePath of the second activity.
	 * @return the imagePath of the second activity.
	 */
	public String getActivity2ImagePath(){
		return choice2.getImagePath();
	}

	/**
	 * gets the imagePath of the third activity.
	 * @return the imagePath of the third activity.
	 */
	public String getActivity3ImagePath(){
		return choice3.getImagePath();
	}

	/**
	 * Returns the amount of points players have earned for this question.
	 * It compares the selected answer by the player (its title)
	 * to the title of the correct answer, and see if it matches.
	 * @param maxPoints maximum of points that is possible to earn.
	 * @param answerGivenTitle title of the option selected by the user.
	 * @param timeForAnswering time user took to answer the question.
	 * @param totalTime total time user had to answer the question.
	 * @return the amount of points user earned for this question.
	 */
	protected int pointsEarned(int maxPoints, String answerGivenTitle, float timeForAnswering,
								float totalTime){

		int maxConsumption;
		Activity highestConsumptionAct;

		maxConsumption = (Math.max(this.choice1.getConsumptionInWh(),
				Math.max(this.choice2.getConsumptionInWh(), this.choice3.getConsumptionInWh())));

		if (choice1.getConsumptionInWh() == maxConsumption)
			highestConsumptionAct = choice1;
		else if (choice2.getConsumptionInWh() == maxConsumption)
			highestConsumptionAct = choice2;
		else
			highestConsumptionAct = choice3;


		if(!answerGivenTitle.equals(highestConsumptionAct.getTitle()))
			return 0;
		else
			return Math.round((maxPoints * (totalTime - timeForAnswering)) / totalTime);
	}

	/**
	 * compares this HighestConsumptionQuestion to another one.
	 * @param o the object for comparison.
	 * @return true if this question is equal to o, and o is also a HighestConsumptionQuestion.
	 *  Returns false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HighestConsumptionQuestion that = (HighestConsumptionQuestion) o;
		return choice1.equals(that.choice1) && choice2.equals(that.choice2)
				&& choice3.equals(that.choice3);
	}

	/**
	 * Generates a hashcode for this question.
	 * @return the hashcode that has been generated.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(choice1, choice2, choice3);
	}
}
