package fx.javaeebootstrap.tests;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class BeanValidationSETest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void nullAuthorTest() {
        Document car = new Document(null, "Something about Java EE and JavaFX", 40);

        Set<ConstraintViolation<Document>> constraintViolations
                = validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void shortSummaryTest() {
        Document car = new Document("Stephen Hawkins", "Whatever", 30);

        Set<ConstraintViolation<Document>> constraintViolations = validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "size must be between 10 and 140",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void fewPagesTest() {
        Document car = new Document("Stephen Hawkins", "Whatever that works", 2);

        Set<ConstraintViolation<Document>> constraintViolations
                = validator.validate(car);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "must be greater than or equal to 20",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void validDocumentTest() {
        Document car = new Document("Stephen Hawkins", "Whatever that works", 22);

        Set<ConstraintViolation<Document>> constraintViolations
                = validator.validate(car);

        assertEquals(0, constraintViolations.size());
    }

}
