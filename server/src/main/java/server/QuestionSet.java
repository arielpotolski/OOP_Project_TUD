package server;

import java.util.ArrayList;
import java.util.Collections;
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
	private List<Question> questions; // List of the questions in this QuestionSet.
	private List<Activity> activities; // List of Activities to be used in the questions.
	private Random random; // Used to randomize the activities used per QuestionSet.
	private int size; // Size of the list of activities.
	private List<Integer> activityNumbers; // List containing the # of each activity
											// so we can test its content.

	/**
	 * Constructor for the QuestionsSet
	 *
	 * @param activities list of activities
	 * @param seed the seed with whom we generate the random order
	 */
	public QuestionSet(List<Activity> activities, long seed) {
		this.questions = new ArrayList<>();
		this.random = new Random(seed);
		this.activities = activities;
		this.size = activities.size();
		this.activityNumbers = new ArrayList<>();
	}

	/**
	 * Getter for the list of Questions in this question set.
	 * @return list of Questions.
	 */
	public List<Question> getQuestions() {
		return this.questions;
	}

	/**
	 * Getter for the size of the question set.
	 * @return the size of the question set.
	 */
	public int getQuestionSetSize() {
		return this.questions.size();
	}

	/**
	 * Getter for the list of the activity numbers.
	 * @return list of activity numbers.
	 */
	public List<Integer> getActivityNumbers() {
		return this.activityNumbers;
	}

	/**
	 * Fills all the container class with questions
	 * @param numberOfQuestions the number of questions contained in the class
	 */
	public void fillSet(int numberOfQuestions) {
		ArrayList<Character> seq = new ArrayList<>(generateSequence(numberOfQuestions));
		Collections.shuffle(seq, random);

		seq.forEach(c -> {
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
		int randNumber = this.random.nextInt(this.size);
		this.activityNumbers.add(randNumber);

		Activity activity = this.activities.get(randNumber);
		this.questions.add(new MCQuestion(activity));
	}

	/**
	 * Generates the Highest Consumption Question
	 */
	private void generateHigh() {
		int randNumber1 = this.random.nextInt(this.size);
		int randNumber2 = this.random.nextInt(this.size);
		int randNumber3 = this.random.nextInt(this.size);

		this.activityNumbers.add(randNumber1);
		this.activityNumbers.add(randNumber2);
		this.activityNumbers.add(randNumber3);

		Activity activity1 = this.activities.get(randNumber1);
		Activity activity2 = this.activities.get(randNumber2);
		Activity activity3 = this.activities.get(randNumber3);
		this.questions.add(new HighestConsumptionQuestion(activity1, activity2, activity3));
	}

	/**
	 * Generates a Estimate Question
	 */
	private void generateEstimate() {
		int randNumber = this.random.nextInt(this.size);
		this.activityNumbers.add(randNumber);

		Activity activity = this.activities.get(randNumber);
		this.questions.add(new EstimateQuestion(activity));
	}

	/**
	 * Generates Instead of Question
	 */
	private void generateInstead() {
		int randNumber1 = this.random.nextInt(this.size);
		int randNumber2 = this.random.nextInt(this.size);
		int randNumber3 = this.random.nextInt(this.size);
		int randNumber4 = this.random.nextInt(this.size);

		this.activityNumbers.add(randNumber1);
		this.activityNumbers.add(randNumber2);
		this.activityNumbers.add(randNumber3);
		this.activityNumbers.add(randNumber4);

		Activity activity1 = this.activities.get(randNumber1);
		Activity activity2 = this.activities.get(randNumber2);
		Activity activity3 = this.activities.get(randNumber3);
		Activity activity4 = this.activities.get(randNumber4);
		this.questions.add(new InsteadOfQuestion(activity1, activity2, activity3, activity4));
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