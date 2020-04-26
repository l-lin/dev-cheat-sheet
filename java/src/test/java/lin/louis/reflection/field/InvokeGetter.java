package lin.louis.reflection.field;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvokeGetter {
    @Test
    void invokeGetter() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Foo foo = new Foo();
        foo.setId(123);

        Integer id = (Integer) PropertyUtils.getProperty(foo, "id");
        assertThat(id).isEqualTo(123);
    }

    public static class Foo {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
