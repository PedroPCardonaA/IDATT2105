package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class User {
    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private boolean isAdmin;
    private String email;
    private String password;
    private double balance;
    private Date creationTime;
}

