package ntnu.idi.idatt2015.tokenly.backend.model;

/**
 * UserCreationResponse is a record class representing a user creation response containing a username and email.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public record UserCreationResponse(String username, String email,
                                   String firstname, String lastname) {
}
