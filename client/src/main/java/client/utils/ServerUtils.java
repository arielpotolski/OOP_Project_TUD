package client.utils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import commons.Activity;
import commons.Connection;
import commons.LobbyResponse;
import commons.Player;
import commons.Question;
import commons.messages.JoinMessage;

import com.google.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.ResponseProcessingException;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {
	private final String host;
	private final Client client;
	private String name;
	private int id;
	private Connection connection;
	private StompSession session;
	private static final int SERVER_PORT = 8080;

	/**
	 * Constructor for the connection between client and server.
	 * @param host The link that player types before playing the game.
	 */
	@Inject
	public ServerUtils(String host) {
		this.host = host;
		this.client = ClientBuilder.newClient();
	}

	/**
	 * Getter for the connection.
	 * @return The connection of this server.
	 */
	public Connection getConnection() {
		return this.connection;
	}

	private URI getServer() {
		return UriBuilder
			.newInstance()
			.scheme("http")
			.host(this.host)
			.port(SERVER_PORT)
			.build();
	}

	/**
	 * The method adds a player to the database.
	 * @param player The player to add.
	 * @return The added player.
	 */
	public Player addPlayer(Player player) {
		return this.client
			.target(this.getServer())
			.path("api/players/addPlayer")
			.request(APPLICATION_JSON)
			.put(Entity.entity(player, APPLICATION_JSON), Player.class);
	}


	/**
	 * Get a list of questions from the server.
	 * @return A list of questions from the server.
	 * @param seed The seed that dictates the order of the questions.
	 */
	public List<Question> getQuestions(long seed) {
		return this.client
			.target(this.getServer())
			.path("api/questions/")
			.queryParam("seed", seed)
			.request(APPLICATION_JSON)
			.get(new GenericType<>() {});
	}

	/**
	 * Get a list of activities from the server.
	 * @return A list of activities.
	 */
	public List<Activity> getActivities() {
		return this.client
			.target(this.getServer())
			.path("api/questions/getActivities")
			.request(APPLICATION_JSON)
			.get(new GenericType<>() {});
	}

	/**
	 * This method gets a list of players for the global leaderboard.
	 * @return A list of players.
	 */
	public List<Player> getPlayers() {
		return this.client
			.target(this.getServer())
			.path("api/players/")
			.request(APPLICATION_JSON)
			.get(new GenericType<>() {});
	}

	/**
	 * @param name Name of the player.
	 * @return An optional LobbyResponse.  If the request was successful then the LobbyResponse is
	 * returned.  Otherwise, `Optional.empty()` is returned.  This can happen if the name is already
	 * in use.
	 */
	public Optional<LobbyResponse> connectToLobby(String name) throws ProcessingException {
		try {
			LobbyResponse response = this.client
				.target(this.getServer())
				.path("lobby/register/")
				.queryParam("name", name)
				.request(APPLICATION_JSON)
				.get(new GenericType<>() {});

			this.name = name;
			this.id = response.playerID();

			return Optional.of(response);
		} catch (WebApplicationException err) {
			// 400 BAD REQUEST
			// It means the name is already taken assuming
			// that the request is well formatted (it should be).
			return Optional.empty();
		}
	}

	/**
	 * Helper function for `refreshLobby` and `startMultiplayerGame`.
	 * @param path API request path.
	 * @return Optional LobbyResponse.  Empty if something went wrong.  For example if the client
	 * timed out or is sending the wrong id.
	 */
	private Optional<LobbyResponse> lobbyRequest(String path) {
		try {
			LobbyResponse response = this.client
				.target(this.getServer())
				.path(path)
				.queryParam("name", this.name)
				.queryParam("id", this.id)
				.request(APPLICATION_JSON)
				.get(new GenericType<>() {});
			return Optional.of(response);
		} catch (ResponseProcessingException err) {
			return Optional.empty();
		}
	}

	/**
	 * Create a request to refresh our presence in the lobby.
	 * @return LobbyResponse if the request was successful, otherwise empty.
	 */
	public Optional<LobbyResponse> refreshLobby() {
		return this.lobbyRequest("lobby/refresh/");
	}

	/**
	 * Create a request to start the multiplayer game.
	 * @return LobbyResponse if the request was successful, otherwise empty.
	 */
	public Optional<LobbyResponse> startMultiplayerGame() {
		return this.lobbyRequest("lobby/start/");
	}

	/**
	 * Creates a TCP connection to the server and sends a JOIN message.
	 * @param port Socket port to connect to.
	 * @throws IOException When Connection creation fails or if the sending fails.
	 */
	public void makeConnection(int port) throws IOException {
		this.connection = Connection.to(this.host, port);
		this.connection.send(new JoinMessage(this.name));
	}

	/**
	 * Add the given activity to the severs database and return the added activity.
	 * @param a The activity to add to the database.
	 * @return The activity that was added.
	 */
	public Activity addActivity(Activity a) {
		return this.client
			.target(this.getServer())
			.path("api/questions/addActivity")
			.request(APPLICATION_JSON)
			.put(Entity.entity(a, APPLICATION_JSON), Activity.class);
	}

	/**
	 * Remove an activity from the server.
	 * @param a The activity to remove.
	 * @return The servers response after the DELETE request.
	 */
	public Response removeActivity(Activity a) {
		return this.client
			.target(this.getServer())
			.path("api/questions/" + a.getId())
			.request(APPLICATION_JSON)
			.delete();
	}

	private URI getWebSocketServer() {
		return UriBuilder
			.newInstance()
			.scheme("ws")
			.host(this.host)
			.port(SERVER_PORT)
			.path("/websocket")
			.build();
	}

	/**
	 * This method creates the connection between the client and the server.
	 * @return The session.
	 */
	public StompSession connect() {
		var client = new StandardWebSocketClient();
		var stomp = new WebSocketStompClient(client);
		stomp.setMessageConverter(new MappingJackson2MessageConverter());

		try {
			return stomp.connect(
				this.getWebSocketServer().toString(),
				new StompSessionHandlerAdapter() {}
			).get();
		} catch (InterruptedException err) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException err) {
			throw new RuntimeException(err);
		}
		throw new IllegalArgumentException();
	}

	/**
	 * This method allows the server to send messages to the client.
	 * @param dest The client.
	 * @param type The type of the class.
	 * @param consumer Consumer of the object.
	 * @param <T> The generic type of the object that the server send to the client.
	 */
	public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
		this.session.subscribe(dest, new StompSessionHandlerAdapter() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return type;
			}
			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				consumer.accept((T) payload);
			}
		});
	}

	/**
	 * This method allows the client to send the message to server.
	 * @param dest The destination - server.
	 * @param o The object - client will send this object to the server.
	 */
	public void send(String dest, Object o) {
		this.session.send(dest, o);
	}

	public void setSession(StompSession session) {
		this.session = session;
	}
}