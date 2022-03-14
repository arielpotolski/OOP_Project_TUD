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

import java.util.List;

import commons.Activity;
import commons.Player;

import com.google.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {
	private String server;


	/**
	 * Constructor for the connection between client and server.
	 *
	 * @param server the link that player types before playing the game
	 */
	@Inject
	public ServerUtils(String server){
		this.server = server;
	}

	/**
	 * The method add a player to database
	 *
	 * @param player an information of a player.
	 * @return a player
	 */
	public Player addPlayer(Player player) {
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/players/addPlayer")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.put(Entity.entity(player, APPLICATION_JSON), Player.class);
	}

	/**
	 * The method get a list of activities from database.
	 *
	 * @return a list of activity.
	 */
	public List<Activity> getActivityList(){
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/activities/")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Activity>>() {});
	}

	/**
	 * this method get a list of players for global leader board.
	 *
	 * @return a list of players.
	 */
	public List<Player> getPlayers() {
		return ClientBuilder.newClient(new ClientConfig())
				.target(server).path("api/players")
				.request(APPLICATION_JSON)
				.accept(APPLICATION_JSON)
				.get(new GenericType<List<Player>>() {});
	}

}
