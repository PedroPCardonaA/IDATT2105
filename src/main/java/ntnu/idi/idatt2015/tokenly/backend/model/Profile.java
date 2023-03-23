package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * The Profile class represents a user profile in the system.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter
@Setter
public class Profile {

    /**
     * The ID of the profile.
     */
    private long profileId;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The first name of the user.
     */
    private String firstname;

    /**
     * The last name of the user.
     */
    private String lastname;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The birthdate of the user.
     */
    private Date birthdate;

    /**
     * The time the profile was created.
     */
    private Timestamp creationTime;

    /**
     * The balance of the user.
     */
    private Double balance;
}
