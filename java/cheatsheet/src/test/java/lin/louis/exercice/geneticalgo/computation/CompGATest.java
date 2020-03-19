package lin.louis.exercice.geneticalgo.computation;

import org.junit.jupiter.api.Test;


class CompGATest {
    @Test
    void geneticAlgo() {
//        GeneticAlgo.proceed(200);
        CompPopulation population = new CompPopulation(20, 5, 20);
        CompChromosome chromosome = population.generation(1000);
        System.out.println("Found GOAL ----------------");
        System.out.println("Generations: " + population.getGenerationNumber());
        System.out.println(chromosome.toString());
    }
}
