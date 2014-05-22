package lin.louis.spring.injectbean;

import org.springframework.stereotype.Component;

/**
 * @author llin
 * @created 22/05/14 09:28
 */
@Component
public class BeanToInject {
    /**
     * Foo string.
     *
     * @return the string
     */
    public String foo() {
        return "foo";
    }
}
