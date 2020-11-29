package lin.louis.spring.aop.method;

import lin.louis.spring.aop.mock.FooService;
import lin.louis.spring.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class MethodAspectTest {

    @Autowired
    private FooService fooService;

    @Test
    public void testMethodAspect() {
        fooService.foo("testMethodAspect");
    }
}
