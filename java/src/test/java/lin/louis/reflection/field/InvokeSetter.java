package lin.louis.reflection.field;

import java.lang.reflect.InvocationTargetException;

import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 14/05/14 17:00
 */
public class InvokeSetter {
    @Test
    public void invokeSetter() throws InvocationTargetException, IllegalAccessException {
        Foo foo = new Foo();
        BeanUtils.setProperty(foo, "id", 123);
        assertThat(foo.getId()).isEqualTo(123);
    }

    @Data
    public class Foo {
        private Integer id;
    }
}
