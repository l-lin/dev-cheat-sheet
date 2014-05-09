package lin.louis;

import org.springframework.stereotype.Component;

@Component
public class HelloService {
    public String sayHello() {
        return "Hello world!";
    }
}
