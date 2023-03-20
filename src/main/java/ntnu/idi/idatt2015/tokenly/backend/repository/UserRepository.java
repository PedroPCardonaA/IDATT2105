package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.User;

import java.util.Optional;

public interface UserRepository {
    void save (User user);
    Optional<User> getUserById (long id);
    void setBalance(long id, double newBalance);

}
