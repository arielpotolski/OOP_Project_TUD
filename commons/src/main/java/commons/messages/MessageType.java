package commons.messages;

// Add more message types here as needed.

/**
 * The type of the message. Each subclass of Message should have its own value in this enum.
 * They should then return it in their `getType()` method. (See `Message`).
 */
public enum MessageType { ERROR, JOIN, LEADERBOARD }
