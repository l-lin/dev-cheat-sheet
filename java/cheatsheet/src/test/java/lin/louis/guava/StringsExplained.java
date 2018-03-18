package lin.louis.guava;

import java.util.List;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.assertj.core.data.Index;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 31/10/14 10:49
 */
public class StringsExplained {
    @Test
    public void joiner() {
        Joiner joiner = Joiner.on(";").skipNulls();
        String str = joiner.join("foo", "bar", "moliku");
        assertThat(str).isEqualTo("foo;bar;moliku");
    }

    @Test
    public void splitter() {
        Splitter splitter = Splitter.on(";").omitEmptyStrings().trimResults();
        List<String> results = splitter.splitToList("foo;bar;;foo bar ");
        assertThat(results).hasSize(3)
                .contains("foo", Index.atIndex(0))
                .contains("bar", Index.atIndex(1))
                .contains("foo bar", Index.atIndex(2));
    }

    @Test
    public void caseFormat() {
        String camel = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "FOO_BAR");
        assertThat(camel).isEqualTo("fooBar");
    }
}
