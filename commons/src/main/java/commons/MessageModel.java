                                          package commons;
                                                  
                                     import java.util.Objects;
                                                  
                       import com.fasterxml.jackson.annotation.JsonProperty;
                       import org.apache.commons.lang3.builder.EqualsBuilder;
                                                  
                                    public class MessageModel {
                                                  
                                      @JsonProperty("message")
                                      private String message;
                                                  
                                     @JsonProperty("nickname")
                                      private String nickname;
                                                  
                                                  
                                                /**
                               * The constructor of the MessageModel
                         * @param message the message when the client send
                            * @param nickname the nickname of the player
                                                 */
                       public MessageModel(String message, String nickname) {
                                      this.message = message;
                                     this.nickname = nickname;
                                                 }
                                                  
                                      public MessageModel() {}
                                                  
                                                /**
                    * Getter for the message that client sends to other clients
                                       * @return the message
                                                 */
                                    public String getMessage() {
                                          return message;
                                                 }
                                                  
                                                /**
                    * Setter for the message that client sends to other clients
                                    * @param message the message
                                                 */
                              public void setMessage(String message) {
                                      this.message = message;
                                                 }
                                                  
                                                /**
                              * Getter for the nickname of the client
                                * @return the nickname of the client
                                                 */
                                   public String getNickname() {
                                          return nickname;
                                                 }
                                                  
                                                /**
                              * Setter for the nickname of the client
                            * @param nickname the nickname of the client
                                                 */
                             public void setNickname(String nickname) {
                                     this.nickname = nickname;
                                                 }
                                                  
                                             @Override
                                public boolean equals(Object obj) {
                         return EqualsBuilder.reflectionEquals(this, obj);
                                                 }
                                                  
                                             @Override
                                      public int hashCode() {
                         return Objects.hash(this.message, this.nickname);
                                                 }
                                                 }
