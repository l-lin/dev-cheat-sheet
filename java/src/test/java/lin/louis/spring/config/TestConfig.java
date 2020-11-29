package lin.louis.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("lin.louis.spring")
@EnableAspectJAutoProxy
public class TestConfig {
}
