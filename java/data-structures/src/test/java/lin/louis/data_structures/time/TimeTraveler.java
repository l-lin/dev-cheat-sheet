package lin.louis.data_structures.time;

import java.time.LocalDateTime;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class TimeTraveler implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private final LocalDateTime now;

    /**
     * Represents the level for which the tests are located. We need to track it
     * because we can have multiple nested tests.
     */
    private int testLevel;

    public static TimeTraveler travelTo(LocalDateTime dateTime) {
        return new TimeTraveler(dateTime);
    }

    private TimeTraveler(LocalDateTime now) {
        this.now = now;
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        timeKeeperThreadLocal().set(now);
        testLevel++;
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        testLevel--;
        if (testLevel == 0) {
            timeKeeperThreadLocal().remove();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == LocalDateTime.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return now;
    }

    @SuppressWarnings("unchecked")
    private ThreadLocal<LocalDateTime> timeKeeperThreadLocal() throws NoSuchFieldException, IllegalAccessException {
        var nowField = TimeHelper.class.getDeclaredField("NOW");
        nowField.setAccessible(true);
        return (ThreadLocal<LocalDateTime>) nowField.get(null);
    }
}
