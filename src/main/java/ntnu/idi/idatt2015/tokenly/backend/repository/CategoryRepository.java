package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    void save (Category category);
    Optional<Category> getCategoryById(int id);
    Optional<Category> getCategoryByName(String name);
    Optional<List<Category>> getCategoriesByDescription(String description);
    Optional<List<Category>> getCategoriesByPartialName(String name);
    Optional<List<Category>> getAll();
}
