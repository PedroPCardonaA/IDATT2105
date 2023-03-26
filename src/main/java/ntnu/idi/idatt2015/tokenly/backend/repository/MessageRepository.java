package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Message;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for Message objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface MessageRepository {

    /**
     * Saves a Message object to the repository.
     *
     * @param message the Message object to save
     */
    void save (Message message);

    /**
     * Retrieves all Message objects associated with a Chat object with the specified ID.
     *
     * @param chatId the ID of the Chat object to match against
     * @return a List of all Message objects associated with the specified Chat object, or an empty List if no Message objects are associated with the Chat object
     */
    Optional<List<Message>> getAllMessageByChatId(long chatId);

    /**
     * Retrieves all open Message objects associated with a Chat object with the specified ID.
     *
     * @param chatId the ID of the Chat object to match against
     * @return a List of all open Message objects associated with the specified Chat object, or an empty List if no open Message objects are associated with the Chat object
     */
    Optional<List<Message>> getAllOpenedMessageByChatId(long chatId);

    Optional<Long> closeMessage(long messageId);
}

