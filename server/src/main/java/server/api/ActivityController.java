package server.api;

import java.util.List;

import commons.Activity;
import server.database.ActivityRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/activities")
public class ActivityController {

	private final ActivityRepository repository;

	public ActivityController(ActivityRepository repository) {
		this.repository = repository;
	}

	private static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	@GetMapping(path = {"", "/"})
	public List<Activity> getAll() {
		return  repository.findAll();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Activity> deleteById(@PathVariable("id") String id) {
		if (isNullOrEmpty(id) || !repository.existsById(id)) {
			return ResponseEntity.badRequest().build();
		}

		repository.deleteById(id);

		return (ResponseEntity<Activity>) ResponseEntity.ok();
	}

	private static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Activity> getById(@PathVariable("id") String id) {
		if ("".equals(id) || !repository.existsById(id)) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(repository.findById(id).get());
	}

	@PostMapping(path = {"", "/"},
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Activity> create(@RequestBody Activity newActivity) {
		if (
			newActivity == null ||
			isNullOrEmpty(newActivity.getId()) ||
			isNullOrEmpty(newActivity.getTitle()) ||
			isNullOrEmpty(newActivity.getSource()) ||
			newActivity.getConsumptionInWh() < 0
		) {
			return ResponseEntity.badRequest().build();
		}
		Activity activity = this.repository.save(newActivity);
		return new ResponseEntity<>(activity, HttpStatus.CREATED);
	}
}
