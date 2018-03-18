package lin.louis.generic;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 19/09/14 14:28
 */
public class GetGenericClassString extends GetGenericClass<String> {
    public GetGenericClassString() {
        super();
    }

    /**
     * Test get generic class.
     */
    @Test
    public void testGetGenericClass() {
        GetGenericClassString getGenericClass = new GetGenericClassString();
        assertThat(getGenericClass.getType()).isEqualTo(String.class);
    }
}
