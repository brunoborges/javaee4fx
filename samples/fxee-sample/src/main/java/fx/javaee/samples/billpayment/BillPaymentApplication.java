package fx.javaee.samples.billpayment;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.inject.Inject;
import java.io.InputStream;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;

@Dependent
public class BillPaymentApplication {

    @Inject
    FXMLLoader fxmlLoader;

    @Logged
    public void start(@Observes Stage stage) throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/fxml/billPayment.fxml")) {
            Parent root = fxmlLoader.load(is);

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/style.css");

            stage.setTitle("JavaFX and Java EE");
            stage.setScene(scene);
            stage.show();
        }
    }

}
