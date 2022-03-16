package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import commons.Activity;
import commons.EstimateQuestion;
import commons.HighestConsumptionQuestion;
import commons.InsteadOfQuestion;
import commons.MCQuestion;
import commons.Question;

public class QuestionSet {
	private List<Question> questions;
	private List<Activity> activities;
	private Random random;
	private int size;

	// FIXME: This is unsafe.  Rewrite it in rust?
	public List<Question> getQuestions() {
		return questions;
	}

	/**
	 * Constructor for the QuestionsSet
	 *
	 * @param activities list of activities
	 */
	public QuestionSet(List<Activity> activities) {
		this.questions = new ArrayList<>();
		this.random = new Random();
		this.activities = activities;
		this.size = activities.size();
	}

	/**
	 * Fills all the container class with questions
	 * @param numberOfQuestions the number of questions contained in the class
	 */
	public void fillSet(int numberOfQuestions) {
		generateSequence(numberOfQuestions).forEach(c -> {
			switch (c) {
			case 'M' -> generateMCQ();
			case 'H' -> generateHigh();
			case 'E' -> generateEstimate();
			case 'I' -> generateInstead();
			default -> throw new IllegalArgumentException(
				"Invalid question type, expected [MHEI] but got '" + c + "'");
			}
		});
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

		var a = Stream.generate(() -> 'M').limit(numberOfMCQ);
		var b = Stream.generate(() -> 'E').limit(numberOfEst);
		var c = Stream.generate(() -> 'H').limit(numberOfHigh);
		var d = Stream.generate(() -> 'I').limit(numberOfInstead);
		return Stream.concat(a, Stream.concat(b, Stream.concat(c, d))).toList();
	}
}