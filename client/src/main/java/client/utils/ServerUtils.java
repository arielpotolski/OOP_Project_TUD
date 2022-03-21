/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.utils;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import commons.Connection;
import commons.LobbyResponse;
import commons.Player;
import commons.Question;
import commons.messages.JoinMessage;

import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.ResponseProcessingException;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.UriBuilder;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {
	private final String host;
	private final Client client;
	private String server;

	/**
	 * Constructor for the connection between client and server.
	 *
	 * @param host the link that player types before playing the game
	 */
	@Inject
	public ServerUtils(String host) {
		this.host = host;
		this.client = ClientBuilder.newClient();
	}

	private URI getServer() {
		return UriBuilder.newInstance().scheme("http").host(this.host).port(8080).build();
	}

	/**
	 * The method add a player to database
	 *
	 * @param player an information of a player.
	 * @return a player
	 */
	public Player addPlayer(Player player) {
		return this.client
			.target(this.getServer())
			.path("api/players/addPlayer")
			.request(APPLICATION_JSON)
			.put(Entity.entity(player, APPLICATION_JSON), Player.class);
	}

	/**
	 * Get a list of questions from the server
	 *
	 * @return A list of questions from the server
	 */
	public List<Question> getQuestions() {
		return this.client
			.target(this.getServer())
			.path("api/questions/")
			.request(APPLICATION_JSON)
			.get(new GenericType<>() {});
	}

	/**
	 * this method get a list of players for global leader board.
	 *
	 * @return a list of players.
	 */
	public List<Player> getPlayers() {
		return this.client
			.target(this.getServer()).path("api/players")
			.request(APPLICATION_JSON)
			.get(new GenericType<>() {});
	}

				.accept(APPLICATION_JSON)
				.get(new GenericType<>() {});
	}

}