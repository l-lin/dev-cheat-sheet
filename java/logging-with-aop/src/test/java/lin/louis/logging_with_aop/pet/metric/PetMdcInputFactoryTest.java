package lin.louis.logging_with_aop.pet.metric;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lin.louis.logging_with_aop.logging.MdcInput;
import lin.louis.logging_with_aop.logging.MdcInputFactory;
import lin.louis.logging_with_aop.pet.OwnerName;
import lin.louis.logging_with_aop.pet.Pet;
import lin.louis.logging_with_aop.pet.logging.PetMdcInputFactory;

class PetMdcInputFactoryTest {

    private MdcInputFactory mdcInputFactory;

    @BeforeEach
    void beforeEach() {
        mdcInputFactory = PetMdcInputFactory.create();
    }

    @Test
    @DisplayName("""
            Given a pet id,
             when creating a MdcInput,
             then create with associated MDC key and value.
            """)
    void petId() {
        // GIVEN
        var petId = new Pet.Id("123");

        // WHEN
        List<MdcInput> actual = mdcInputFactory.create(petId).toList();

        // THEN
        var expected = List.of(new MdcInput("pet_id", petId.value()));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Given a owner id,
             when creating a MdcInput,
             then create with associated MDC key and value.
            """)
    void ownerId() {
        // GIVEN
        var ownerName = new OwnerName("John");

        // WHEN
        List<MdcInput> actual = mdcInputFactory.create(ownerName).toList();

        // THEN
        var expected = List.of(new MdcInput("owner_name", ownerName.value()));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Given unsupported parameter,
             when creating a MdcInput,
             then return Optional#empty
            """)
    void unsupportedParameter() {
        // GIVEN
        int unsupported = 123;

        // WHEN
        List<MdcInput> actual = this.mdcInputFactory.create(unsupported).toList();

        // THEN
        assertTrue(actual.isEmpty());
    }
}

