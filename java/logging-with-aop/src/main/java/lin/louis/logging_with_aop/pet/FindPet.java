package lin.louis.logging_with_aop.pet;

import lin.louis.logging_with_aop.logging.Observable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindPet {

    private final PetRepository petRepository;

    @Observable
    public Pet byId(Pet.Id petId) {
        return petRepository.findById(petId).orElseThrow(() -> new PetNotFound());
    }

    public static class PetNotFound extends RuntimeException {}
}
