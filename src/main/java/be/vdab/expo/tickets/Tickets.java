package be.vdab.expo.tickets;

public class Tickets {

    private int juniorDag;
    private int seniorDag;

    public Tickets(int juniorDag, int seniorDag) {
        this.juniorDag = juniorDag;
        this.seniorDag = seniorDag;
    }

    public long getJuniorDag() {
        return juniorDag;
    }

    public long getSeniorDag() {
        return seniorDag;
    }

    public void welkeDag(int ticketType) {
        int juniorDag = 0;
        int seniorDag = 0;
        switch (ticketType) {
            case 1: juniorDag += 1;
                break;
            case 2: seniorDag += 1;
                break;
            case 3: juniorDag += 1;
                seniorDag += 1;
        }
        this.juniorDag = juniorDag;
        this.seniorDag = seniorDag;
    }

}
