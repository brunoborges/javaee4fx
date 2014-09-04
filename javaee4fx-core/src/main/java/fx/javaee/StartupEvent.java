package fx.javaee;

import javafx.application.Application;
import javafx.stage.Stage;

public class StartupEvent {

    public final Stage stage;
    public final Application application;

    public StartupEvent(Stage stage, Application application) {
        this.stage = stage;
        this.application = application;
    }

}
