package fx.javaee;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@Dependent
public class BeanValidatorFactoryProducer {

    @Produces
    public ValidatorFactory create() {
        return Validation.buildDefaultValidatorFactory();
    }

}
