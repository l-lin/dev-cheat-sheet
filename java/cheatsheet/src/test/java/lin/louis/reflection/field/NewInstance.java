package lin.louis.reflection.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class NewInstance {
    @Test
    public void newInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String foo = String.class.getConstructor().newInstance();
        assertThat(foo).isNotNull();
    }
}
