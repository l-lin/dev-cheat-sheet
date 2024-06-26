package lin.louis.logging_with_aop.pet.metric;

import java.util.List;
import java.util.Optional;

import lin.louis.logging_with_aop.logging.ActionsFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PetActionsFactory implements ActionsFactory {

    private final List<?> supportedActions;

    public static ActionsFactory create(PetMetrics metrics) {
        return new PetActionsFactory(List.of(
                // success actions
                new PetMetrics.IncrementSuccessfulPurchase(metrics),

                // failure actions
                new PetMetrics.IncrementFailedPurchase(metrics)
        ));
    }

    @Override
    public <O> Optional<O> create(Class<O> clazz) {
        return this.supportedActions.stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .findFirst();
    }
}
