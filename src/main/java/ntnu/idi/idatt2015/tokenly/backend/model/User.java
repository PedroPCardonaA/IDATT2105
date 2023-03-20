package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private double balance;
    private Timestamp creationTime;
}

