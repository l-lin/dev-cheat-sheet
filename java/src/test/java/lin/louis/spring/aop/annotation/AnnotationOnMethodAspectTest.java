package lin.louis.spring.aop.annotation;

import lin.louis.spring.aop.mock.FooService;
import lin.louis.spring.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class AnnotationOnMethodAspectTest {
    @Autowired
    private FooService fooService;

    @Test
    void testAnnotationOnMethodAspect() {
        fooService.foo("testAnnotationOnMethodAspect");
    }
}
