package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;

import java.util.List;

public interface ItemRepository {
    void save(Item item);
    Item getItemById(long id);
    List<Item> getAllItemsByOwnerId(long ownerId);

    List<Item> getAll();
}
