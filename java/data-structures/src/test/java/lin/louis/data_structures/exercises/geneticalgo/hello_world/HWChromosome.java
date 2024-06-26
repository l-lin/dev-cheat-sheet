package lin.louis.data_structures.exercises.geneticalgo.hello_world;

import java.util.Random;

public class HWChromosome implements Comparable<HWChromosome> {
    private static final char[] ALPHA = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final float MUTATION_RATE = 0.5f;
    private static Random rand = new Random();
    private String code;
    private int cost = Integer.MAX_VALUE;

    private HWChromosome(String code) {
        this.code = code;
    }

    public static HWChromosome random(int length) {
        String code = "";
        while (code.length() < length) {
            code += randomChar();
        }
        return new HWChromosome(code);
    }

    public void computeCost(String target) {
        int total = 0;
        for (int i = 0; i < this.code.length(); i++) {
            total += Math.abs(((int) this.code.charAt(i)) - ((int) target.charAt(i)));
        }
        this.cost = total;
    }

    public HWChromosome[] mate(HWChromosome chromosome) {
        int pivot = Math.round(this.code.length() / 2) - 1;
        String child1 = this.code.substring(0, pivot) + chromosome.code.substring(pivot);
        String child2 = chromosome.code.substring(0, pivot) + this.code.substring(pivot);
        return new HWChromosome[]{new HWChromosome(child1), new HWChromosome(child2)};
    }

    public void mutate() {
        if (Math.random() > MUTATION_RATE) {
            return;
        }

        int index = rand.nextInt(this.code.length());
        int upOrDown = Math.random() <= 0.5f ? -1 : 1; // Fifty fifty chance
        char newChar = Character.toChars(((int) this.code.charAt(index)) + upOrDown)[0];
        String newString = "";
        for (int i = 0; i < this.code.length(); i++) {
            if (i == index) {
                newString += newChar;
            } else {
                newString += this.code.charAt(i);
            }
        }

        this.code = newString;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public int compareTo(HWChromosome o) {
        return this.cost - o.cost;
    }

    @Override
    public String toString() {
        return code;
    }

    private static char randomChar() {
        int randomNb = rand.nextInt(25);
        return ALPHA[randomNb];
    }
}
