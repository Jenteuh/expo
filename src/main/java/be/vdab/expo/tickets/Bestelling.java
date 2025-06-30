package be.vdab.expo.tickets;

public class Bestelling {

    private long id;
    private String naam;
    private int ticketType;

    public Bestelling(long id, String naam, int ticketType) {

        if (naam.isEmpty()) {
            throw new IllegalArgumentException("Naam moet ingevuld zijn");
        }

        if (ticketType <= 0 || ticketType >= 4) {
            throw new OngeldigTicketTypeException();
        }

        this.id = id;
        this.naam = naam;
        this.ticketType = ticketType;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getTicketType() {
        return ticketType;
    }
}
