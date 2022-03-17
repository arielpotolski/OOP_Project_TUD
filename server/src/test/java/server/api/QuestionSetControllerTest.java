package server.api;

import java.util.ArrayList;

import commons.Activity;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionSetControllerTest {
	private ResponseEntity badResponse = ResponseEntity.badRequest().build();
	private ResponseEntity goodResponse = ResponseEntity.ok().build();
	private QuestionSetController qsc = new QuestionSetController(new ActivityRepositoryTest());

	@Test
	void getAll() {}

	@Test
	void addActivities() {
		Activity a = new Activity();
		Activity b = new Activity();
		ArrayList<Activity> xs = new ArrayList<>();

		// Test with null
		assertEquals(this.qsc.addActivities(null), this.badResponse);

		// Test with the empty list
		assertEquals(this.qsc.addActivities(xs), this.badResponse);
		xs.add(a);

		// Test with a list containing an invalid activity
		assertEquals(this.qsc.addActivities(xs), this.badResponse);
		a.setId("42");
		assertEquals(this.qsc.addActivities(xs), this.badResponse);
		a.setSource("42");
		assertEquals(this.qsc.addActivities(xs), this.badResponse);

		// The activity is now valid
		a.setTitle("42");
		assertEquals(this.qsc.addActivities(xs), this.goodResponse);

		// And now it's not valid again
		a.setConsumptionInWh(-1);
		assertEquals(this.qsc.addActivities(xs), this.badResponse);

		// Test with multiple elements in the list
		xs.add(a);
		assertEquals(this.qsc.addActivities(xs), this.badResponse);
		a.setConsumptionInWh(0);
		assertEquals(this.qsc.addActivities(xs), this.goodResponse);
		xs.add(b);
		b.setId("69");
		b.setTitle("69");
		b.setSource("69");
		b.setConsumptionInWh(69);
		assertEquals(this.qsc.addActivities(xs), this.goodResponse);
	}

	@Test
	void addActivity() {}

	@Test
	void deleteById() {}

	@Test
	void getById() {}

	@Test
	void create() {}
}