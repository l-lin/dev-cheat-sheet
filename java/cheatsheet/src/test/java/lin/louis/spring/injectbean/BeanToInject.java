package lin.louis.spring.injectbean;

import org.springframework.stereotype.Component;

@Component
public class BeanToInject {
    public String foo() {
        return "foo";
    }
}
