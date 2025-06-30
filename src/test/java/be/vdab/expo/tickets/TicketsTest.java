package be.vdab.expo.tickets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TicketsTest {

    @Test
    void welkeDagGeeftWaarde1BijJuisteDag() {
        var tickets = new Tickets(0,0);
        var bestelling = new Bestelling(0,"test", 3);
        tickets.welkeDag(bestelling.getTicketType());

        assertThat(tickets.getJuniorDag()).isEqualTo(1);
        assertThat(tickets.getSeniorDag()).isEqualTo(1);
    }

}
