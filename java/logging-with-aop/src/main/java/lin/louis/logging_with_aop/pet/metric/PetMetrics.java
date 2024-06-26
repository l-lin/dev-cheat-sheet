package lin.louis.logging_with_aop.pet.metric;

import java.util.function.Consumer;

import lin.louis.logging_with_aop.pet.Pet;
import lombok.RequiredArgsConstructor;

public interface PetMetrics {

    void incrementSuccessfulPurchase();

    void incrementFailedPurchase();

    @RequiredArgsConstructor
    class IncrementSuccessfulPurchase implements Consumer<Pet> {

        private final PetMetrics metrics;

        @Override
        public void accept(Pet unused) {
            this.metrics.incrementSuccessfulPurchase();
        }
    }

    @RequiredArgsConstructor
    class IncrementFailedPurchase implements Consumer<Exception> {

        private final PetMetrics metrics;

        @Override
        public void accept(Exception exception) {
            this.metrics.incrementFailedPurchase();
        }
    }
}
