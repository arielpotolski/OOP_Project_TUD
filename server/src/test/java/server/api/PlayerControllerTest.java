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
                                  void setup() throws Exception {
                                  player = new Player("Dimitar");
                                  listPlayers = new ArrayList<>();
                                      listPlayers.add(player);
               playerController = new PlayerController(new DummyPlayerRepository());
                   messageModel = new MessageModel("Hello World", "Viet Luong");
                                                 }
                                                  
                                               @Test
                                          void getAll() {
                       assertEquals(listPlayers, playerController.getAll());
                                                 }
                                                  
                                               @Test
                                         void addPlayer() {
              assertResponseEquals(HttpStatus.OK, playerController.addPlayer(player));
                                                 }
                                                  
                                               @Test
                                        void sendMessage() {
            assertEquals(messageModel, playerController.sendMessage("0", messageModel));
                                                 }
                                                  }