package be.vdab.expo.tickets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import({BestellingService.class, BestellingRepository.class, TicketsRepository.class})
@Sql("/tickets.sql")
public class BestellingServiceIntegrationTest {

    private static final String TICKETS_TABLE = "tickets";
    private static final String BESTELLINGEN_TABLE = "bestellingen";
    private final BestellingService bestellingService;
    private final JdbcClient jdbcClient;

    public BestellingServiceIntegrationTest(BestellingService bestellingService, JdbcClient jdbcClient) {
        this.bestellingService = bestellingService;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void createVoegtEenBestellingToeEnWijzigtHetAantalTickets() {
        bestellingService.create(new Bestelling(0, "test", 3));
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, TICKETS_TABLE,
                "juniorDag = 19 and seniorDag = 39")).isOne();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, BESTELLINGEN_TABLE,
                "naam = 'test' and ticketType = 3")).isOne();
    }

    @Test
    void bestellingMetOnvoldoendeBeschikbareTicketsMislukt() {
        jdbcClient.sql("update tickets set juniorDag = 0").update();
        assertThatExceptionOfType(OnvoldoendeTicketsBeschikbaarException.class).isThrownBy(
                () -> bestellingService.create(new Bestelling(0, "test", 3)));
    }


}
