package lin.louis.data_structures.generic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GetGenericClassString extends GetGenericClass<String> {

    public GetGenericClassString() {
        super();
    }

    @Test
    void testGetGenericClass() {
        GetGenericClassString getGenericClass = new GetGenericClassString();
        assertThat(getGenericClass.getType()).isEqualTo(String.class);
    }
}
