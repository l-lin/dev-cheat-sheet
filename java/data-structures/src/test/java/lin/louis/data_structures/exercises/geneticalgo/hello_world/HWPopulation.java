package lin.louis.data_structures.exercises.geneticalgo.hello_world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HWPopulation {
    private List<HWChromosome> chromosomeList;
    private String target;
    private int generationNumber;

    public HWPopulation(String target, int size) {
        this.target = target;
        chromosomeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            chromosomeList.add(HWChromosome.random(target.length()));
        }
    }

    public HWChromosome generation(int nbMaxGenerations) {
        for (HWChromosome chromosome : chromosomeList) {
            chromosome.computeCost(this.target);
        }

        Collections.sort(chromosomeList);

        HWChromosome[] children = this.chromosomeList.get(0).mate(this.chromosomeList.get(1));
        chromosomeList.set(chromosomeList.size() - 2, children[0]);
        chromosomeList.set(chromosomeList.size() - 1, children[1]);

        for (HWChromosome chromosome : chromosomeList) {
            chromosome.mutate();
            chromosome.computeCost(this.target);
            if (chromosome.getCode().equals(this.target)) {
                return chromosome;
            }
        }
        this.generationNumber++;
        if (generationNumber < nbMaxGenerations) {
            return generation(nbMaxGenerations);
        }
        return chromosomeList.get(0);
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = chromosomeList.size() - 1; i > 0; i--) {
            HWChromosome chromosome = chromosomeList.get(i);
            sb.append(chromosome.toString()).append("\n");
        }
        return sb.toString();
    }
}
