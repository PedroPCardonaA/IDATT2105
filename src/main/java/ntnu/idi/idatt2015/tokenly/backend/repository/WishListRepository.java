package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface WishListRepository {
    void save( long userId, long itemId);
    Optional<List<User>> getAllUserThatWantTheItem(long itemId);
    Optional<List<Item>>getAllTheItemsWantedByUser(String username);
}
