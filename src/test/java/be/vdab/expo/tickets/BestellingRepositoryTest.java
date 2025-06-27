package be.vdab.expo.tickets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BestellingRepository.class)
@Sql("/bestellingen.sql")
class BestellingRepositoryTest {

    private static final String BESTELLINGEN_TABLE = "bestellingen";

    private final BestellingRepository bestellingRepository;
    private final JdbcClient jdbcClient;

    BestellingRepositoryTest(BestellingRepository bestellingRepository, JdbcClient jdbcClient) {
        this.bestellingRepository = bestellingRepository;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void createVoegtEenBestellingToe() {
        bestellingRepository.create(new Bestelling(0,"test3", 2));
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient, BESTELLINGEN_TABLE,
                "naam = 'test3' and ticketType = 2");
        assertThat(aantalRecords).isOne();
    }

    @Test
    void findBestellingIdVindtJuisteId() {
        var bestelling = jdbcClient.sql("select * from bestellingen where naam = 'test1'")
                        .query(Bestelling.class).single();
        assertThat(bestellingRepository.findBestellingId(bestelling)).isEqualTo(1);
    }
}
