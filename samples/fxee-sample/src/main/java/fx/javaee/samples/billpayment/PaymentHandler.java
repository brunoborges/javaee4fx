package fx.javaee.samples.billpayment;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

@Dependent
public class PaymentHandler implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(PaymentHandler.class.getName());

    // private Client client = ClientBuilder.newClient();
    @Logged
    public void payment(@Observes Payment event) {
        LOGGER.log(Level.INFO, "PaymentHandler: {0}", event.toString());
        // WebTarget target = client.target("http://localhost:8080/fxee-serverside/rest").path("payment");
        // call a specific Credit handler class...
    }

}
