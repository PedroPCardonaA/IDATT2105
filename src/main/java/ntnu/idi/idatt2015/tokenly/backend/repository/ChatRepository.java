package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Chat;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for Chat objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface ChatRepository {

    /**
     * Saves a Chat object to the repository.
     *
     * @param chat the Chat object to save
     * @return the saved Chat object
     */
    Chat save(Chat chat);

    /**
     * Retrieves a List of all Chat objects in the repository.
     *
     * @return an Optional containing the List of all Chat objects in the repository, or an empty Optional if the repository is empty
     */
    Optional<List<Chat>> getAll();

/**
     * Retrieves a List of all Chat objects in the repository that involve a seller with the specified name.
     *
     * @param username the name of the seller to match against
     * @return an Optional containing the List of matching Chat objects, or an empty Optional if no Chat objects match the specified seller name
     */

    Optional<List<Chat>> getAllChatsByUsername(String username);

    /**
     * Retrieves a Chat object from the repository by ID.
     *
     * @param chatId the ID of the Chat object to retrieve
     * @return an Optional containing the retrieved Chat object, or an empty Optional if no Chat object with the specified ID exists in the repository
     */
    Optional<Chat> getChatById (long chatId);

    Optional<Long> makeAllSeen (long chatId);

}

