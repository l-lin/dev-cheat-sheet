package lin.louis.spring.injectbean;

import org.springframework.stereotype.Component;

/**
 * @author llin
 * @created 22/05/14 09:28
 */
@Component
public class BeanToInject {
    public String foo() {
        return "foo";
    }
}
