package server.api;

import java.util.List;

import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;


@RestController
@RequestMapping("/api/activities")
public class ActivityController {

	private final ActivityRepository repository;

	public ActivityController(ActivityRepository repository) {
		this.repository = repository;
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
}
