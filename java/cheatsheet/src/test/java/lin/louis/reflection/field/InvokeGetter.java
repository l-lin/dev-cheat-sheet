package lin.louis.reflection.field;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 14/05/14 16:50
 */
public class InvokeGetter {
    @Test
    public void invokeGetter() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Foo foo = new Foo();
        foo.setId(123);

        Integer id = (Integer) PropertyUtils.getProperty(foo, "id");
        assertThat(id).isEqualTo(123);
    }

    public class Foo {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
