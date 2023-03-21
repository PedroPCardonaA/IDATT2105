package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter @Setter
public class Profile {
    private long profileId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Date birthdate;
    private Timestamp creationTime;
}
