package lin.louis.spring.injectbean;

import javax.inject.Inject;

import lin.louis.spring.injectbean.mock.BeanToInject;

/**
 * @author llin
 * @created 22/05/14 09:26
 */
public class SimplePOJOWithInjectedBean {
    @Inject
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
