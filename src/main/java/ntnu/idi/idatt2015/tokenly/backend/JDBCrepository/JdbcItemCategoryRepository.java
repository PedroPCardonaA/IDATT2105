package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemsCategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcItemCategoryRepository implements ItemsCategoryRepository {
    @Override
    public void save(long itemId, int categoryId) {

    }

    @Override
    public Optional<List<Category>> getAllTheCategoriesByItemId(long itemId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Item>> getAllTheItemsByCategoryId(long categoryId) {
        return Optional.empty();
    }
}
