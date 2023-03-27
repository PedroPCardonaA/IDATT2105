package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Profile;
import ntnu.idi.idatt2015.tokenly.backend.model.User;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for Profile objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */

public interface ProfileRepository {

    /**
     * Saves a Profile object to the repository.
     *
     * @param profile the Profile object to save
     */
    void save(Profile profile);


    /**
     * Retrieves a Profile object with the specified ID.
     *
     * @param profileId the ID of the Profile object to retrieve
     * @return the Profile object with the specified ID, or null if no Profile object with the specified ID exists
     */
    Optional<Profile> getByProfileId(Long profileId);

    /**
     * Retrieves a Profile object with the specified username.
     *
     * @param username the username of the Profile object to retrieve
     * @return the Profile object with the specified username, or null if no Profile object with the specified username exists
     */
    Optional<Profile> getByUsername(String username);

    /**
     * Updates the balance of a Profile object with the specified ID.
     *
     * @param profileId the ID of the Profile object to update
     * @param balance the new balance of the Profile object
     * @return the number of rows affected by the update
     */
    int updateBalance(long profileId, double balance);

    /**
     * Updates the firstname of a Profile object with the specified ID.
     *
     * @param profileId the ID of the Profile object to update
     * @param firstname the new firstname of the Profile object
     * @return the number of rows affected by the update
     */
    int updateFirstname(long profileId, String firstname);

    /**
     * Updates the lastname of a Profile object with the specified ID.
     *
     * @param profileId the ID of the Profile object to update
     * @param lastname the new lastname of the Profile object
     * @return the number of rows affected by the update
     */
    int updateLastname(long profileId, String lastname);

    /**
     * Updates the email of a Profile object with the specified ID.
     *
     * @param profileId the ID of the Profile object to update
     * @param email the new email of the Profile object
     * @return the number of rows affected by the update
     */
    int updateEmail(long profileId, String email);

    /**
     * Updates the birthdate of a Profile object with the specified ID.
     *
     * @param profileId the ID of the Profile object to update
     * @param birthdate the new birthdate of the Profile object
     * @return the number of rows affected by the update
     */
    int updateBirthdate(long profileId, Date birthdate);

    int changeBalance(long profileID, double balance);

    int updatePassword(String username, String password, String oldPassword);

    Boolean isAdmin(String username);
    Optional<List<User>> getAllUser();
    long changeUnable(String username);
}

