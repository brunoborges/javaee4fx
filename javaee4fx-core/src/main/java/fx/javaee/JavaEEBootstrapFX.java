package fx.javaee;

import java.lang.annotation.Annotation;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.enterprise.inject.spi.CDI;
import org.jboss.weld.environment.se.Weld;

public final class JavaEEBootstrapFX extends Application {

    private Weld weld;

    @Override
    public final void init() {
        weld = new Weld();
        weld.initialize();
    }

    @Override
    public final void start(Stage primaryStage) throws Exception {
        StartupEvent startupEvent = new StartupEvent(primaryStage, this);

        // Event for @Observes Stage
        CDI.current()
                .getBeanManager()
                .fireEvent(primaryStage, new Annotation[0]);

        // Event for @Observes StartupEvent
        CDI.current()
                .getBeanManager()
                .fireEvent(startupEvent, new Annotation[0]);
    }

    @Override
    public final void stop() {
        if (weld != null) {
            weld.shutdown();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
