package lin.louis.game.platinumrift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;

public class ContinentTest {
    @Test
    public void testContinent() {
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.remove(list.size() - 1);
//        System.out.println(list);
//        System.out.println(list.subList(0, 2));
//        System.out.println(list.subList(2, 4));
//        System.out.println(57 / 20);
        Random rand = new Random();
        List<Integer> usedIndex = new ArrayList<>(10);
        for (int i = 0; i < 100; i++) {
            int j = 0;
            int index;
            do {
                index = rand.nextInt(10);
                j++;
            } while(usedIndex.contains(index) && j < 100);
            usedIndex.add(index);
        }
    }

    public static class Foo implements Comparable<Foo> {
        int id;

        @Override
        public int compareTo(Foo o) {
            return o.id - this.id;
        }
    }
}
