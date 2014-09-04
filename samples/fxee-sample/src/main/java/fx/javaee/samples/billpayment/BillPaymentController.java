package fx.javaee.samples.billpayment;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Dependent
public class BillPaymentController {

    @Inject
    Validator validator;

    @Inject
    Event<Payment> paymentEvent;

    @Inject
    private EchoWebSocketClient pwsc;

    @FXML
    private ToggleGroup paymentType;

    @FXML
    private TextField amount;

    Payment payment = new Payment();

    public void pay() {
        pwsc.send("I want to pay!");

        payment.setDatetime(Calendar.getInstance().getTime());

        Set<ConstraintViolation<Payment>> constraintViolations = validator.validate(payment);
        if (constraintViolations.isEmpty()) {
            paymentEvent.fire(payment);
        } else {
            constraintViolations.forEach(System.out::println);
        }
    }

    public void initialize() {
        System.out.println("Calling BPA.initialize... " + Objects.isNull(pwsc));
        amount.textProperty().addListener((observable, oldValue, newValue)
                -> payment.setValue(new BigDecimal(newValue)));
        paymentType
                .selectedToggleProperty()
                .addListener((observable, oldValue, newValue)
                        -> payment.setPaymentType(
                                newValue.getUserData().toString()));

    }

}
