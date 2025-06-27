package be.vdab.expo.tickets;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BestellingTest {

    @Test
    void eenBestellingDieLukt() {
        new Bestelling(0,"test", 2);
    }

    @Test
    void naamMoetIngevuldZijn() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> new Bestelling(0,"", 2));
    }

    @Test
    void ongeldigTicketTypeBestellenMislukt() {
        assertThatExceptionOfType(OngeldigTicketTypeException.class).isThrownBy(
                () -> new Bestelling(0,"test", 69));
    }

}
