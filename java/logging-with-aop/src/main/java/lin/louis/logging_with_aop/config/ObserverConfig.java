package lin.louis.logging_with_aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lin.louis.logging_with_aop.logging.Observer;
import lin.louis.logging_with_aop.logging.aop.ObserverAspect;
import lin.louis.logging_with_aop.pet.logging.PetMdcInputFactory;
import lin.louis.logging_with_aop.pet.metric.PetActionsFactory;
import lin.louis.logging_with_aop.pet.metric.PetMetrics;

@Configuration
class ObserverConfig {

    @Bean
    Observer observer(PetMetrics petMetrics) {
        return Observer.create(PetActionsFactory.create(petMetrics));
    }

    @Bean
    ObserverAspect observerAspect(Observer observer) {
        return new ObserverAspect(observer, PetMdcInputFactory.create());
    }
}
