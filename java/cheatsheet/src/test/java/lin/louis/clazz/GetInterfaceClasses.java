package lin.louis.clazz;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.junit.jupiter.api.Test;


class GetInterfaceClasses {

    @Test
    public void getInterfaceClasses() {
        assertThat(getInterfaceClasses(FooImpl.class)).isNotNull().isNotEmpty().contains(Foo.class);
        assertThat(getInterfaceClasses(FooBarImpl.class)).isNotNull().isNotEmpty().contains(Foo.class).contains(Bar.class);
        assertThat(getInterfaceClasses(NestedFooBarImpl.class)).isNotNull().isNotEmpty().contains(Foo.class).contains(Bar.class);
    }

    public static Class<?>[] getInterfaceClasses(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        List<Class<?>> classList = ClassUtils.getAllInterfaces(clazz);
        return classList.toArray(new Class[0]);
    }

    private interface Foo {}

    private interface Bar {}

    private static class FooImpl implements Foo {}

    private static class FooBarImpl implements Foo, Bar {}

    private static class NestedFooBarImpl extends FooImpl implements Bar {}
}
