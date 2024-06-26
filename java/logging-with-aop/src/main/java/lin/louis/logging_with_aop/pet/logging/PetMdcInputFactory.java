package lin.louis.logging_with_aop.pet.logging;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import lin.louis.logging_with_aop.logging.MdcInput;
import lin.louis.logging_with_aop.logging.MdcInputFactory;
import lin.louis.logging_with_aop.pet.OwnerName;
import lin.louis.logging_with_aop.pet.Pet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PetMdcInputFactory implements MdcInputFactory {

    private final Map<Class<?>, Mapper<?>> mappers;

    public static MdcInputFactory create() {
        return new PetMdcInputFactory(Map.of(
            Pet.Id.class, (Mapper<Pet.Id>) petId -> Stream.of(new MdcInput("pet_id", petId.value())),
            Pet.Name.class, (Mapper<Pet.Name>) petName -> Stream.of(new MdcInput("pet_name", petName.value())),
            OwnerName.class, (Mapper<OwnerName>) ownerName -> Stream.of(new MdcInput("owner_name", ownerName.value()))
        ));
    }

    @Override
    public Stream<MdcInput> create(Object o) {
        return Optional.ofNullable(this.mappers.get(o.getClass()))
                .map(mapper -> mapper.map(o))
                .orElse(Stream.empty());
    }

    private interface Mapper<I> extends Function<I, Stream<MdcInput>> {

        default Stream<MdcInput> map(Object o) {
            return apply((I) o);
        }
    }
}
