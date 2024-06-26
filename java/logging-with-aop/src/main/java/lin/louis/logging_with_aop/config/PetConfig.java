package lin.louis.logging_with_aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;
import lin.louis.logging_with_aop.pet.FindPet;
import lin.louis.logging_with_aop.pet.PetRepository;
import lin.louis.logging_with_aop.pet.PurchasePet;
import lin.louis.logging_with_aop.pet.metric.MicrometerPetMetrics;
import lin.louis.logging_with_aop.pet.metric.PetMetrics;

@Configuration
class PetConfig {

    @Configuration
    static class MonitoringConfig {

        @Bean
        PetMetrics petMetrics(MeterRegistry meterRegistry) {
            return new MicrometerPetMetrics(meterRegistry);
        }
    }

    @Bean
    PetRepository petRepository() {
        return new PetRepository.Simulation(new PetRepository.InMemory());
    }

    @Bean
    PurchasePet purchasePet(PetRepository petRepository) {
        return new PurchasePet(petRepository);
    }

    @Bean
    FindPet findPet(PetRepository petRepository) {
        return new FindPet(petRepository);
    }
}
