package lin.louis.data_structures.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * The Java API, such as {@link LocalDateTime#now()}, {@link Instant#now()}, or {@link Clock#systemUTC()}, can be
 * difficult to stub because they are closely coupled with one another. Stubbing one can be quite challenging.
 * While we can use <a href="https://www.baeldung.com/mockito-mock-static-methods">Mockito static methods</a>,
 * some libraries use one of the three above (or any variant with some parameters), which might generate
 * unexpected behavior in your tests or, even worse, cause flaky tests.
 * <br/>
 * One option is to create a helper that your code MUST use to get the current date and time instead of using
 * the Java ones. This way, it's easier to stub and does not impact the behavior of the libraries.
 * <br/>
 * However, the drawback is that you need to use this specific helper in your code, and if you expect some
 * specific behavior from the libraries based on the current time, this option won't work.
 */
public class TimeHelper {

    /**
     * No setter is exposed to ensure that only tests using {@link TimeTraveler} can set the value of the thread-local
     * variable via reflection. This prevents the implementation code from accidentally modifying the current time.
     */
    private static final ThreadLocal<LocalDateTime> NOW = new ThreadLocal<>();

    public static LocalDateTime nowUTC() {
        var now = NOW.get();
        return now != null ? now : LocalDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDate todayUTC() {
        return nowUTC().toLocalDate();
    }

    private TimeHelper() {}
}
