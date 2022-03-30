package server.api;

import java.util.List;

import commons.MessageModel;
import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
	final SimpMessagingTemplate simpMessagingTemplate;

	public PlayerController(PlayerRepository playerRepository, SimpMessagingTemplate simpMessagingTemplate) {
		this.playerRepository = playerRepository;
		this.simpMessagingTemplate = simpMessagingTemplate;
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


	/**
	 * This method return the MessageModel to other clients after one client want to
	 * send the message to other clients
	 * @param messageModel
	 * @param idFromClient
	 * @return return the message which is sent by the client
	 */
	@MessageMapping("/chat/{idFromClient}")
	@SendTo("/message/receive/{idFromServer}")
	public void sendMessage(@DestinationVariable String idFromClient,
									MessageModel messageModel) {
		simpMessagingTemplate.convertAndSend("/message/receive/" + idFromClient, messageModel);
	}

}