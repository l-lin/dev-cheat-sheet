package lin.louis.data_structures.exercises.geneticalgo.hello_world;

import org.junit.jupiter.api.Test;


class HelloWorldGATest {
    @Test
    void geneticAlgo() {
        HWPopulation population = new HWPopulation("foobar", 20);
        HWChromosome chromosome = population.generation(5000);
        System.out.println("Found GOAL ----------------");
        System.out.println("Generations: " + population.getGenerationNumber());
        System.out.println(chromosome.toString());
    }
}
