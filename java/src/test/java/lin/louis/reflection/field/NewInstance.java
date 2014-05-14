package lin.louis.reflection.field;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 14/05/14 17:02
 */
public class NewInstance {
    @Test
    public void newInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String foo = String.class.getConstructor().newInstance();
        assertThat(foo).isNotNull();
    }
}
