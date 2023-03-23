package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for Category objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface CategoryRepository {

    /**
     * Saves a Category object to the repository.
     *
     * @param category the Category object to save
     */
    boolean save (Category category);

    /**
     * Retrieves a Category object from the repository by ID.
     *
     * @param id the ID of the Category object to retrieve
     * @return an Optional containing the retrieved Category object, or an empty Optional if no Category object with the specified ID exists in the repository
     */
    Optional<Category> getCategoryById(int id);

    /**
     * Retrieves a Category object from the repository by name.
     *
     * @param name the name of the Category object to retrieve
     * @return an Optional containing the retrieved Category object, or an empty Optional if no Category object with the specified name exists in the repository
     */
    Optional<Category> getCategoryByName(String name);

    /**
     * Retrieves a List of Category objects from the repository that match a partial name.
     *
     * @param name the partial name to match against
     * @return an Optional containing the List of matching Category objects, or an empty Optional if no Category objects match the specified partial name
     */
    Optional<List<Category>> getCategoriesByPartialName(String name);

    /**
     * Retrieves a List of Category objects from the repository that have a matching description.
     *
     * @param description the description to match against
     * @return an Optional containing the List of matching Category objects, or an empty Optional if no Category objects have a matching description
     */
    Optional<List<Category>> getCategoriesByDescription(String description);

    /**
     * Retrieves a List of all Category objects in the repository.
     *
     * @return an Optional containing the List of all Category objects in the repository, or an empty Optional if the repository is empty
     */
    Optional<List<Category>> getAll();
}
