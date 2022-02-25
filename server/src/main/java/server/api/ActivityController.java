package server.api;

import java.util.List;

import commons.Activity;
import server.database.ActivityRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
