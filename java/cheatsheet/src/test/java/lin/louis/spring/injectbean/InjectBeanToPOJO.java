package lin.louis.spring.injectbean;

import lin.louis.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = TestConfig.class)
public class InjectBeanToPOJO {
    @Autowired
    private AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor;

    @Test
    public void injectBean() {
        SimplePOJOWithInjectedBean pojo = new SimplePOJOWithInjectedBean();
        annotationBeanPostProcessor.processInjection(pojo);
        assertThat(pojo.foo()).isEqualTo("foo");
    }
}
