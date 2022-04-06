package server.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commons.Activity;
import static server.CustomAssertions.assertResponseEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class QuestionSetControllerTest {
	private QuestionSetController qsc = new QuestionSetController(new DummyActivityRepository());

	/**
	 * Check that we get 20 questions even when there are fewer activities.
	 */
	@Test
	void getQuestionsTest() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));

		assertEquals(this.qsc.getQuestions(0).size(), 20);
	}

	/**
	 * Tests whether two identical seeds give the same result.
	 * @throws IOException
	 */
	@Test
	void getQuestionsSameSeed() throws IOException {
		this.qsc.addActivities(List.of(
				new Activity("42", "42", 42, "42", "42"),
				new Activity("69", "69", 69, "69", "69")
		));

		assertEquals(this.qsc.getQuestions(404), this.qsc.getQuestions(404));
	}

	/**
	 * Tests whether two different seeds give the same results.
	 * @throws IOException
	 */
	@Test
	void getQuestionsSameSeedMoreActivities() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69"),
			new Activity("420", "420", 420, "420", "420"),
			new Activity("701034", "701034", 701034, "701034", "701034")
		));

		assertEquals(this.qsc.getQuestions(404), this.qsc.getQuestions(404));
	}

	/**
	 * Tests whether two different seeds give different results.
	 * @throws IOException
	 */
	@Test
	void getQuestionsDifferentSeeds() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69"),
			new Activity("420", "420", 420, "420", "420"),
			new Activity("701034", "701034", 701034, "701034", "701034")
		));

		assertNotEquals(this.qsc.getQuestions(404), this.qsc.getQuestions(1));
	}

	/**
	 * Assert that adding a null list of activities results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesNull() throws IOException {
		assertResponseEquals(HttpStatus.BAD_REQUEST, this.qsc.addActivities(null));
	}

	/**
	 * Assert that adding an empty list of activities results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesEmpty() throws IOException {
		assertResponseEquals(HttpStatus.BAD_REQUEST, this.qsc.addActivities(new ArrayList<>()));
	}

	/**
	 * Assert that adding a list with an activity containing an invalid ID results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidActivityId() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity(null, "42", 42, "42", "42")))
			)
		);
	}

	/**
	 * Assert that adding a list with an activity containing an invalid title results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidActivityTitle() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity("42", null, 42, "42", "42")))
			)
		);
	}

	/**
	 * Assert that adding a list with an activity containing an invalid source results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidActivitySource() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity("42", "42", 42, "42", null)))
			)
		);
	}

	/**
	 * Assert that adding a list with an activity containing an invalid consumption results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidActivityConsumption() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity("42", "42", -1, "42", "42")))
			)
		);
	}

	/**
	 * Assert that adding a list with a valid activity results in a 200 OK.
	 */
	@Test
	void addActivitiesValid() throws IOException {
		assertResponseEquals(
			HttpStatus.OK,
			this.qsc.addActivities(
				new ArrayList<>(List.of(new Activity("42", "42", 42, "42", "42")))
			)
		);
	}

	/**
	 * Assert that adding a list with multiple valid activities results in a 200 OK.
	 */
	@Test
	void addActivitiesValidMultiple() throws IOException {
		assertResponseEquals(
			HttpStatus.OK,
			this.qsc.addActivities(
				new ArrayList<>(List.of(
					new Activity("42", "42", 42, "42", "42"),
					new Activity("69", "69", 69, "69", "69")
				))
			)
		);
	}

	/**
	 * Assert that adding a list with multiple valid AND invalid activities results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivitiesInvalidMultiple() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivities(
				new ArrayList<>(List.of(
					new Activity("42", "42", 42, "42", "42"),
					new Activity("", "", -1, "", "")
				))
			)
		);
	}

	/**
	 * Assert that adding a null results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityNull() throws IOException {
		assertResponseEquals(HttpStatus.BAD_REQUEST, this.qsc.addActivity(null));
	}

	/**
	 * Assert that adding a zeroed activity results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidZero() throws IOException {
		assertResponseEquals(HttpStatus.BAD_REQUEST, this.qsc.addActivity(new Activity()));
	}

	/**
	 * Assert that adding an activity containing an invalid ID results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidId() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivity(new Activity(null, "42", 42, "42", "42"))
		);
	}

	/**
	 * Assert that adding an activity containing an invalid title results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidTitle() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivity(new Activity("42", null, 42, "42", "42"))
		);
	}

	/**
	 * Assert that adding an activity containing an invalid source results in a 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidSource() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivity(new Activity("42", "42", 42, "42", null))
		);
	}

	/**
	 * Assert that adding an activity containing an invalid consumption results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void addActivityInvalidConsumption() throws IOException {
		assertResponseEquals(
			HttpStatus.BAD_REQUEST,
			this.qsc.addActivity(new Activity("42", "42", -1, "42", "42"))
		);
	}

	/**
	 * Assert that adding a valid activity results in a 200 OK.
	 */
	@Test
	void addActivityValid() throws IOException {
		assertResponseEquals(
			HttpStatus.OK,
			this.qsc.addActivity(new Activity("42", "42", 42, "42", "42"))
		);
	}

	/**
	 * Assert that deleting an activity by ID when the activity actually exists results in a 200 OK
	 * and removes the activity.  To check that the activity was removed, we just check the size of
	 * the return value of `getActivities()'.
	 */
	@Test
	void deleteByIdValid() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertEquals(this.qsc.getActivities().size(), 2);
		assertResponseEquals(HttpStatus.OK, this.qsc.deleteById("69"));
		assertEquals(this.qsc.getActivities().size(), 1);
	}

	/**
	 * Assert that deleting an activity by ID when the activity doesn't exist results in a
	 * 400 BAD REQUEST.
	 */
	@Test
	void deleteByIdInvalid() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertEquals(this.qsc.getActivities().size(), 2);
		assertResponseEquals(HttpStatus.BAD_REQUEST, this.qsc.deleteById("1337"));
		assertEquals(this.qsc.getActivities().size(), 2);
	}

	/**
	 * Assert that deleting an activity by ID when it is null results in a 400 BAD REQUEST.
	 */
	@Test
	void deleteByIdNull() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertEquals(this.qsc.getActivities().size(), 2);
		assertResponseEquals(HttpStatus.BAD_REQUEST, this.qsc.deleteById(null));
		assertEquals(this.qsc.getActivities().size(), 2);
	}

	/**
	 * Assert that getting an activity by ID when it is null results in a 400 BAD REQUEST.
	 */
	@Test
	void getByIdNull() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertResponseEquals(HttpStatus.BAD_REQUEST, this.qsc.getById(null));
	}

	/**
	 * Assert that getting an activity by ID when it doesn't exist results in a 400 BAD REQUEST.
	 */
	@Test
	void getByIdInvalid() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertResponseEquals(HttpStatus.BAD_REQUEST, this.qsc.getById("1337"));
	}

	/**
	 * Assert that getting an activity by ID when it exists results in a 200 OK.
	 */
	@Test
	void getByIdNullValid() throws IOException {
		this.qsc.addActivities(List.of(
			new Activity("42", "42", 42, "42", "42"),
			new Activity("69", "69", 69, "69", "69")
		));
		assertResponseEquals(HttpStatus.OK, this.qsc.getById("69"));
	}
}