package lin.louis.aop.proxifiedtarget;

import javax.inject.Inject;
import java.lang.reflect.Field;

import lin.louis.aop.mock.FooService;
import lin.louis.aop.mock.impl.FooServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oodrive
 * @author llin
 * @created 22/05/14 10:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config-test.xml"})
public class GetAopProxyfiedTargetField {
    @Test
    public void getTargetField() throws NoSuchFieldException {
        Field field = FooServiceImpl.class.getDeclaredField("foo");
        assertThat(ReflectionUtils.findField(FooServiceImpl.class, "foo")).isNotNull().isEqualTo(field);
    }
}
