package lin.louis.exercice.geneticalgo.computation;

import java.util.*;

public class GeneticAlgo {
    // Static info
    static char[] ltable = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/'};
    static int chromoLen = 5;
    static double crossRate = .7;
    static double mutRate = .001;
    static Random rand = new Random();
    static int poolSize = 40;    // Must be even

    public static void proceed(int target) {

        int gen = 0;

        // Create the pool
        List<Chomosone> pool = new ArrayList<>(poolSize);
        List<Chomosone> newPool = new ArrayList<>(pool.size());

        // Generate unique cromosomes in the pool
        for (int x = 0; x < poolSize; x++) {
            pool.add(new Chomosone(target));
        }

        // Loop until solution is found
        while (true) {
            // Clear the new pool
            newPool.clear();

            // Add to the generations
            gen++;

            // Loop until the pool has been processed
            for (int x = pool.size() - 1; x >= 0; x -= 2) {
                // Select two members
                Chomosone n1 = selectMember(pool);
                Chomosone n2 = selectMember(pool);

                // Cross over and mutate
                n1.crossOver(n2);
                n1.mutate();
                n2.mutate();

                // Rescore the nodes
                n1.scoreChromo(target);
                n2.scoreChromo(target);

                // Check to see if either is the solution
                if (n1.total == target && n1.isValid()) {
                    System.out.println("Generations: " + gen + "  Solution: " + n1.decodeChromo());
                    return;
                }
                if (n2.total == target && n2.isValid()) {
                    System.out.println("Generations: " + gen + "  Solution: " + n2.decodeChromo());
                    return;
                }

                // Add to the new pool
                newPool.add(n1);
                newPool.add(n2);
            }

            // Add the newPool back to the old pool
            pool.addAll(newPool);
        }

    }


    //---- Chomosone Class -----
    private static Chomosone selectMember(List<Chomosone> chomosoneList) {

        // Get the total fitness
        double tot = 0.0;
        for (int x = chomosoneList.size() - 1; x >= 0; x--) {
            double score = chomosoneList.get(x).score;
            tot += score;
        }
        double slice = tot * rand.nextDouble();

        // Loop to find the node
        double ttot = 0.0;
        for (int x = chomosoneList.size() - 1; x >= 0; x--) {
            Chomosone node = chomosoneList.get(x);
            ttot += node.score;
            if (ttot >= slice) {
                chomosoneList.remove(x);
                return node;
            }
        }

        return chomosoneList.remove(chomosoneList.size() - 1);
    }

    // Genetic Algorithm Node
    private static class Chomosone {
        // The chromo
        StringBuffer chromo = new StringBuffer(chromoLen * 4);
        public StringBuffer decodeChromo = new StringBuffer(chromoLen * 4);
        public double score;
        public int total;

        // Constructor that generates a random
        public Chomosone(int target) {

            // Create the full buffer
            for (int y = 0; y < chromoLen; y++) {
                // What's the current length
                int pos = chromo.length();

                // Generate a random binary integer
                String binString = Integer.toBinaryString(rand.nextInt(ltable.length));
                int fillLen = 4 - binString.length();

                // Fill to 4
                for (int x = 0; x < fillLen; x++) chromo.append('0');

                // Append the chromo
                chromo.append(binString);

            }

            // Score the new cromo
            scoreChromo(target);
        }

        public Chomosone(StringBuffer chromo) {
            this.chromo = chromo;
        }

        // Decode the string
        public final String decodeChromo() {

            // Create a buffer
            decodeChromo.setLength(0);

            // Loop throught the chromo
            for (int x = 0; x < chromo.length(); x += 4) {
                // Get the
                int idx = Integer.parseInt(chromo.substring(x, x + 4), 2);
                if (idx < ltable.length) decodeChromo.append(ltable[idx]);
            }

            // Return the string
            return decodeChromo.toString();
        }

        // Scores this chromo
        public final void scoreChromo(int target) {
            total = addUp();
            if (total == target) score = 0;
            score = (double) 1 / (target - total);
        }

        // Crossover bits
        public final void crossOver(Chomosone other) {

            // Should we cross over?
            if (rand.nextDouble() > crossRate) return;

            // Generate a random position
            int pos = rand.nextInt(chromo.length());

            // Swap all chars after that position
            for (int x = pos; x < chromo.length(); x++) {
                // Get our character
                char tmp = chromo.charAt(x);

                // Swap the chars
                chromo.setCharAt(x, other.chromo.charAt(x));
                other.chromo.setCharAt(x, tmp);
            }
        }

        // Mutation
        public final void mutate() {
            for (int x = 0; x < chromo.length(); x++) {
                if (rand.nextDouble() <= mutRate)
                    chromo.setCharAt(x, (chromo.charAt(x) == '0' ? '1' : '0'));
            }
        }


        // Add up the contents of the decoded chromo
        public final int addUp() {

            // Decode our chromo
            String decodedString = decodeChromo();

            // Total
            int tot = 0;

            // Find the first number
            int ptr = 0;
            while (ptr < decodedString.length()) {
                char ch = decodedString.charAt(ptr);
                if (Character.isDigit(ch)) {
                    tot = ch - '0';
                    ptr++;
                    break;
                } else {
                    ptr++;
                }
            }

            // If no numbers found, return
            if (ptr == decodedString.length()) return 0;

            // Loop processing the rest
            boolean num = false;
            char oper = ' ';
            while (ptr < decodedString.length()) {
                // Get the character
                char ch = decodedString.charAt(ptr);

                // Is it what we expect, if not - skip
                if (num && !Character.isDigit(ch)) {
                    ptr++;
                    continue;
                }
                if (!num && Character.isDigit(ch)) {
                    ptr++;
                    continue;
                }

                // Is it a number
                if (num) {
                    switch (oper) {
                        case '+': {
                            tot += (ch - '0');
                            break;
                        }
                        case '-': {
                            tot -= (ch - '0');
                            break;
                        }
                        case '*': {
                            tot *= (ch - '0');
                            break;
                        }
                        case '/': {
                            if (ch != '0') tot /= (ch - '0');
                            break;
                        }
                    }
                } else {
                    oper = ch;
                }

                // Go to next character
                ptr++;
                num = !num;
            }

            return tot;
        }

        public final boolean isValid() {

            // Decode our chromo
            String decodedString = decodeChromo();

            boolean num = true;
            for (int x = 0; x < decodedString.length(); x++) {
                char ch = decodedString.charAt(x);

                // Did we follow the num-oper-num-oper-num patter
                if (num == !Character.isDigit(ch)) return false;

                // Don't allow divide by zero
                if (x > 0 && ch == '0' && decodedString.charAt(x - 1) == '/') return false;

                num = !num;
            }

            // Can't end in an operator
            if (!Character.isDigit(decodedString.charAt(decodedString.length() - 1))) return false;

            return true;
        }
    }
}
