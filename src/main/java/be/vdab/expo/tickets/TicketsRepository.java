package be.vdab.expo.tickets;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class TicketsRepository {

    private final JdbcClient jdbcClient;

    public TicketsRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void updateTicketsBeschikbaar(long ticketType) {
        int juniorDag = 0;
        int seniorDag = 0;
        switch ((int) ticketType) {
            case 1: juniorDag += 1;
                    break;
            case 2: seniorDag += 1;
                    break;
            case 3: juniorDag += 1;
                    seniorDag += 1;
        }
        var sql = """
                UPDATE tickets
                SET juniorDag = juniorDag - ?, seniorDag = seniorDag - ?
                """;
        jdbcClient.sql(sql)
                .params(juniorDag, seniorDag)
                .update();
    }

    public void ticketAvailabilityCheck(long ticketType) {
        var aantalJuniorTickets = jdbcClient.sql("select juniorDag from tickets")
                .query(Long.class).single();
        var aantalSeniorTickets = jdbcClient.sql("select seniorDag from tickets")
                .query(Long.class).single();

        switch ((int) ticketType) {
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
