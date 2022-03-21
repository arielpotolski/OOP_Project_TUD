package server;

import java.util.ArrayList;
import java.util.List;

import commons.Activity;
import commons.InsteadOfQuestion;
import commons.Question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionSetTest {

	private Activity activity1;
	private Activity activity2;
	private Activity activity3;
	private List<Activity> activities;
	private QuestionSet qs;

	@BeforeEach
	private void setUp() {
		this.activity1 = new Activity("123", "act11", 1000,
				"pathpng1", "first site");
		this.activity2 = new Activity("123", "act12", 1000,
				"pathpng1", "first site");
		this.activity3 = new Activity("123", "act13", 1000,
				"pathpng1", "first site");
		this.activities = new ArrayList<>();
		this.activities.add(this.activity1);
		this.qs = new QuestionSet(activities);
	}

	@Test
	public void constructorTest() {
		assertNotNull(qs);
	}

	/**
	 * this method tests both the fillSet method and the getQuestionSetSize method.
	 */
	@Test
	public void fillSetTest() {
		qs.fillSet(2);
		assertEquals(2, qs.getQuestionSetSize());
	}

	@Test
	public void questionContentTest() {
		qs.fillSet(1);
		Question question = qs.getQuestions().get(0);
		assertTrue(question instanceof InsteadOfQuestion);

		assertEquals(activities.get(qs.getActivityNumbers().get(0)),
				((InsteadOfQuestion)(qs.getQuestions().get(0))).getAnswer1());
		assertEquals(activities.get(qs.getActivityNumbers().get(1)),
				((InsteadOfQuestion)(qs.getQuestions().get(0))).getAnswer2());
		assertEquals(activities.get(qs.getActivityNumbers().get(2)),
				((InsteadOfQuestion)(qs.getQuestions().get(0))).getAnswer3());
	}

	@Test
	public void generateSequenceTest() {
		int numOfI = 0;
		int numOfH = 0;
		int numOfE = 0;
		int numOfM = 0;

		List<Character> characters = qs.generateSequence(100);
		for (char c : characters) {
			switch (c) {
				case 'I' -> {
					++numOfI;
				}
				case 'H' -> {
					++numOfH;
				}
				case 'E' -> {
					++numOfE;
				}
				case 'M' -> {
					++numOfM;
				}
			}
		}

		assertEquals(20, numOfI);
		assertEquals(35, numOfM);
		assertEquals(15, numOfE);
		assertEquals(30, numOfH);
	}
}
