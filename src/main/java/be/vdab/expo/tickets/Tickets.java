package be.vdab.expo.tickets;

public class Tickets {

    private long juniorDag;
    private long seniorDag;

    public Tickets(long juniorDag, long seniorDag) {
        this.juniorDag = juniorDag;
        this.seniorDag = seniorDag;
    }

    public long getJuniorDag() {
        return juniorDag;
    }

    public long getSeniorDag() {
        return seniorDag;
    }

}
