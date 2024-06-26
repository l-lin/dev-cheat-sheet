package lin.louis.logging_with_aop.logging;

import java.util.Optional;

/**
 * Factory used to create the instance of the action to perform on success / failure.
 */
public interface ActionsFactory {

    <O> Optional<O> create(Class<O> clazz);
}
