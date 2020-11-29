package lin.louis.spring.aop.proxifiedtarget;

import lin.louis.spring.aop.mock.FooService;
import lin.louis.spring.aop.mock.impl.FooServiceImpl;
import lin.louis.spring.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class GetAopProxyfiedTargetClass {

    @Autowired
    private FooService fooService;

    @Test
    void getTargetClass() {
        assertThat(fooService).isNotOfAnyClassIn(FooService.class);
        assertThat(AopUtils.getTargetClass(fooService)).isEqualTo(FooServiceImpl.class);
    }
}
