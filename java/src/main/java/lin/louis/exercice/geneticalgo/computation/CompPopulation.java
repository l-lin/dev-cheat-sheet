package lin.louis.exercice.geneticalgo.computation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FIXME: Does not WORK
 */
public class CompPopulation {
    private List<CompChromosome> chromosomeList;
    private int generationNumber;
    private int target;

    public CompPopulation(int target, int nbCharacters, int size) {
        this.target = target;
        chromosomeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            chromosomeList.add(CompChromosome.random(nbCharacters));
        }
    }

    public CompChromosome generation(int nbMaxGenerations) {
        for (CompChromosome chromosome : chromosomeList) {
            chromosome.computeCost(target);
        }
        Collections.sort(chromosomeList);

        CompChromosome[] children = chromosomeList.get(0).mate(chromosomeList.get(1));
        chromosomeList.set(chromosomeList.size() - 2, children[0]);
        chromosomeList.set(chromosomeList.size() - 1, children[1]);

        for (CompChromosome chromosome : chromosomeList) {
            chromosome.mutate();
            chromosome.computeCost(target);
            if (chromosome.getCost() == 0 && chromosome.isValid()) {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CompChromosome chromosome : chromosomeList) {
            sb.append(chromosome.toString()).append("\n");
        }
        return sb.toString();
    }
}
