                                          package server;
                                                  
                                    import java.io.IOException;
                                   import java.net.ServerSocket;
                                     import java.util.HashMap;
                                                  
                                   import commons.LobbyResponse;
                                                  
                            import org.springframework.http.HttpStatus;
                          import org.springframework.http.ResponseEntity;
                         import org.springframework.stereotype.Controller;
                            import org.springframework.util.SocketUtils;
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
                             * Stores names of players and their info:
                                *  - timestamp of their last request
                                           *  - their id
                           *  - whether they are supposed to be in a game
                                                 */
                        private final HashMap<String, LobbyPlayer> players;
                                                /**
                  * Used to generate unique IDs. It only ever increases in value.
                                                 */
                                     private int uniqueID = 0;
                                                  
                                     public LobbyController() {
                                  this.players = new HashMap<>();
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
                                            this.players
                                             .values()
      .removeIf(playerInfo -> currentTime > playerInfo.getTimestamp() + TIMEOUT_MILLISECONDS);
                                                 }
                                                  
                                                /**
                             * Checks ID against players in the lobby.
                               * @param name The name of the player.
                      * @param id The ID of the player that should be checked.
                                * @return whether the ID was wrong.
                                                 */
                           private boolean wrongID(String name, int id) {
          return !this.players.containsKey(name) || this.players.get(name).getId() != id;
                                                 }
                                                  
                                     @GetMapping("/register/")
          public ResponseEntity<LobbyResponse> registerPlayer(@RequestParam String name) {
                                            clearOld();
                                                  
                                    // Check for name collision.
                               if (this.players.containsKey(name)) {
                            return ResponseEntity.badRequest().build();
                                                 }
                                                  
                    // Add player to players in lobby and keep track of the id.
                                    int id = this.getUniqueID();
                            this.players.put(name, new LobbyPlayer(id));
                                    return new ResponseEntity<>(
                      new LobbyResponse(this.players.keySet(), id, false, -1),
                                        HttpStatus.ACCEPTED
                                                 );
                                                 }
                                                  
                                      @GetMapping("/refresh/")
                        public ResponseEntity<LobbyResponse> refreshPlayer(
                                     @RequestParam String name,
                                        @RequestParam int id
                                                ) {
                                            clearOld();
                                      if (wrongID(name, id)) {
                            return ResponseEntity.badRequest().build();
                                                 }
                                                  
                                     // Refresh the timestamp.
                            LobbyPlayer player = this.players.get(name);
                                     player.updateTimestamp();
                                                  
                                    return new ResponseEntity<>(
      new LobbyResponse(this.players.keySet(), id, player.getGameStarted(), player.getPort()),
                                        HttpStatus.ACCEPTED
                                                 );
                                                 }
                                                  
                                       @GetMapping("/start/")
                          public ResponseEntity<LobbyResponse> startGame(
                                     @RequestParam String name,
                                        @RequestParam int id
                                       ) throws IOException {
                                            clearOld();
                                      if (wrongID(name, id)) {
                            return ResponseEntity.badRequest().build();
                                                 }
                                                  
                          // Start a new thread for the multiplayer game.
                           int port = SocketUtils.findAvailableTcpPort();
                            MultiplayerGame game = new MultiplayerGame(
                                      new ServerSocket(port),
                                    new HashMap<>(this.players)
                                                 );
                                           game.start();
                                                  
                      // Set gameStarted to true for all players in the lobby
                      // so that the next time they refresh they know they are
                                           // in a game.
                         for (LobbyPlayer player: this.players.values()) {
                                      player.setGameStarted();
                                     player.setGamePort(port);
                                                 }
                                                  
                                    return new ResponseEntity<>(
                                         new LobbyResponse(
                                       this.players.keySet(),
                                                id,
                                               true,
                                                port
                                                 ),
                                        HttpStatus.ACCEPTED
                                                 );
                                                 }
                                                 }
