package lin.louis.data_structures.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class TimeHelperTest {
    private static final LocalDateTime NOW = LocalDateTime.parse("2025-02-09T18:30:31");

    @RegisterExtension
    private static TimeTraveler TIME_TRAVELER = TimeTraveler.travelTo(NOW);

    @Test
    void currentTimeHasBeenStubbed() {
        var actual = TimeHelper.nowUTC();

        assertThat(actual).isEqualTo(NOW);
    }

    @Test
    void currentTimeCanBeInjected(LocalDateTime now) {
        assertThat(now).isEqualTo(NOW);
    }
}
