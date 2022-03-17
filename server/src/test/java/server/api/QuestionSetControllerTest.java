package server.api;

import java.util.ArrayList;
import java.util.List;

import commons.Activity;
import static server.CustomAssertions.assertResponseEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionSetControllerTest {
	private QuestionSetController qsc = new QuestionSetController(new ActivityRepositoryTest());

	@Test
	void getAll() {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));

		/* As far as I am aware, this is the only real kind of test you can perform.  Ideally the
		 * question types will be random every time, and because each question type has its own
		 * unique fields and behaviors (some even have multiple activities) it becomes incredibly
		 * annoying to properly test this method.  Since this method is so simple, I don't think it
		 * deserves to be tested that extensively.
		 */
		assertEquals(this.qsc.getAll().size(), 2);
	}

	/**
	 * Assert that adding a null list of activities results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesNull() {
		assertResponseEquals(this.qsc.addActivities(null), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Assert that adding an empty list of activities results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesEmpty() {
		assertResponseEquals(this.qsc.addActivities(new ArrayList<>()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Assert that adding a list with an activity containing an invalid ID results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidActivityId() {
		assertResponseEquals(
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity(null, "42", 42, "42", "42")))
			),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding a list with an activity containing an invalid title results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidActivityTitle() {
		assertResponseEquals(
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity("42", null, 42, "42", "42")))
			),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding a list with an activity containing an invalid source results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidActivitySource() {
		assertResponseEquals(
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity("42", "42", 42, "42", null)))
			),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding a list with an activity containing an invalid consumption results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidActivityConsumption() {
		assertResponseEquals(
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity("42", "42", -1, "42", "42")))
			),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding a list with a valid activity results in a 200 OK.
	 */
	@Test
	void addActivitiesValid() {
		assertResponseEquals(
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity("42", "42", 42, "42", "42")))
			),
			HttpStatus.OK
		);
	}

	/**
	 * Assert that adding a list with multiple valid activities results in a 200 OK.
	 */
	@Test
	void addActivitiesValidMultiple() {
		assertResponseEquals(
			this.qsc.addActivities(
				new ArrayList<>(List.of(
					new Activity("42", "42", 42, "42", "42"),
					new Activity("69", "69", 69, "69", "69")
				))
			),
			HttpStatus.OK
		);
	}

	/**
	 * Assert that adding a list with multiple valid AND invalid activities results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidMultiple() {
		assertResponseEquals(
			this.qsc.addActivities(
				new ArrayList<>(List.of(
					new Activity("42", "42", 42, "42", "42"),
					new Activity("", "", -1, "", "")
				))
			),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding a null results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityNull() {
		assertResponseEquals(this.qsc.addActivity(null), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Assert that adding a zeroed activity results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidZero() {
		assertResponseEquals(this.qsc.addActivity(new Activity()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Assert that adding an activity containing an invalid ID results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidId() {
		assertResponseEquals(
			this.qsc.addActivity(new Activity(null, "42", 42, "42", "42")),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding an activity containing an invalid title results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidTitle() {
		assertResponseEquals(
			this.qsc.addActivity(new Activity("42", null, 42, "42", "42")),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding an activity containing an invalid source results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidSource() {
		assertResponseEquals(
			this.qsc.addActivity(new Activity("42", "42", 42, "42", null)),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding an activity containing an invalid consumption results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidConsumption() {
		assertResponseEquals(
			this.qsc.addActivity(new Activity("42", "42", -1, "42", "42")),
			HttpStatus.BAD_REQUEST
		);
	}

	/**
	 * Assert that adding a valid activity results in a 200 OK.
	 */
	@Test
	void addActivityValid() {
		assertResponseEquals(
			this.qsc.addActivity(new Activity("42", "42", 42, "42", "42")),
			HttpStatus.OK
		);
	}

	/**
	 * Assert that deleting an activity by ID when the activity actually exists results in a 200 OK
	 * and removes the activity.  To check that the activity was removed, we just check the size of
	 * the return value of `getAll()' as there is no method to get a list of activities.  Also, we
	 * can't check the individual activities for the reasons laid out in the comment in the test
	 * for `getAll()' at the top of this file.
	 */
	@Test
	void deleteByIdValid() {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertEquals(this.qsc.getAll().size(), 2);
		assertResponseEquals(this.qsc.deleteById("69"), HttpStatus.OK);
		assertEquals(this.qsc.getAll().size(), 1);
	}

	/**
	 * Assert that deleting an activity by ID when the activity doesn't exist results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void deleteByIdInvalid() {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertEquals(this.qsc.getAll().size(), 2);
		assertResponseEquals(this.qsc.deleteById("1337"), HttpStatus.BAD_REQUEST);
		assertEquals(this.qsc.getAll().size(), 2);
	}

	/**
	 * Assert that deleting an activity by ID when it is null results in a 400 BAD REQUEST.
	 */
	@Test
	void deleteByIdNull() {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertEquals(this.qsc.getAll().size(), 2);
		assertResponseEquals(this.qsc.deleteById(null), HttpStatus.BAD_REQUEST);
		assertEquals(this.qsc.getAll().size(), 2);
	}

	/**
	 * Assert that getting an activity by ID when it is null results in a 400 BAD REQUEST.
	 */
	@Test
	void getByIdNull() {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertResponseEquals(this.qsc.getById(null), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Assert that getting an activity by ID when it doesn't exist results in a 400 BAD REQUEST.
	 */
	@Test
	void getByIdInvalid() {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertResponseEquals(this.qsc.getById("1337"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Assert that getting an activity by ID when it exists results in a 200 OK.
	 */
	@Test
	void getByIdNullValid() {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertResponseEquals(this.qsc.getById("69"), HttpStatus.OK);
	}
}