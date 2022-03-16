package server.api;

import java.util.List;

import commons.Player;
import server.database.PlayerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
	private final PlayerRepository playerRepository;

	public PlayerController(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@GetMapping(path = {"", "/"})
	public List<Player> getAll() {
		return this.playerRepository.findAll();
	}

	@PutMapping("/addPlayer")
	public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
		if (player == null || player.getNickName().equals("")) {
			return ResponseEntity.badRequest().build();
		}
		Player result = playerRepository.save(player);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}