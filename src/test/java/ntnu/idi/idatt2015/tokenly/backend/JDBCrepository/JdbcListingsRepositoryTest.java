package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@JdbcTest
public class JdbcListingsRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcListingsRepository jdbcListingsRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS LISTINGS");
        jdbcTemplate.execute("CREATE TABLE LISTINGS (LISTING_ID BIGINT PRIMARY KEY AUTO_INCREMENT, ITEM_ID BIGINT NOT NULL, MIN_PRICE DECIMAL(19, 4), MAX_PRICE DECIMAL(19, 4))");

        // Create an instance of JdbcListingsRepository
        jdbcListingsRepository = new JdbcListingsRepository(jdbcTemplate);
    }

    @Test
    void getByListingIdTest() {
        // Insert a listing into the table for testing purposes
        jdbcTemplate.execute("INSERT INTO LISTINGS (ITEM_ID, MIN_PRICE, MAX_PRICE) VALUES (1, 100.0, 200.0)");

        // Test the getByListingId method
        Optional<Listing> listingOptional = jdbcListingsRepository.getByListingId(1L);
        assertThat(listingOptional).isNotEmpty();
        assertThat(listingOptional.get().getListingId()).isEqualTo(1L);
        assertThat(listingOptional.get().getItemId()).isEqualTo(1L);
        assertThat(listingOptional.get().getMinPrice()).isEqualTo(100.0);
        assertThat(listingOptional.get().getMaxPrice()).isEqualTo(200.0);
    }
}
