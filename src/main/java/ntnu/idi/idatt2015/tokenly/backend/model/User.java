package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private String username;
    private String password;
    private boolean enable;
    private Profile profile;
}
