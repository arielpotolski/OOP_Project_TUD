package server.api;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import static java.util.concurrent.TimeUnit.SECONDS;

import commons.MessageModel;
import commons.Player;
import static server.CustomAssertions.assertResponseEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayerControllerTest {

	@LocalServerPort
	private Integer port;

	private Player player;
	private PlayerController playerController;
	private MessageModel messageModel;
	List<Player> listPlayers;
	private WebSocketStompClient webSocketStompClient;

	@BeforeEach
	void setup() {
		this.player = new Player("Dimitar");
		this.listPlayers = new ArrayList<>();
		this.listPlayers.add(this.player);
		this.playerController = new PlayerController(new DummyPlayerRepository());
		this.messageModel = new MessageModel("Hello World", "Viet Luong");
		this.webSocketStompClient = new WebSocketStompClient(new SockJsClient(
				List.of(new WebSocketTransport(new StandardWebSocketClient()))));
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
	void verifySendMessageIsWorking() throws ExecutionException,
			InterruptedException, TimeoutException {
		BlockingQueue<MessageModel> blockingQueue = new ArrayBlockingQueue(1);

		this.webSocketStompClient.setMessageConverter(
				new MappingJackson2MessageConverter());

		StompSession session = this.webSocketStompClient
				.connect(String.format("ws://localhost:%d/websocket", this.port),
						new StompSessionHandlerAdapter() {})
				.get(1, SECONDS);

		session.subscribe("/message/receive/1", new StompSessionHandlerAdapter() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return MessageModel.class;
			}
			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				System.out.println("Received message: " + payload);
				blockingQueue.add((MessageModel) payload);
			}
		});

		session.send("/app/chat/1", this.messageModel);

		assertEquals(this.messageModel, blockingQueue.poll(1, SECONDS));
	}
}