package server.api;

import java.util.List;

import commons.MessageModel;
import commons.Player;
import org.springframework.web.bind.annotation.*;
import server.database.PlayerRepository;
import static commons.Utility.nullOrEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

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
		if (player == null || nullOrEmpty(player.getNickName())) {
			return ResponseEntity.badRequest().build();
		}
		Player result = playerRepository.save(player);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@MessageMapping("/chat/{id}") //app/chat
	@SendTo("/message/receive/{id}")
	/**
	 * This method return the MessageModel to other clients after one client want to
	 * send the message to other clients
	 */
	public MessageModel sendMessage(@PathVariable(value = "id") int id, MessageModel messageModel) {
		return messageModel;
	}

}