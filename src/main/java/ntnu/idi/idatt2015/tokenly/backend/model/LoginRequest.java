/**
 * ntnu.idi.idatt2015.tokenly.backend.model
 * Provides classes related to models and data structures in the application.
 */
package ntnu.idi.idatt2015.tokenly.backend.model;

/**
 * LoginRequest is a record class representing a user login request containing a username and password.
 */
public record LoginRequest(String username, String password) { }