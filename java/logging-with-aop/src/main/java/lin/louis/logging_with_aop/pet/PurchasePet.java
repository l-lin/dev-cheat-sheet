package lin.louis.logging_with_aop.pet;

import java.util.UUID;

import lin.louis.logging_with_aop.logging.Observable;
import lin.louis.logging_with_aop.pet.metric.PetMetrics;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchasePet {

    private final PetRepository petRepository;

    @Observable(
        doOnSuccessClass = PetMetrics.IncrementSuccessfulPurchase.class,
        doOnFailureClass = PetMetrics.IncrementFailedPurchase.class
    )
    public Pet purchase(Pet.Name petName, OwnerName ownerName) {
        return petRepository.save(new Pet(generatePetId(), petName));
    }

    private static Pet.Id generatePetId() {
        return new Pet.Id(UUID.randomUUID().toString());
    }
}
