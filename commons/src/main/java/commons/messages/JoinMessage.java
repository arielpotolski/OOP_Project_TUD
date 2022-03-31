                                     package commons.messages;
                                                  
                                                /**
                 * Sent by the client after they create the initial TCP connection
                  * so that the server has a name to identify the connection with.
                                                 */
                             public class JoinMessage extends Message {
                                             @Override
                                   public MessageType getType() {
                                      return MessageType.JOIN;
                                                 }
                                                  
                                     private final String name;
                                                  
                                 public JoinMessage(String name) {
                                         this.name = name;
                                                 }
                                                  
                                     public String getName() {
                                         return this.name;
                                                 }
                                                 }
