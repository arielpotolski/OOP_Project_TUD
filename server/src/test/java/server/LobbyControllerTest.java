package server;

import commons.LobbyResponse;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LobbyControllerTest {
	@Test
	public void onePlayerConnects() {
		LobbyController lobby = new LobbyController();
		// Add one player.
		ResponseEntity<LobbyResponse> response = lobby.registerPlayer("Alice");
		// Make sure it was accepted.
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		// Check that the number of players is 1.
		// The ID is an implementation detail.
		assertEquals(1, response.getBody().playersInLobby());
	}

	@Test
	public void threePlayersConnect() {
		LobbyController lobby = new LobbyController();

		// Add three players, each with a unique name
		ResponseEntity<LobbyResponse> responseAlice = lobby.registerPlayer("Alice");
		assertEquals(HttpStatus.ACCEPTED, responseAlice.getStatusCode());
		LobbyResponse alice = responseAlice.getBody();

		ResponseEntity<LobbyResponse> responseBob = lobby.registerPlayer("Bob");
		assertEquals(HttpStatus.ACCEPTED, responseBob.getStatusCode());
		LobbyResponse bob = responseBob.getBody();

		ResponseEntity<LobbyResponse> responseCharlie = lobby.registerPlayer("Charlie");
		assertEquals(HttpStatus.ACCEPTED, responseCharlie.getStatusCode());
		LobbyResponse charlie = responseCharlie.getBody();

		// Make sure IDs are unique.
		assertNotEquals(alice.playerID(), bob.playerID());
		assertNotEquals(bob.playerID(), charlie.playerID());
		assertNotEquals(charlie.playerID(), alice.playerID());

		// Charlie was last to connect, so they have the latest information.
		assertEquals(3, charlie.playersInLobby());
	}

	@Test
	public void nameCollision() {
		LobbyController lobby = new LobbyController();
		assertEquals(HttpStatus.ACCEPTED, lobby.registerPlayer("Alice").getStatusCode());
		assertEquals(HttpStatus.ACCEPTED, lobby.registerPlayer("Bob").getStatusCode());
		assertEquals(HttpStatus.ACCEPTED, lobby.registerPlayer("Charlie").getStatusCode());

		assertEquals(HttpStatus.BAD_REQUEST, lobby.registerPlayer("Alice").getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, lobby.registerPlayer("Bob").getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, lobby.registerPlayer("Charlie").getStatusCode());
	}

	@Test
	public void refreshLobby() {
		LobbyController lobby = new LobbyController();
		LobbyResponse alice = lobby.registerPlayer("Alice").getBody();
		ResponseEntity<LobbyResponse> response = lobby.refreshPlayer("Alice", alice.playerID());
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		// Check that player ID stays the same.
		assertEquals(alice.playerID(), response.getBody().playerID());
		// Check that the number of players stays one.
		assertEquals(1, response.getBody().playersInLobby());
	}

	@Test
	public void refreshWithWrongParams() {
		LobbyController lobby = new LobbyController();
		LobbyResponse alice = lobby.registerPlayer("Alice").getBody();
		assertEquals(
			HttpStatus.BAD_REQUEST,
			lobby.refreshPlayer("Alice", alice.playerID() + 999).getStatusCode()
		);
		assertEquals(
			HttpStatus.BAD_REQUEST,
			lobby.refreshPlayer("Impostor", alice.playerID()).getStatusCode()
		);
	}

	@Test
	public void nameAvailableAfterTimeout() throws InterruptedException {
		LobbyController lobby = new LobbyController();
		LobbyResponse alice = lobby.registerPlayer("Alice").getBody();
		// Check that refresh works.
		assertEquals(
			HttpStatus.ACCEPTED,
			lobby.refreshPlayer("Alice", alice.playerID()).getStatusCode()
		);
		// Check that name collision is detected.
		assertEquals(HttpStatus.BAD_REQUEST, lobby.registerPlayer("Alice").getStatusCode());
		// Wait until Alice is dropped from the lobby.
		Thread.sleep(LobbyController.TIMEOUT_MILLISECONDS + 10);
		// Check that ID is now invalid.
		assertEquals(
			HttpStatus.BAD_REQUEST,
			lobby.refreshPlayer("Alice", alice.playerID()).getStatusCode()
		);
		// Check that new registration works.
		LobbyResponse newAlice = lobby.registerPlayer("Alice").getBody();
		assertNotEquals(alice.playerID(), newAlice.playerID());
		// Check that the number of players is still one.
		assertEquals(1, alice.playersInLobby());
	}

	@Test
	public void detectPlayerTimeoutFromOtherPlayer() throws InterruptedException {
		LobbyController lobby = new LobbyController();
		// Add two players to lobby.
		assertEquals(HttpStatus.ACCEPTED, lobby.registerPlayer("Alice").getStatusCode());
		LobbyResponse bob = lobby.registerPlayer("Bob").getBody();
		// There are two players in the lobby.
		assertEquals(2, bob.playersInLobby());
		Thread.sleep(LobbyController.TIMEOUT_MILLISECONDS / 2);
		// After a while there should still be two. Bob refreshes his timer.
		assertEquals(2, lobby.refreshPlayer("Bob", bob.playerID()).getBody().playersInLobby());
		Thread.sleep(LobbyController.TIMEOUT_MILLISECONDS / 2 + 10);
		// Now, Alice should have timed out and bob should see only one player in the lobby.
		assertEquals(1, lobby.refreshPlayer("Bob", bob.playerID()).getBody().playersInLobby());
		// A different Alice joins now and Bob should see two players again.
		assertEquals(HttpStatus.ACCEPTED, lobby.registerPlayer("Alice").getStatusCode());
		assertEquals(2, lobby.refreshPlayer("Bob", bob.playerID()).getBody().playersInLobby());
	}
}
