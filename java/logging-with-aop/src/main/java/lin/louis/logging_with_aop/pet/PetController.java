package lin.louis.logging_with_aop.pet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lin.louis.logging_with_aop.pet.FindPet.PetNotFound;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/pets")
public class PetController {

    private final PurchasePet purchasePet;
    private final FindPet findPet;

    public record PetPurchaseOrderDto(@NotBlank String petName, @NotBlank String ownerName) {}

    public record PetDto(String id, String name) {

        public static PetDto from(Pet pet) {
            return new PetDto(pet.id().value(), pet.name().value());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDto purchase(@Valid @RequestBody PetPurchaseOrderDto petPurchaseOrder) {
        return PetDto.from(purchasePet.purchase(
                new Pet.Name(petPurchaseOrder.petName()),
                new OwnerName(petPurchaseOrder.ownerName())
        ));
    }

    @GetMapping(path = "/{petId}")
    public ResponseEntity<PetDto> findById(@PathVariable String petId) {
        try {
            return ResponseEntity.ok(PetDto.from(findPet.byId(new Pet.Id(petId))));
        } catch (PetNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }
}
