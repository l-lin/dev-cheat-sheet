package lin.louis.spring.injectbean;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 22/05/14 09:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config-test.xml"})
public class InjectBeanToPOJO {
    @Inject
    private AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor;

    @Test
    public void injectBean() {
        SimplePOJOWithInjectedBean pojo = new SimplePOJOWithInjectedBean();
        annotationBeanPostProcessor.processInjection(pojo);
        assertThat(pojo.foo()).isEqualTo("foo");
    }
}
