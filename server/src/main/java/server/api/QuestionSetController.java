package server.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import commons.Activity;
import commons.Question;
import server.QuestionSet;
import server.database.ActivityRepository;
import static commons.Utility.nullOrEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionSetController {
	private final ActivityRepository repository;

	public QuestionSetController(ActivityRepository repository) {
		this.repository = repository;
	}

	/**
	 * This method will get a list of activities and transform it into a list of questions.
	 * These questions are then sent to the client in the main controller.
	 *
	 * @param seed The seed which dictates the order of the questions.
	 * @return The list of returned questions.
	 */
	@GetMapping(path = {"", "/"})
	public List<Question> getQuestions(@RequestParam long seed) {
		List<Activity> as = this.repository.findAll();
		QuestionSet qs = new QuestionSet(as, seed);
		qs.fillSet(20);
		return qs.getQuestions();
	}

	/**
	 * Get a list of all the activities on the server.
	 * @return A list of all the servers activities.
	 */
	@GetMapping("/getActivities")
	public List<Activity> getActivities() {
		return this.repository.findAll();
	}
	
	@PutMapping("/addActivities")
	public ResponseEntity<List<Activity>> addActivities(@RequestBody List<Activity> activities)
			throws IOException {
		if (
			nullOrEmpty(activities)
			|| !activities.parallelStream().allMatch(a -> a != null && a.isValid())
		) {
			return ResponseEntity.badRequest().build();
		}
		List<Activity> res = new ArrayList<>();
		for (Activity activity : activities) {
			res.add(new Activity(
				activity.getId(),
				activity.getTitle(),
				activity.getConsumptionInWh(),
				activity.getImagePath(),
				activity.getSource()
			));
		}
		List<Activity> result = this.repository.saveAll(res);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/addActivity")
	public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) throws IOException {
		if (activity == null || !activity.isValid()) {
			return ResponseEntity.badRequest().build();
		}
		Activity res = new Activity(
			activity.getId(),
			activity.getTitle(),
			activity.getConsumptionInWh(),
			activity.getImagePath(),
			activity.getSource()
		);
		Activity result = this.repository.save(res);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Activity> deleteById(@PathVariable("id") String id) {
		if (nullOrEmpty(id) || !this.repository.existsById(id)) {
			return ResponseEntity.badRequest().build();
		}
		Activity activity = this.repository.findById(id).get();
		this.repository.deleteById(id);

		return ResponseEntity.ok(activity);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Activity> getById(@PathVariable("id") String id) {
		if (nullOrEmpty(id) || !this.repository.existsById(id)) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(this.repository.findById(id).get());
	}
}