package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Bid;
import ntnu.idi.idatt2015.tokenly.backend.repository.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(JdbcBidRepository.class)
public class JdbcBidRepositoryTest {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS BID");
        jdbcTemplate.execute("CREATE TABLE BID (BID_ID BIGINT PRIMARY KEY AUTO_INCREMENT, LISTING_ID BIGINT NOT NULL, BUYER_NAME VARCHAR(255) NOT NULL, PRICE DECIMAL(19, 4) NOT NULL)");
    }

    @Test
    void testSave() {
        Bid bid = new Bid();
        bid.setListingId(1);
        bid.setBuyerName("TestBuyer");
        bid.setPrice(100.0);

        Bid savedBid = bidRepository.save(bid);

        assertThat(savedBid).isNotNull();
        assertEquals(1, savedBid.getListingId());
        assertEquals("TestBuyer", savedBid.getBuyerName());
        assertEquals(100.0, savedBid.getPrice());
        assertThat(savedBid.getBidId()).isGreaterThan(0);
    }

    @Test
    void testGetBidById() {
        jdbcTemplate.execute("INSERT INTO BID (LISTING_ID, BUYER_NAME, PRICE) VALUES (1, 'TestBuyer', 100.0)");

        Optional<Bid> bid = bidRepository.getBidById(1);

        assertThat(bid.isPresent()).isTrue();
        assertEquals(1, bid.get().getListingId());
        assertEquals("TestBuyer", bid.get().getBuyerName());
        assertEquals(100.0, bid.get().getPrice());
        assertEquals(1, bid.get().getBidId());
    }

    @Test
    void testGetAllBidByBuyerName() {
        jdbcTemplate.execute("INSERT INTO BID (LISTING_ID, BUYER_NAME, PRICE) VALUES (1, 'TestBuyer', 100.0)");
        jdbcTemplate.execute("INSERT INTO BID (LISTING_ID, BUYER_NAME, PRICE) VALUES (2, 'TestBuyer', 200.0)");

        Optional<List<Bid>> bids = bidRepository.getAllBidByBuyerName("TestBuyer");

        assertThat(bids.isPresent()).isTrue();
        assertThat(bids.get()).hasSize(2);
    }

    @Test
    void testGetAllBidByListingId() {
        jdbcTemplate.execute("INSERT INTO BID (LISTING_ID, BUYER_NAME, PRICE) VALUES (1, 'TestBuyer1', 100.0)");
        jdbcTemplate.execute("INSERT INTO BID (LISTING_ID, BUYER_NAME, PRICE) VALUES (1, 'TestBuyer2', 150.0)");

        Optional<List<Bid>> bids = bidRepository.getAllBidByListingId(1);

        assertThat(bids.isPresent()).isTrue();
        assertThat(bids.get()).hasSize(2);
    }

    @Test
    void testGetBidById_notFound() {
        Optional<Bid> bid = bidRepository.getBidById(1);

        assertThat(bid.isPresent()).isFalse();
    }

    @Test
    void testGetAllBidByBuyerName_notFound() {
        jdbcTemplate.execute("INSERT INTO BID (LISTING_ID, BUYER_NAME, PRICE) VALUES (1, 'TestBuyer1', 100.0)");

        Optional<List<Bid>> bids = bidRepository.getAllBidByBuyerName("NonExistentBuyer");

        assertThat(bids.isPresent()).isTrue();
        assertThat(bids.get()).isEmpty();
    }

    @Test
    void testGetAllBidByListingId_notFound() {
        jdbcTemplate.execute("INSERT INTO BID (LISTING_ID, BUYER_NAME, PRICE) VALUES (1, 'TestBuyer1', 100.0)");

        Optional<List<Bid>> bids = bidRepository.getAllBidByListingId(2);

        assertThat(bids.isPresent()).isTrue();
        assertThat(bids.get()).isEmpty();
    }
}