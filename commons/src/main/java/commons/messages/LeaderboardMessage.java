                                     package commons.messages;
                                                  
                                     import java.util.HashMap;
                                                  
                                                /**
                 * Sent by the client after they create the initial TCP connection
                  * so that the server has a name to identify the connection with.
                                                 */
                         public class LeaderboardMessage extends Message {
                                             @Override
                                   public MessageType getType() {
                                  return MessageType.LEADERBOARD;
                                                 }
                                                  
                          private final HashMap<String, Integer> players;
                                                  
                   public LeaderboardMessage(HashMap<String, Integer> players) {
                                      this.players = players;
                                                 }
                                                  
                           public HashMap<String, Integer> getPlayers() {
                                        return this.players;
                                                 }
                                                 }
