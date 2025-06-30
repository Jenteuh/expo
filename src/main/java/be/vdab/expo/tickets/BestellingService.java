package be.vdab.expo.tickets;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BestellingService {

    private final BestellingRepository bestellingRepository;
    private final TicketsRepository ticketsRepository;

    public BestellingService(BestellingRepository bestellingRepository, TicketsRepository ticketsRepository) {
        this.bestellingRepository = bestellingRepository;
        this.ticketsRepository = ticketsRepository;
    }

    @Transactional
    public void create(Bestelling bestelling) {
        if (!ticketsRepository.ticketAvailabilityCheck(bestelling.getTicketType())) {
            throw new OnvoldoendeTicketsBeschikbaarException();
        }
        bestellingRepository.create(bestelling);
        ticketsRepository.updateTicketsBeschikbaar(bestelling.getTicketType());
    }

    public long findBestellingId(Bestelling bestelling) {
        return bestellingRepository.findBestellingId(bestelling);
    }
}
