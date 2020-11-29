package lin.louis.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


class ArrayListOrLinkedList {
    private static final int MAX_ITERATIONS = 1_000_000;

    private static final int NB_COMPARISON = 100;

    private final List<Foo> arrayList = new ArrayList<>(MAX_ITERATIONS);

    private final List<Foo> linkedList = new LinkedList<>();

    @BeforeEach
    void setUp() {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            arrayList.add(new Foo());
            linkedList.add(new Foo());
        }
    }

    /**
     * ArrayList is the winner
     */
    @Test
    @Disabled
    void testPerformanceForLoop() {
        int nbTestArraySlowerLinked = 0;
        int nbTestLinkedSlowerArray = 0;
        for (int i = 0; i < NB_COMPARISON; i++) {
            long arrayListStartTime = System.currentTimeMillis();
            for (int j = 0; j < MAX_ITERATIONS; j++) {
                Integer id = arrayList.get(i).getId();
            }
            long arrayListEndTime = System.currentTimeMillis();
            long arrayListExecTime = arrayListEndTime - arrayListStartTime;

            long linkedListStartTime = System.currentTimeMillis();
            for (int j = 0; j < MAX_ITERATIONS; j++) {
                Integer id = linkedList.get(i).getId();
            }
            long linkedListEndTime = System.currentTimeMillis();
            long linkedListExecTime = linkedListEndTime - linkedListStartTime;

            if (linkedListExecTime - arrayListExecTime < 0) {
                nbTestArraySlowerLinked++;
            } else {
                nbTestLinkedSlowerArray++;
            }
        }

        displayResult(nbTestArraySlowerLinked, nbTestLinkedSlowerArray);
    }

    /**
     * ArrayList is the winner
     */
    @Test
    @Disabled
    void testPerformanceForEachLoop() {
        int nbTestArraySlowerLinked = 0;
        int nbTestLinkedSlowerArray = 0;
        for (int i = 0; i < NB_COMPARISON; i++) {
            long arrayListStartTime = System.currentTimeMillis();
            for (Foo foo : arrayList) {
                Integer id = foo.getId();
            }
            long arrayListEndTime = System.currentTimeMillis();
            long arrayListExecTime = arrayListEndTime - arrayListStartTime;

            long linkedListStartTime = System.currentTimeMillis();
            for (Foo foo : linkedList) {
                Integer id = foo.getId();
            }
            long linkedListEndTime = System.currentTimeMillis();
            long linkedListExecTime = linkedListEndTime - linkedListStartTime;

            if (linkedListExecTime - arrayListExecTime < 0) {
                nbTestArraySlowerLinked++;
            } else {
                nbTestLinkedSlowerArray++;
            }
        }

        displayResult(nbTestArraySlowerLinked, nbTestLinkedSlowerArray);
    }

    /**
     * ArrayList is the winner
     */
    @Test
    @Disabled
    void testPerformanceIteratorLoop() {
        int nbTestArraySlowerLinked = 0;
        int nbTestLinkedSlowerArray = 0;
        for (int i = 0; i < NB_COMPARISON; i++) {
            long arrayListStartTime = System.currentTimeMillis();
            Iterator<Foo> iterator = arrayList.iterator();
            while (iterator.hasNext()) {
                Integer id = iterator.next().getId();
            }
            long arrayListEndTime = System.currentTimeMillis();
            long arrayListExecTime = arrayListEndTime - arrayListStartTime;

            long linkedListStartTime = System.currentTimeMillis();
            iterator = linkedList.iterator();
            while (iterator.hasNext()) {
                Integer id = iterator.next().getId();
            }
            long linkedListEndTime = System.currentTimeMillis();
            long linkedListExecTime = linkedListEndTime - linkedListStartTime;

            if (linkedListExecTime - arrayListExecTime < 0) {
                nbTestArraySlowerLinked++;
            } else {
                nbTestLinkedSlowerArray++;
            }
        }

        displayResult(nbTestArraySlowerLinked, nbTestLinkedSlowerArray);
    }

    private void displayResult(final int nbTestArraySlowerLinked, final int nbTestLinkedSlowerArray) {
        System.out.println("Nb test where ArrayList is slower than LinkedList: " + nbTestArraySlowerLinked);
        System.out.println("Nb test where LinkedList is slower than ArrayList: " + nbTestLinkedSlowerArray);
        String fasterList = nbTestLinkedSlowerArray - nbTestArraySlowerLinked > 0 ? "ArrayList" : "LinkedList";
        System.out.println("The fastest is: " + fasterList);
    }

    public static class Foo implements Serializable {
        private static final long serialVersionUID = -7103199952150233156L;
        private Integer id;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}
	}
}
