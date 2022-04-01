package commons.messages;

import java.io.Serializable;

/**
 * Parent class for all TCP messages. `Connection.send()` and `Connection.receive()` use this.
 */
public abstract class Message implements Serializable {
	/**
	 * This method exists because Java does not allow you to switch on classes.
	 * I would want to do something like this:
	 * <pre>
	 * switch (message.getClass()) {
	 *     case JoinMessage.class:
	 * 	       // code
	 * 	   default:
	 * 	       // code
	 * }
	 * </pre>
	 * Unfortunately Java is a badly designed language with weird limitations
	 * and forces you to write ugly bloated code. With this method you can
	 * now switch on the type like so:
	 * <pre>
	 * switch (message.getType()) {
	 *     case JOIN:
	 *         // code
	 *     default:
	 *         // code
	 * }
	 * </pre>
	 * @return The type of the message.
	 */
	public abstract MessageType getType();
}