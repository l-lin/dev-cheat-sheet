package lin.louis.spring.injectbean;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.stereotype.Service;

/**
 * @author Oodrive
 * @author llin
 * @created 22/05/14 09:29
 */
@Service
public class InjectBeanToPOJOService {
    @Inject
    private AutowiredAnnotationBeanPostProcessor annotationBeanPostProcessor;

    /**
     * Process injection.
     *
     * @param bean the bean
     */
    public void processInjection(Object bean) {
        annotationBeanPostProcessor.processInjection(bean);
    }
}
