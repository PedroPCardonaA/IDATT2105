package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;


/**
 * The User class represents a user in the system.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter @Setter
public class User {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * Whether the user is enabled.
     */
    private boolean enabled;

    /**
     * The profile associated with the user.
     */
    private Profile profile;
}
