package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.model.User;
import ntnu.idi.idatt2015.tokenly.backend.model.Wishlist;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for WishList objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface WishListRepository {

    /**
     * Saves a WishList object to the repository.
     *
     * @param wishlist the WishList object to save
     * @return the saved WishList object
     */
    Wishlist save(Wishlist wishlist);

    /**
     * Retrieves all User objects in the repository who want the item with the specified ID.
     *
     * @param itemId the ID of the item to match against
     * @return an Optional containing a List of all User objects who want the specified item, or an empty Optional if no Users want the item
     */
    Optional<List<String>> getAllUserThatWantTheItem(long itemId);

    /**
     * Retrieves all Item objects in the repository that are wanted by the user with the specified username.
     *
     * @param username the username of the user to match against
     * @return an Optional containing a List of all Item objects wanted by the specified user, or an empty Optional if the user wants no items
     */
    Optional<List<Item>> getAllTheItemsWantedByUser(String username);

    int deleteWishlistItem(Wishlist wishlist);
}

