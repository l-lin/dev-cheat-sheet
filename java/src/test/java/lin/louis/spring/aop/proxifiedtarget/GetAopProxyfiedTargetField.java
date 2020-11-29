package lin.louis.spring.aop.proxifiedtarget;

import lin.louis.spring.aop.mock.impl.FooServiceImpl;
import lin.louis.spring.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class GetAopProxyfiedTargetField {

    @Test
    void getTargetField() throws NoSuchFieldException {
        Field field = FooServiceImpl.class.getDeclaredField("foo");
        assertThat(ReflectionUtils.findField(FooServiceImpl.class, "foo")).isNotNull().isEqualTo(field);
    }
}
