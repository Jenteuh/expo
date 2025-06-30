package be.vdab.expo.tickets;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class TicketsRepository {

    private final JdbcClient jdbcClient;

    public TicketsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void updateTicketsBeschikbaar(int ticketType) {
        var tickets = new Tickets(0,0);
        tickets.welkeDag(ticketType);
        var sql = """
                UPDATE tickets
                SET juniorDag = juniorDag - ?, seniorDag = seniorDag - ?
                """;
        jdbcClient.sql(sql)
                .params(tickets.getJuniorDag(), tickets.getSeniorDag())
                .update();
    }

    public void ticketAvailabilityCheck(int ticketType) {
        var aantalJuniorTickets = jdbcClient.sql("select juniorDag from tickets")
                .query(Integer.class).single();
        var aantalSeniorTickets = jdbcClient.sql("select seniorDag from tickets")
                .query(Integer.class).single();

        switch (ticketType) {
            case 1: if (aantalJuniorTickets < 1) {
                throw new OnvoldoendeTicketsBeschikbaarException();
            }
            break;
            case 2: if (aantalSeniorTickets < 1) {
                throw new OnvoldoendeTicketsBeschikbaarException();
            }
            break;
            case 3: if (aantalJuniorTickets < 1 || aantalSeniorTickets < 1) {
                throw new OnvoldoendeTicketsBeschikbaarException();
            }
        }
    }
}
