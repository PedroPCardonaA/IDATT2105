package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;

import java.util.List;

public interface WishListInterface {
    void saveWish( long userId, long itemId);
    //List<User> getAllUserThatWantTheItem(long itemId);
    List<Item> getAllTheItemsWantedByUser(long userId);
}
