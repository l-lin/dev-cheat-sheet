package lin.louis.guava;

import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://code.google.com/p/guava-libraries/wiki/UsingAndAvoidingNullExplained
 *
 * Better works with @Nullable annotations. See http://l-lin.github.io/2014/02/27/Java-How_to_avoid_NPE/
 *
 * @author Oodrive
 * @author llin
 * @created 31/10/14 09:56
 */
public class AvoidingNull {
    private Integer val;

    @Before
    public void setUp() {
        val = 5;
    }

    @Test
    public void optional() {
        // 2 implementations of Optional: Present<T> and Absent<T>
        Optional<Integer> possible = Optional.of(val);
        assertThat(possible.isPresent()).isTrue();
        assertThat(possible.get()).isEqualTo(5);

        // This is
        if (possible.isPresent()) {
            System.out.println(possible.get());
        }
        // better to read than
        if (val != null) {
            System.out.println(val);
        }
    }

    @Test
    public void defaultValueOptional() {
        // Useful for external API when we don't know if they are null or not
        Optional<Integer> possible = Optional.fromNullable(null);
        // or(T)
        Integer otherVal = possible.or(10);
        assertThat(otherVal).isEqualTo(10);

        // orNull()
        otherVal = possible.orNull();
        assertThat(otherVal).isNull();

        // asSet()
        Set<Integer> set = possible.asSet();
        assertThat(set).isEmpty();
    }

    @Test
    public void firstNonNull() {
        Integer result = Objects.firstNonNull(null, val);
        assertThat(result).isEqualTo(5);

        result = Objects.firstNonNull(val, null);
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void emptyToNull() {
        String str = Strings.emptyToNull(null);
        assertThat(str).isNull();
        str = Strings.emptyToNull("");
        assertThat(str).isNull();
        str = Strings.emptyToNull("foobar");
        assertThat(str).isNotNull();
        assertThat(str).isEqualTo("foobar");
    }

    @Test
    public void isNullOrEmpty() {
        // same as StringUtils.isEmpty(String)
        assertThat(Strings.isNullOrEmpty(null)).isTrue();
        assertThat(Strings.isNullOrEmpty("")).isTrue();
        assertThat(Strings.isNullOrEmpty(" ")).isFalse();
    }

    @Test
    public void nullToEmpty() {
        String str = Strings.nullToEmpty(null);
        assertThat(str).isEqualTo("");

        str = Strings.nullToEmpty("");
        assertThat(str).isEqualTo("");

        str = Strings.nullToEmpty("foobar");
        assertThat(str).isEqualTo("foobar");
    }
}
