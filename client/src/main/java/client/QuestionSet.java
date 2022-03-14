package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import commons.Activity;
import commons.EstimateQuestion;
import commons.HighestConsumptionQuestion;
import commons.InsteadOfQuestion;
import commons.MCQuestion;
import commons.Question;

public class QuestionSet {

	private List<Question> questions;
	List<Activity> activities;
	Random random;
	int size;

	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 *
	 *
	 * @param activities
	 */
	public QuestionSet(List<Activity> activities) {
		this.questions = new ArrayList<>();
		random = new Random();
		this.activities = activities;
		size = activities.size();
	}

	/**
	 * Fills all the container class with questions
	 * @param numberOfQuestions the number of questions contained in the class
	 */
	public void fillSet(int numberOfQuestions){
		List<Character> sequence = generateSequence(numberOfQuestions);

		for (Character c : sequence) {
			if (c == 'M') generateMCQ();
			if (c == 'H') generateHigh();
			if (c == 'E') generateEstimate();
			if (c == 'I') generateInstead();
		}
	}

	/**
	 * Generates a random Multiple Choice Question
	 */
	private void generateMCQ() {
		Activity activity = activities.get(random.nextInt(size));
		this.questions.add(new MCQuestion(activity));
	}

	/**
	 * Generates the Highest Consumption Question
	 */
	private void generateHigh() {
		Activity activity = activities.get(random.nextInt(size));
		Activity activity1 = activities.get(random.nextInt(size));
		Activity activity2 = activities.get(random.nextInt(size));
		this.questions.add(new HighestConsumptionQuestion(activity, activity1, activity2));
	}

	/**
	 * Generates a Estimate Question
	 */
	private void generateEstimate() {
		Activity activity = activities.get(random.nextInt(size));
		this.questions.add(new EstimateQuestion(activity));
	}

	/**
	 * Generates Instead of Question
	 */
	private void generateInstead() {
		Activity activity = activities.get(random.nextInt(size));
		Activity activity1 = activities.get(random.nextInt(size));
		Activity activity2 = activities.get(random.nextInt(size));
		Activity activity3 = activities.get(random.nextInt(size));
		this.questions.add(new InsteadOfQuestion(activity, activity1, activity2, activity3));
	}

	/**
	 * Generates a random sequence of characters representing the order of questions in the class
	 * @param numberOfQuestions the number of characters in the sequence
	 * @return a random generated sequence of however many symbols one wants
	 */
	public List<Character> generateSequence(int numberOfQuestions) {
		int numberOfMCQ = ((Long) Math.round(numberOfQuestions * 0.35)).intValue();
		int numberOfEst = ((Long) Math.round(numberOfQuestions * 0.15)).intValue();
		int numberOfHigh = ((Long) Math.round(numberOfQuestions * 0.30)).intValue();
		int numberOfInstead = numberOfQuestions - numberOfEst - numberOfMCQ - numberOfHigh;

		List<Character> result = new ArrayList<>();

		for (int i = 0; i < numberOfMCQ; i++) {
			result.add('M');
		}

		for (int i = 0; i < numberOfEst; i++) {
			result.add('E');
		}

		for (int i = 0; i < numberOfHigh; i++) {
			result.add('H');
		}

		for (int i = 0; i < numberOfInstead; i++) {
			result.add('I');
		}

		return result;
	}
}