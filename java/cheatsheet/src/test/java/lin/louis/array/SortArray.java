package lin.louis.array;

import lombok.Data;
import org.assertj.core.data.Index;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author llin
 * @created 10/05/14.
 */
public class SortArray {

    @Test
    public void sortArray() {
        Person foo = new Person("Foo", 10);
        Person bar = new Person("Bar", 20);
        Person moliku = new Person("Moliku", 20);
        Person foobar = new Person("Foobar", 5);
        Person[] persons = new Person[]{foo, moliku, bar, foobar};

        Comparator<Person> comparator = new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                if (person1.getAge() - person2.getAge() == 0) {
                    return person1.getName().compareTo(person2.getName());
                }
                return person1.getAge() - person2.getAge();
            }
        };

        Arrays.sort(persons, comparator);

        assertThat(persons).contains(foobar, Index.atIndex(0))
                .contains(foo, Index.atIndex(1))
                .contains(bar, Index.atIndex(2))
                .contains(moliku, Index.atIndex(3));
    }

    @Data
    private class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
