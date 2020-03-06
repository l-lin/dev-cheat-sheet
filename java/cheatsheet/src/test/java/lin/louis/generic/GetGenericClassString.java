package lin.louis.generic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class GetGenericClassString extends GetGenericClass<String> {
    public GetGenericClassString() {
        super();
    }

    @Test
    public void testGetGenericClass() {
        GetGenericClassString getGenericClass = new GetGenericClassString();
        assertThat(getGenericClass.getType()).isEqualTo(String.class);
    }
}
