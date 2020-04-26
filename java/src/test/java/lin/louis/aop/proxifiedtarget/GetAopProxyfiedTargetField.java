package lin.louis.aop.proxifiedtarget;

import java.lang.reflect.Field;

import lin.louis.aop.mock.impl.FooServiceImpl;
import lin.louis.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class GetAopProxyfiedTargetField {
    @Test
    public void getTargetField() throws NoSuchFieldException {
        Field field = FooServiceImpl.class.getDeclaredField("foo");
        assertThat(ReflectionUtils.findField(FooServiceImpl.class, "foo")).isNotNull().isEqualTo(field);
    }
}
