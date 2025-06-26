package be.vdab.expo.tickets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@JdbcTest
@Import(TicketsRepository.class)
@Sql("/tickets.sql")
class TicketsRepositoryTest {

    private static final String TICKETS_TABLE = "tickets";
    private final TicketsRepository ticketsRepository;
    private final JdbcClient jdbcClient;
    private Bestelling bestelling;

    public TicketsRepositoryTest(TicketsRepository ticketsRepository, JdbcClient jdbcClient) {
        this.ticketsRepository = ticketsRepository;
        this.jdbcClient = jdbcClient;
    }

    @BeforeEach
    void beforeEach() {
        bestelling = new Bestelling(0, "test", 1);
    }

    @Test
    void updateTicketsBeschikbaarWijzigtHetAantalTickets() {
        ticketsRepository.updateTicketsBeschikbaar(bestelling.getTicketType());
        var aantalRecords = JdbcTestUtils.countRowsInTableWhere(jdbcClient,
                TICKETS_TABLE, "juniorDag = 19 and seniorDag = 40");
        assertThat(aantalRecords).isOne();

    }

    @Test
    void ticketAvailabilityCheckGooitOnvoldoendeTicketsBeschikbaarException() {
        jdbcClient.sql("update tickets set juniorDag = 0").update();
        assertThatExceptionOfType(OnvoldoendeTicketsBeschikbaarException.class)
                .isThrownBy(() -> ticketsRepository.ticketAvailabilityCheck(bestelling.getTicketType()));
    }
}
