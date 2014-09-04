package fx.javaee;

import javafx.fxml.FXMLLoader;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.CDI;

@Dependent
public class FXMLLoaderProducer {

    @Produces
    public FXMLLoader provides() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(c -> CDI.current().select(c).get());
        return fxmlLoader;
    }

}
