package server.api;

import java.util.List;

import commons.Activity;
import commons.Question;
import server.QuestionSet;
import server.database.ActivityRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionSetController {
	private final ActivityRepository repository;

	public QuestionSetController(ActivityRepository repository) {
		this.repository = repository;
	}

	private static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	/**
	 * This method will get a list of activities and transform it into a list of questions.
	 * These questions are then sent to the client in the main controller.
	 *
	 * @return The list of returned questions
	 */
	@GetMapping(path = {"", "/"})
	public List<Question> getAll() {
		List<Activity> as = this.repository.findAll();
		QuestionSet qs = new QuestionSet(as);
		qs.fillSet(as.size());
		return qs.getQuestions();
	}

	@PutMapping("/addActivity")
	public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) {
		if (
			activity == null
			|| activity.getId() == null
			|| activity.getSource() == null
			|| activity.getTitle() == null
			|| activity.getConsumptionInWh() < 0
		) {
			return ResponseEntity.badRequest().build();
		}
		Activity result = this.repository.save(activity);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Activity> deleteById(@PathVariable("id") String id) {
		if (isNullOrEmpty(id) || !this.repository.existsById(id)) {
			return ResponseEntity.badRequest().build();
		}
		Activity activity = this.repository.findById(id).get();
		this.repository.deleteById(id);

		return ResponseEntity.ok(activity);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Activity> getById(@PathVariable("id") String id) {
		if ("".equals(id) || !this.repository.existsById(id)) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(this.repository.findById(id).get());
	}

	@PostMapping(path = {"", "/"},
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Activity> create(@RequestBody Activity newActivity) {
		if (
			newActivity == null
			|| isNullOrEmpty(newActivity.getId())
			|| isNullOrEmpty(newActivity.getTitle())
			|| isNullOrEmpty(newActivity.getSource())
			|| isNullOrEmpty(newActivity.getImagePath())
			|| newActivity.getConsumptionInWh() < 0
		) {
			return ResponseEntity.badRequest().build();
		}
		Activity activity = this.repository.save(newActivity);
		return new ResponseEntity<>(activity, HttpStatus.CREATED);
	}
}