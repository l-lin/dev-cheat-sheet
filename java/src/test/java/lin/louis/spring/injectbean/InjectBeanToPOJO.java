package lin.louis.spring.injectbean;

import lin.louis.spring.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class InjectBeanToPOJO {

    @Autowired
    private AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor;

    @Test
    void injectBean() {
        SimplePOJOWithInjectedBean pojo = new SimplePOJOWithInjectedBean();
        annotationBeanPostProcessor.processInjection(pojo);
        assertThat(pojo.foo()).isEqualTo("foo");
    }
}
