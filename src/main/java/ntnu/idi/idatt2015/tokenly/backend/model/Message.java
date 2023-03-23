package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

/**
 * The Message class represents a message sent in a chat by one of the parts.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter
@Setter
public class Message {

    /**
     * The ID of the message.
     */
    private long messageId;

    /**
     * The name of the sender who sent the message.
     */
    private String senderName;

    /**
     * The ID of the chat the message was sent in.
     */
    private long chatId;

    /**
     * The content of the message.
     */
    private String message;

    /**
     * The time the message was sent.
     */
    private Timestamp messageTime;

    /**
     * Whether the message has been deleted.
     */
    private boolean isDeleted;

    /**
     * Whether the message has been seen.
     */
    private boolean seen;
}

