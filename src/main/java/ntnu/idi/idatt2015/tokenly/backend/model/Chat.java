package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents a chat between two users, for example between a seller and a buyer.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter
@Setter
public class Chat {
    /**
     * The ID of the chat.
     */
    private long chatId;

    /**
     * The ID of the listing associated with the chat.
     */
    private long listingId;

    /**
     * The name of the seller.
     */
    private String sellerName;

    /**
     * The name of the buyer.
     */
    private String buyerName;

    /**
     * The list of messages in the chat.
     */
    private List<Message> messages;
}
