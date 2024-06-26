package lin.louis.logging_with_aop.pet;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import lin.louis.logging_with_aop.pet.Pet.Id;
import lombok.RequiredArgsConstructor;

public interface PetRepository {

    Optional<Pet> findById(Pet.Id petId);

    Pet save(Pet pet);

    static class InMemory implements PetRepository {

        private final Map<Pet.Id, Pet> pets = new ConcurrentHashMap<>();

        @Override
        public Pet save(Pet pet) {
            pets.put(pet.id(), pet);
            return pet;
        }

        @Override
        public Optional<Pet> findById(Pet.Id petId) {
            return Optional.ofNullable(pets.get(petId));
        }
    }

    /**
     * Implementation to simulate failure.
     */
    @RequiredArgsConstructor
    static class Simulation implements PetRepository {

        private final PetRepository petRepository;

        @Override
        public Optional<Pet> findById(Id petId) {
            if (petId.value().contains("fail")) {
                throw new IllegalStateException("simulating failure");
            }
            return petRepository.findById(petId);
        }

        @Override
        public Pet save(Pet pet) {
            if (pet.name().value().contains("fail")) {
                throw new IllegalStateException("simulating failure");
            }
            return petRepository.save(pet);
        }
    }
}
