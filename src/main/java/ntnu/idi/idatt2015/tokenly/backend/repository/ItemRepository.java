package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for Item objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface ItemRepository {

    /**
     * Saves an Item object to the repository.
     *
     * @param item the Item object to save
     */
    void save(Item item);

    /**
     * Retrieves an Item object from the repository by ID.
     *
     * @param id the ID of the Item object to retrieve
     * @return an Optional containing the retrieved Item object, or an empty Optional if no Item object with the specified ID exists in the repository
     */
    Optional<Item> getItemById(long id);

    /**
     * Retrieves a List of all Item objects in the repository that are owned by a user with the specified name.
     *
     * @param ownerName the name of the owner to match against
     * @return an Optional containing the List of matching Item objects, or an empty Optional if no Item objects are owned by the specified user
     */
    Optional<List<Item>> getAllItemsByOwnerName(String ownerName);

    /**
     * Retrieves a List of all Item objects in the repository that match a partial name.
     *
     * @param itemName the partial name to match against
     * @return an Optional containing the List of matching Item objects, or an empty Optional if no Item objects match the specified partial name
     */
    Optional<List<Item>> getAllByPartialName(String itemName);

    /**
     * Retrieves a List of all Item objects in the repository.
     *
     * @return an Optional containing the List of all Item objects in the repository, or an empty Optional if the repository is empty
     */
    Optional<List<Item>> getAll();
}

