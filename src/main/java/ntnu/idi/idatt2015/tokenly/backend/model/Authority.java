package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;
/**
 * Class representing an Authority.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter @Setter
public class Authority {
    /**
     * The username of the user.
     */
    private String username;
    /**
     * The authority of the user.
     */
    private String authority;

}
