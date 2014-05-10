package lin.louis.collection;

import lombok.Data;
import org.assertj.core.data.Index;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.*;

/**
 * @author llin
 * @created 10/05/14.
 */
public class SortList {
    @Test
    public void sortWithImplementingComparable() {
        PersonImplComparable foo = new PersonImplComparable("Foo", 10);
        PersonImplComparable bar = new PersonImplComparable("Bar", 20);
        PersonImplComparable moliku = new PersonImplComparable("Moliku", 20);
        PersonImplComparable foobar = new PersonImplComparable("Foobar", 5);
        List<PersonImplComparable> personList = newArrayList(foo, moliku, bar, foobar);

        Collections.sort(personList);

        assertThat(personList).contains(foobar, Index.atIndex(0))
                .contains(foo, Index.atIndex(1))
                .contains(bar, Index.atIndex(2))
                .contains(moliku, Index.atIndex(3));
    }

    @Test
    public void sortListWithComparator() {
        Person foo = new Person("Foo", 10);
        Person bar = new Person("Bar", 20);
        Person moliku = new Person("Moliku", 20);
        Person foobar = new Person("Foobar", 5);
        List<Person> personList = newArrayList(foo, moliku, bar, foobar);

        Comparator<Person> comparator = new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                if (person1.getAge() - person2.getAge() == 0) {
                    return person1.getName().compareTo(person2.getName());
                }
                return person1.getAge() - person2.getAge();
            }
        };

        Collections.sort(personList, comparator);

        assertThat(personList).contains(foobar, Index.atIndex(0))
                .contains(foo, Index.atIndex(1))
                .contains(bar, Index.atIndex(2))
                .contains(moliku, Index.atIndex(3));
    }

    // -------------------

    @Data
    private class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    private class PersonImplComparable extends Person implements Comparable<Person> {
        public PersonImplComparable(String name, int age) {
            super(name, age);
        }

        @Override
        public int compareTo(Person person) {
            if (this.getAge() - person.getAge() == 0) {
                return this.getName().compareTo(person.getName());
            }
            return this.getAge() - person.getAge();
        }
    }
}
