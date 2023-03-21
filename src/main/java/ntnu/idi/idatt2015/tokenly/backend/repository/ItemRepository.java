package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    void save(Item item);
    Optional<Item> getItemById(long id);
    Optional<List<Item>> getAllItemsByOwnerName(String ownerName);
    Optional<List<Item>> getAll();
}
