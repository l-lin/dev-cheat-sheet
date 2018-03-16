package lin.louis.aop.annotation;

import lin.louis.aop.mock.FooService;
import lin.louis.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author llin
 * @created 20/05/14 10:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class AnnotationOnMethodAspectTest {
    @Autowired
    private FooService fooService;

    @Test
    public void testAnnotationOnMethodAspect() {
        fooService.foo("testAnnotationOnMethodAspect");
    }
}
