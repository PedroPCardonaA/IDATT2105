/**
 * ntnu.idi.idatt2015.tokenly.backend.model
 * Provides classes related to models and data structures in the application.
 */
package ntnu.idi.idatt2015.tokenly.backend.model;

/**
 * UserCreationRequest is a record class representing a user creation request containing a username and password.
 */
public record UserCreationRequest(String username, String password,
                                  String firstName, String lastName, String email) { }