package lin.louis.logging_with_aop.logging;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Factory used to create the {@link MdcInput} from method parameters.
 */
public interface MdcInputFactory {

    default List<MdcInput> create(Object[] objects) {
        if (objects == null) {
            return List.of();
        }

        return Arrays.stream(objects).flatMap(this::create).toList();
    }

    Stream<MdcInput> create(Object object);
}
