package lin.louis.spring.injectbean;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author llin
 * @created 22/05/14 09:26
 */
public class SimplePOJOWithInjectedBean {
    @Autowired
    private BeanToInject beanToInject;

    /**
     * Foo string.
     *
     * @return the string
     */
    public String foo() {
        return beanToInject.foo();
    }

}
