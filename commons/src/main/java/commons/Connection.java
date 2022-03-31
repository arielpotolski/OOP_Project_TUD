                                          package commons;
                                                  
                                    import java.io.IOException;
                                 import java.io.ObjectInputStream;
                                 import java.io.ObjectOutputStream;
                                      import java.net.Socket;
                                                  
                               import commons.messages.ErrorMessage;
                                  import commons.messages.Message;
                                import commons.messages.MessageType;
                                                  
      public record Connection(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
                                                /**
                               * Create a TCP connection to a server.
                               * @param server Address to the server.
                                 * @return The created Connection.
                    * @throws IOException When we cannot create the connection.
                                                 */
             public static Connection to(String server, int port) throws IOException {
                            return fromSocket(new Socket(server, port));
                                                 }
                                                  
                                                /**
                       * Create the Connection class from an existing socket.
                 * @param socket Socket from which to create the Connection class.
                        * @return Connection that uses the supplied socket.
                      * @throws IOException When ObjectStream creation fails.
                                                 */
              public static Connection fromSocket(Socket socket) throws IOException {
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
               ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                              return new Connection(socket, in, out);
                                                 }
                                                  
                                                /**
                               * @param message The message to send.
             * @throws IOException If we cannot write the message to the OutputStream.
                                                 */
                       public void send(Message message) throws IOException {
                                   this.out.writeObject(message);
                                                 }
                                                  
                                                /**
                                    * @return Received message.
                       * @throws IOException When we cannot read the object.
              * @throws ClassNotFoundException If an unknown object class is received.
                                                 */
               public Message receive() throws IOException, ClassNotFoundException {
                               return (Message) this.in.readObject();
                                                 }
                                                  
                                                /**
             * Keep receiving until a message with the correct MessageType is received.
                   * For each incorrect message received we send an ErrorMessage.
                               * @param type MessageType to wait for.
                         * @return A Message with the correct MessageType.
                   * @throws IOException Issues with writing or reading objects.
                * @throws ClassNotFoundException Unknown object class was received.
                                                 */
     public Message receiveType(MessageType type) throws IOException, ClassNotFoundException {
                                           while (true) {
                                 Message message = this.receive();
                                  if (message.getType() == type) {
                                          return message;
                                              } else {
                    this.send(new ErrorMessage("expected " + type.toString()));
                                                 }
                                                 }
                                                 }
                                                 }
