package server;

import java.util.HashMap;
import java.util.Map.Entry;

import commons.LobbyResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/lobby")
public class LobbyController {
	/**
	 * Time in milliseconds.
	 * If a player does not send a request before this timeout they are removed from the lobby.
	 */
	public final static long TIMEOUT_MILLISECONDS = 1000L;

	/**
	 * Stores names of players and time since their last request.
	 */
	private HashMap<String, Long> players;
	/**
	 * Stores which names have which ID. This is used to detect name collisions.
	 */
	private HashMap<String, Integer> ids;
	/**
	 * Used to generate unique IDs. It only ever increases in value.
	 */
	private int uniqueID = 0;

	public LobbyController() {
		this.players = new HashMap<>();
		this.ids = new HashMap<>();
	}

	/**
	 * Increments the id and then returns it.
	 *
	 * @return a unique ID
	 */
	private int getUniqueID() {
		return ++this.uniqueID;
	}

	/**
	 * Removes old players from the lobby.
	 */
	private void clearOld() {
		long currentTime = System.currentTimeMillis();
		HashMap<String, Long> players = new HashMap<>();
		HashMap<String, Integer> ids = new HashMap<>();
		for (Entry<String, Long> entry : this.players.entrySet()) {
			String name = entry.getKey();
			Long time = entry.getValue();
			// Keep only names that are younger than TIMEOUT
			if (currentTime < time + TIMEOUT_MILLISECONDS) {
				players.put(name, time);
				ids.put(name, this.ids.get(name));
			}
		}
		this.players = players;
		this.ids = ids;
	}

	@GetMapping("/register/")
	public ResponseEntity<LobbyResponse> registerPlayer(@RequestParam String name) {
		// Remove any timed-out players.
		clearOld();

		// Check for name collision.
		if (this.players.containsKey(name)) {
			return ResponseEntity.badRequest().build();
		}
		// Add player to players in lobby and keep track of the id.
		int id = this.getUniqueID();
		this.players.put(name, System.currentTimeMillis());
		this.ids.put(name, id);
		return new ResponseEntity<>(
			new LobbyResponse(this.players.size(), id),
			HttpStatus.ACCEPTED
		);
	}

	@GetMapping("/refresh/")
	public ResponseEntity<LobbyResponse> refreshPlayer(
		@RequestParam String name,
		@RequestParam int id
	) {
		clearOld();

		// Make sure the correct ID was sent.
		if (!this.ids.containsKey(name) || id != this.ids.get(name)) {
			return ResponseEntity.badRequest().build();
		}
		// Refresh the time.
		this.players.put(name, System.currentTimeMillis());
		return new ResponseEntity<>(
			new LobbyResponse(this.players.size(), id),
			HttpStatus.ACCEPTED
		);
	}
}
