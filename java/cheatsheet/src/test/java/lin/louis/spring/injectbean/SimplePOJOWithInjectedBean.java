package lin.louis.spring.injectbean;

import org.springframework.beans.factory.annotation.Autowired;

public class SimplePOJOWithInjectedBean {
    @Autowired
    private BeanToInject beanToInject;

    public String foo() {
        return beanToInject.foo();
    }

}
