package be.vdab.expo.tickets;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class BestellingRepository {

    private final JdbcClient jdbcClient;

    public BestellingRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void create(Bestelling bestelling) {
        var sql = """
                insert into bestellingen(naam, ticketType)
                values (?, ?)
                """;
        jdbcClient.sql(sql)
                .params(bestelling.getNaam(), bestelling.getTicketType())
                .update();
    }

    public long findBestellingId(Bestelling bestelling) {
        var sql = """
                select id
                from bestellingen
                where naam = ? and ticketType = ?;
                """;
        return jdbcClient.sql(sql)
                .params(bestelling.getNaam(), bestelling.getTicketType())
                .query(Long.class)
                .single();
    }
}
