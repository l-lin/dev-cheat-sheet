package lin.louis.logging_with_aop.pet.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;

public class MicrometerPetMetrics implements PetMetrics {

    private final Counter successfulPurchaseCounter;
    private final Counter failedPurchaseCounter;

    public MicrometerPetMetrics(MeterRegistry meterRegistry) {
        this.successfulPurchaseCounter = Counter.builder("purchase")
                .tags(Tags.of(Tag.of("status", "ok")))
                .description("number of paired camera")
                .register(meterRegistry);
        this.failedPurchaseCounter = Counter.builder("purchase")
                .tags(Tags.of(Tag.of("status", "ko")))
                .description("number of paired camera")
                .register(meterRegistry);
    }

    @Override
    public void incrementSuccessfulPurchase() {
        successfulPurchaseCounter.increment();
    }

    @Override
    public void incrementFailedPurchase() {
        failedPurchaseCounter.increment();
    }
}
