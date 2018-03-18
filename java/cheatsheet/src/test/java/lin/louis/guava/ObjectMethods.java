package lin.louis.guava;

import java.util.Arrays;

import com.google.common.collect.ComparisonChain;
import lombok.Data;
import org.assertj.core.data.Index;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 31/10/14 10:39
 */
public class ObjectMethods {
    @Test
    public void comparisonChain() {
        Person foo = Person.of("foo", 10);
        Person bar = Person.of("bar", 15);
        Person moliku = Person.of("moliku", 5);
        Person[] persons = new Person[]{foo, bar, moliku};

        Arrays.sort(persons);
        assertThat(persons)
                .contains(moliku, Index.atIndex(0))
                .contains(foo, Index.atIndex(1))
                .contains(bar, Index.atIndex(2));
    }

    @Data(staticConstructor = "of")
    private static class Person implements Comparable<Person> {
        private final String firstName;
        private final int age;

        @Override
        public int compareTo(Person that) {
            return ComparisonChain.start()
                    .compare(this.age, that.age)
                    .compare(this.firstName, that.firstName)
                    .result();
        }
    }
}
