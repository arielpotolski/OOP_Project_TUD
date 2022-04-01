package server.api;

import java.util.ArrayList;
import java.util.List;

import commons.MessageModel;
import commons.Player;
import static server.CustomAssertions.assertResponseEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerControllerTest {
	private Player player;
	private PlayerController playerController;
	private MessageModel messageModel;
	List<Player> listPlayers;

	@BeforeEach
	void setup() {
		this.player = new Player("Dimitar");
		this.listPlayers = new ArrayList<>();
		this.listPlayers.add(this.player);
		this.playerController = new PlayerController(new DummyPlayerRepository());
		this.messageModel = new MessageModel("Hello World", "Viet Luong");
	}

	@Test
	void getAll() {
		assertEquals(this.listPlayers, this.playerController.getAll());
	}

	@Test
	void addPlayer() {
		assertResponseEquals(HttpStatus.OK, this.playerController.addPlayer(this.player));
	}

	@Test
	void sendMessage() {
		assertEquals(this.messageModel, this.playerController.sendMessage("0", this.messageModel));
	}
}