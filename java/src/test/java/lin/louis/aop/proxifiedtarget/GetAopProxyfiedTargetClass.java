package lin.louis.aop.proxifiedtarget;

import javax.inject.Inject;

import lin.louis.aop.mock.FooService;
import lin.louis.aop.mock.impl.FooServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 22/05/14 10:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config-test.xml"})
public class GetAopProxyfiedTargetClass {
    @Inject
    private FooService fooService;

    @Test
    public void getTargetClass() {
        assertThat(fooService).isNotOfAnyClassIn(FooService.class);
        assertThat(AopUtils.getTargetClass(fooService)).isEqualTo(FooServiceImpl.class);
    }
}
