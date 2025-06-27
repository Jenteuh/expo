package be.vdab.expo;

import be.vdab.expo.tickets.Bestelling;
import be.vdab.expo.tickets.BestellingService;
import be.vdab.expo.tickets.OngeldigTicketTypeException;
import be.vdab.expo.tickets.OnvoldoendeTicketsBeschikbaarException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {

    private final BestellingService bestellingService;

    public MyRunner(BestellingService bestellingService) {
        this.bestellingService = bestellingService;
    }

    @Override
    public void run(String... args) {

        var scanner = new Scanner(System.in);
        System.out.println("Naam:");
        var naam = scanner.nextLine();
        System.out.println("Welk type ticket wilt u bestellen?");
        System.out.println("1 = Junior dag");
        System.out.println("2 = Senior dag");
        System.out.println("3 = Beide dagen");
        var ticketType = scanner.nextInt();

        try {
            var bestelling = new Bestelling(0, naam, ticketType);
            bestellingService.create(bestelling);

            var id = bestellingService.findBestellingId(bestelling);
            System.out.println("Ticket succesvol besteld");
            System.out.println("ID van uw bestelling: " + id);
        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
        catch (OngeldigTicketTypeException ex) {
            System.out.println("Ongeldig ticket type");
        }
        catch (OnvoldoendeTicketsBeschikbaarException ex) {
            System.out.println("Onvoldoende tickets beschikbaar");
        }

    }

}
