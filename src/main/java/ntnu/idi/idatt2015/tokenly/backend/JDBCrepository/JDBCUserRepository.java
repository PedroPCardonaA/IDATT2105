package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.User;
import ntnu.idi.idatt2015.tokenly.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JDBCUserRepository implements UserRepository {

    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JDBCUserRepository (JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void save(User user) {
        String sql = "Insert INTO USERS (USER_NAME,FIRST_NAME,LAST_NAME,IS_ADMIN,EMAIL,PASSWORD) " +
                "VALUES (:username, :firstname, :lastname, :isAdmin, :password)";
        Map<String, Object> params = new HashMap<>();
        params.put("username",user.getUsername());
        params.put("firstname",user.getFirstname());
        params.put("lastname", user.getLastname());
        params.put("isAdmin", user.isAdmin());
        params.put("password",user.getPassword());
        namedParameterJdbcTemplate.update(sql,params);
    }

    @Override
    public Optional<User> getUserById(long id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = :userId";
        Map<String,Object> params = new HashMap<>();
        params.put("userId",id);
        try{
            User user = namedParameterJdbcTemplate.queryForObject(sql,params, BeanPropertyRowMapper.newInstance(User.class));
            return  Optional.ofNullable(user);
        }catch (IncorrectResultSizeDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void setBalance(long id, double newBalance) {
        String sql = "UPDATE USERS SET BALANCE = :newBalance WHERE USER_ID = :userId";
        Map<String,Object> params = new HashMap<>();
        params.put("newBalance", newBalance);
        params.put("userId", id);
        namedParameterJdbcTemplate.update(sql,params);
    }
}
