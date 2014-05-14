package lin.louis.clazz;

import javax.annotation.Nullable;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

import static com.google.common.base.Preconditions.checkArgument;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 14/05/14 16:31
 */
public class GetInterfaceClasses {

    @Test
    public void getInterfaceClasses() {
        assertThat(getInterfaceClasses(FooImpl.class)).isNotNull().isNotEmpty().contains(Foo.class);
        assertThat(getInterfaceClasses(FooBarImpl.class)).isNotNull().isNotEmpty().contains(Foo.class).contains(Bar.class);
        assertThat(getInterfaceClasses(NestedFooBarImpl.class)).isNotNull().isNotEmpty().contains(Foo.class).contains(Bar.class);
    }

    /**
     * Gets the interfaces of the given class.
     * If the class does not implement any interface, then thrown an IllegalArgumentException!
     * Returns at least ONE element!
     *
     * @param clazz the clazz
     * @return the interface class
     */
    public static Class<?>[] getInterfaceClasses(@Nullable Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        List<Class<?>> classList = ClassUtils.getAllInterfaces(clazz);
        checkArgument(!classList.isEmpty(), "Could not find the interfaces for " + clazz);
        return classList.toArray(new Class[classList.size()]);
    }

    private interface Foo {}

    private interface Bar {}

    private class FooImpl implements Foo {}

    private class FooBarImpl implements Foo, Bar {}

    private class NestedFooBarImpl extends FooImpl implements Bar {}
}
