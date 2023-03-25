package ntnu.idi.idatt2015.tokenly.backend.model;

import java.sql.Date;

/**
 * UserCreationRequest is a record class representing a user creation request containing a username and password.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public record UserCreationRequest(String username, String password,
                                  String firstname, String lastname, String email,
                                  Date birthdate) { }