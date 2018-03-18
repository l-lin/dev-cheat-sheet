package lin.louis.exercice.geneticalgo.helloworld;

import org.junit.Test;

public class HelloWorldGATest {
    @Test
    public void geneticAlgo() {
        HWPopulation population = new HWPopulation("foobar", 20);
        HWChromosome chromosome = population.generation(5000);
        System.out.println("Found GOAL ----------------");
        System.out.println("Generations: " + population.getGenerationNumber());
        System.out.println(chromosome.toString());
    }
}
