package lin.louis.aop.mock.impl;

import lin.louis.aop.annotation.AnnotationOnMethod;
import lin.louis.aop.mock.FooService;
import org.springframework.stereotype.Service;

/**
 * @author llin
 * @created 20/05/14 10:07
 */
@Service
public class FooServiceImpl implements FooService {
    private String foo;

    @Override
    @AnnotationOnMethod(value = "FooAnnotation")
    public String foo(String param) {
        String msg = "Foo " + param;
        System.out.println(msg);
        return msg;
    }
}
