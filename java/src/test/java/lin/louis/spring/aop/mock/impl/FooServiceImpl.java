package lin.louis.spring.aop.mock.impl;

import lin.louis.spring.aop.annotation.AnnotationOnMethod;
import lin.louis.spring.aop.mock.FooService;
import org.springframework.stereotype.Service;

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
