package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.User;

public interface UserRepository {
    void save (User user);
    User getUserById (long id);
    void setBalance(double newBalance);

}
