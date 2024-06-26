package lin.louis.logging_with_aop.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MdcInputFactoryTest {

    private MdcInputFactory mdcInputFactory;

    @BeforeEach
    void beforeEach() {
        this.mdcInputFactory = new FakeMdcInputFactory();
    }

    @Test
    @DisplayName("""
            Given multiple objects,
             when creating mdc inputs,
             then create
            """)
    void multipleObjects() {
        // GIVEN
        var inputs = new Object[] { "foo", 123, 1L, "bar" };

        // WHEN
        List<MdcInput> actual = this.mdcInputFactory.create(inputs);

        // THEN
        var expected = List.of(new MdcInput("foo", "foo"), new MdcInput("bar", "bar"));
        assertEquals(expected, actual);
    }

    private static class FakeMdcInputFactory implements MdcInputFactory {

        @Override
        public Stream<MdcInput> create(Object object) {
            if (object instanceof String str) {
                return Stream.of(new MdcInput(str, str));
            }
            return Stream.empty();
        }
    }
}
