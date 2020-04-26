package lin.louis.exercice.duplicatesegment;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DuplicateSegment {
    public static boolean hasDuplicateSegment(String line) {
        assert line != null;

        List<Segment> segmentList = new ArrayList<>();
        for (int i = 0; i < line.length() - 1; i++) {
            segmentList.add(new Segment(line.charAt(i), line.charAt(i + 1)));
        }

        Set<Segment> segmentSet = new HashSet<>(segmentList);

        return segmentList.size() != segmentSet.size();
    }

    public static class Segment {
        private char prev;
        private char next;

        public Segment(char prev, char next) {
            this.prev = prev;
            this.next = next;
        }

		public char getPrev() {
			return prev;
		}

		public void setPrev(char prev) {
			this.prev = prev;
		}

		public char getNext() {
			return next;
		}

		public void setNext(char next) {
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
