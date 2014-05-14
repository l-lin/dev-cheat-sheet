package exercice.duplicatesegment;

import lombok.Data;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * @author llin
 * @created 14/05/14.
 */
public class DuplicateSegment {
    public static boolean hasDuplicateSegment(String line) {
        assert line != null;

        List<Segment> segmentList = newArrayList();
        for (int i = 0; i < line.length() - 1; i++) {
            segmentList.add(new Segment(line.charAt(i), line.charAt(i + 1)));
        }

        Set<Segment> segmentSet = newHashSet(segmentList);

        return segmentList.size() != segmentSet.size();
    }

    @Data
    public static class Segment {
        private char prev;
        private char next;

        public Segment(char prev, char next) {
            this.prev = prev;
            this.next = next;
        }

        public String value() {
            if (prev > next) {
                return String.valueOf(next) + String.valueOf(prev);
            }
            return String.valueOf(prev) + String.valueOf(next);
        }

        @Override
        public boolean equals(Object s) {
            assert s instanceof Segment;
            return this.value().equals(((Segment) s).value());
        }

        @Override
        public int hashCode() {
            return prev + next;
        }
    }
}
