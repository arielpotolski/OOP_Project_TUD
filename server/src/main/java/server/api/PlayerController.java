                                        package server.api;
                                                  
                                       import java.util.List;
                                                  
                                    import commons.MessageModel;
                                       import commons.Player;
                              import server.database.PlayerRepository;
                             import static commons.Utility.nullOrEmpty;
                                                  
                            import org.springframework.http.HttpStatus;
                          import org.springframework.http.ResponseEntity;
            import org.springframework.messaging.handler.annotation.DestinationVariable;
              import org.springframework.messaging.handler.annotation.MessageMapping;
                  import org.springframework.messaging.handler.annotation.SendTo;
                     import org.springframework.web.bind.annotation.GetMapping;
                     import org.springframework.web.bind.annotation.PutMapping;
                    import org.springframework.web.bind.annotation.RequestBody;
                   import org.springframework.web.bind.annotation.RequestMapping;
                   import org.springframework.web.bind.annotation.RestController;
                                                  
                                          @RestController
                                  @RequestMapping("/api/players")
                                  public class PlayerController {
                          private final PlayerRepository playerRepository;
                                                  
                    public PlayerController(PlayerRepository playerRepository) {
                             this.playerRepository = playerRepository;
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
                             @SendTo("/message/receive/{idFromClient}")
             public MessageModel sendMessage(@DestinationVariable String idFromClient,
                                    MessageModel messageModel) {
                                        return messageModel;
                                                 }
                                                  
                                                  }