package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Profile;

import ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * The JdbcProfileRepository class provides an implementation of the ProfileRepository interface.
 * This class is responsible for performing CRUD operations on the 'profiles' table in the database.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Repository
public class JdbcProfileRepository implements ProfileRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor for JdbcProfileRepository.
     * The JdbcTemplate is automatically injected by Spring.
     *
     * @param jdbcTemplate the JdbcTemplate to be used by namedParameterJdbcTemplate.
     */
    public JdbcProfileRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Saves a new Profile instance to the database.
     *
     * @param profile The Profile instance to save.
     */
    @Override
    public void save(Profile profile) {
        String sql = "INSERT INTO PROFILES (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDATE) VALUES(:username, :first_name, :last_name, :email, :birthdate)";
        Map<String, Object> params = new HashMap<>();
        params.put("username", profile.getUsername());
        params.put("first_name", profile.getFirst_name());
        params.put("last_name", profile.getLast_name());
        params.put("email", profile.getEmail());
        params.put("birthdate", profile.getBirthdate());
        namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Retrieves a Profile instance by its profileId from the database.
     *
     * @param profileId The profileId of the Profile to retrieve.
     * @return An Optional containing the Profile if found, or an empty Optional otherwise.
     */
    @Override
    public Optional<Profile> getByProfileId(Long profileId) {
        String sql = "SELECT * FROM profiles WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", profileId);
        try {
            Profile profile = namedParameterJdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Profile.class));
            return Optional.ofNullable(profile);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves a Profile instance by its username from the database.
     *
     * @param username The username of the Profile to retrieve.
     * @return An Optional containing the Profile if found, or an empty Optional otherwise.
     */
    @Override
    public Optional<Profile> getByUsername(String username) {
        String sql = "SELECT * FROM profiles WHERE username = :username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            Profile profile = namedParameterJdbcTemplate.queryForObject(sql, params,
                    BeanPropertyRowMapper.newInstance(Profile.class));
            return Optional.ofNullable(profile);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Updates the balance of a Profile instance with the given profileId in the database.
     *
     * @param profileId The profileId of the Profile to update.
     * @param balance The new balance value.
     * @return The number of rows affected by the update.
     */
    @Override
    public int updateBalance(long profileId, double balance) {
        String sql = "UPDATE profiles SET balance = :balance WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("balance", balance);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Updates the first name of a Profile instance with the given profileId in the database.
     *
     * @param profileId The profileId of the Profile to update.
     * @param firstname The new first name value.
     * @return The number of rows affected by the update.
     */
    @Override
    public int updateFirstname(long profileId, String firstname) {
        String sql = "UPDATE profiles SET first_name = :first_name WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("first_name", firstname);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Updates the last name of a Profile instance with the given profileId in the database.
     *
     * @param profileId The profileId of the Profile to update.
     * @param lastname The new last name value.
     * @return The number of rows affected by the update.
     */
    @Override
    public int updateLastname(long profileId, String lastname) {
        String sql = "UPDATE profiles SET last_name = :last_name WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("last_name", lastname);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Updates the email of a Profile instance with the given profileId in the database.
     *
     * @param profileId The profileId of the Profile to update.
     * @param email The new email value.
     * @return The number of rows affected by the update.
     */
    @Override
    public int updateEmail(long profileId, String email) {
        String sql = "UPDATE profiles SET email = :email WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("email", email);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * Updates the birthdate of a Profile instance with the given profileId in the database.
     *
     * @param profileId The profileId of the Profile to update.
     * @param birthdate The new birthdate value.
     * @return The number of rows affected by the update.
     */
    @Override
    public int updateBirthdate(long profileId, Date birthdate) {
        String sql = "UPDATE profiles SET birthdate = :birthdate WHERE id = :profileId";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("profileId", profileId)
                .addValue("birthdate", birthdate);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public int changeBalance(long profile, double balance) {
        String sql = "UPDATE profiles SET balance = balance + :balance WHERE id = :profileId";
        Map<String,Object> params = new HashMap<>();
        params.put("profileId", profile);
        params.put("balance", balance);
        try {
            if ((double) balance < (double) 0.0 ){
                System.out.println("balance to add: " + balance);
                String getSql = "SELECT balance FROM profiles WHERE id = :profileId";
                double currentBalance = namedParameterJdbcTemplate.queryForObject(getSql,params, Double.class);
                System.out.println("new balance" + balance);
                if( ((double) currentBalance + (double) balance) < (double) 0.0){
                    return -1;
                }
            }
            return namedParameterJdbcTemplate.update(sql,params);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }


}
