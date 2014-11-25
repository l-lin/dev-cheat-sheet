package lin.louis.exercice.geneticalgo.computation;

import java.util.Random;

/**
 * FIXME: Does not WORK
 */
class CompChromosome implements Comparable<CompChromosome> {
    private static final char[] LTABLE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/'};
    private static final float MUTATION_RATE = 0.7f;
    private static Random rand = new Random();
    private StringBuffer chromo;
    private StringBuffer decodeChromo;
    private double cost;

    private CompChromosome(StringBuffer chromo) {
        this.chromo = chromo;
        this.decodeChromo = new StringBuffer(chromo.length() * 4);
    }

    public static CompChromosome random(int nbCharacters) {
        StringBuffer chromo = new StringBuffer(nbCharacters * 4);

        // Create the full buffer
        for (int y = 0; y < nbCharacters; y++) {
            // Generate a random binary integer
            String binString = Integer.toBinaryString(rand.nextInt(LTABLE.length));
            int fillLen = 4 - binString.length();

            // Fill to 4
            for (int x = 0; x < fillLen; x++) {
                chromo.append('0');
            }

            // Append the chromo
            chromo.append(binString);
        }
        return new CompChromosome(chromo);
    }

    public final String decodeChromo() {
        // Create a buffer
        decodeChromo.setLength(0);

        // Loop through the chromosome
        for (int x = 0; x < chromo.length(); x += 4) {
            // Get the
            int idx = Integer.parseInt(chromo.substring(x, x + 4), 2);
            if (idx < LTABLE.length) {
                decodeChromo.append(LTABLE[idx]);
            }
        }

        // Return the string
        return decodeChromo.toString();
    }

    public final void computeCost(int target) {
        int total = addUp();
        if (total == target) {
            cost = 0;
        }
        cost = (double) 1 / (target - total);
    }

    public CompChromosome[] mate(CompChromosome other) {
        // Generate a random position
        int pivot = rand.nextInt(chromo.length());

        String child1 = this.chromo.toString().substring(0, pivot) + other.chromo.substring(pivot);
        String child2 = other.chromo.toString().substring(0, pivot) + this.chromo.substring(pivot);

        return new CompChromosome[]{
                new CompChromosome(new StringBuffer(child1)),
                new CompChromosome(new StringBuffer(child2))
        };
    }

    // Mutation
    public final void mutate() {
        for (int x = 0; x < chromo.length(); x++) {
            if (rand.nextDouble() <= MUTATION_RATE) {
                chromo.setCharAt(x, (chromo.charAt(x) == '0' ? '1' : '0'));
            }
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
            if (num == !Character.isDigit(ch)) {
                return false;
            }

            // Don't allow divide by zero
            if (x > 0 && ch == '0' && decodedString.charAt(x - 1) == '/') {
                return false;
            }

            num = !num;
        }

        // Can't end in an operator
        if (!Character.isDigit(decodedString.charAt(decodedString.length() - 1))) {
            return false;
        }

        return true;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(CompChromosome o) {
        return this.cost - o.cost > 0 ? 1 : this.cost == o.cost ? 0 : -1;
    }

    @Override
    public String toString() {
        return decodeChromo();
    }
}

