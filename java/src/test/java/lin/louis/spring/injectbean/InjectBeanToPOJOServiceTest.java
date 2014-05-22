package lin.louis.spring.injectbean;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
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
public class InjectBeanToPOJOServiceTest {
    @Inject
    private InjectBeanToPOJOService injectBeanToPOJOService;

    @Test
    public void testInjectBean() {
        SimplePOJOWithInjectedBean pojo = new SimplePOJOWithInjectedBean();
        injectBeanToPOJOService.processInjection(pojo);
        assertThat(pojo.foo()).isEqualTo("foo");
    }
}
