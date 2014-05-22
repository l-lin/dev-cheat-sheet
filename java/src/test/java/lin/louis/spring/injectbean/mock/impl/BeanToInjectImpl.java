package lin.louis.spring.injectbean.mock.impl;

import lin.louis.spring.injectbean.mock.BeanToInject;
import org.springframework.stereotype.Component;

/**
 * @author llin
 * @created 22/05/14 09:28
 */
@Component
public class BeanToInjectImpl implements BeanToInject {
    /**
     * {@inheritDoc}
     */
    @Override
    public String foo() {
        return "foo";
    }
}
