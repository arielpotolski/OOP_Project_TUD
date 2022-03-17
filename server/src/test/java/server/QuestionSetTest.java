package server;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuestionSetTest {

	private Activity activity;
	private List<Activity> activities;
	private QuestionSet qs;

	@BeforeEach
	void setup() {
		this.activity = new Activity("123", "act1", 1000,
				"pathpng1", "first site");
		this.activities = new ArrayList<>();
		this.activities.add(this.activity);
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
		qs.fillSet(5);
		assertEquals(5, qs.getQuestionSetSize());
	}

	@Test
	public void generateSequenceTest() {
		int numOfI = 0;

		List<Character> characters = qs.generateSequence(100);
		for (char c : characters) {
			if (c == 'I') {
				++numOfI;
			}
		}

		assertEquals(20, numOfI);
	}
}
