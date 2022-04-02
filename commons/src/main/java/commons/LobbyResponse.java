package commons;

import java.util.Set;

public record LobbyResponse(
	Set<String> playersInLobby,
	int playerID,
	boolean gameStarted,
	int tcpPort
) {}