package fx.javaee;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Dependent
public class BeanValidatorProducer {

    @Inject
    ValidatorFactory factory;

    @Produces
    public Validator create() {
        return factory.getValidator();
    }

}
