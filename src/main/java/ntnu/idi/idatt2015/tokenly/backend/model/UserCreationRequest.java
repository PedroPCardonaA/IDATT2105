/**
 * ntnu.idi.idatt2015.tokenly.backend.model
 * Provides classes related to models and data structures in the application.
 */
package ntnu.idi.idatt2015.tokenly.backend.model;

import java.sql.Date;

/**
 * UserCreationRequest is a record class representing a user creation request containing a username and password.
 */
public record UserCreationRequest(String username, String password,
                                  String firstname, String lastname, String email,
                                  Date birthdate) { }