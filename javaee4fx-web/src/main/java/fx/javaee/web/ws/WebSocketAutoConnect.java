package fx.javaee.web.ws;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerEndpoint;

public class WebSocketAutoConnect {

    private Session openSession;
    private String serverURI;

    protected WebSocketAutoConnect(String serverURI) {
        if (Objects.isNull(serverURI) || serverURI.trim().isEmpty()) {
            throw new IllegalArgumentException("'serverURI' cannot be null or empty");
        }
        this.serverURI = serverURI;
    }

    private Optional<Annotation> getInheritedAnnotation(Class<?> type, Class annotation) {
        List<Annotation> result = new ArrayList<>();
        while (Objects.nonNull(type) && type != Object.class) {
            result.addAll(Arrays.asList(type.getDeclaredAnnotation(annotation)));
            type = type.getSuperclass();
        }
        return result.stream().filter(Objects::nonNull).findAny();
    }

    @Inject
    private WebSocketContainer container;

    @PostConstruct
    public void autoConnect() {
        if (getInheritedAnnotation(getClass(), ClientEndpoint.class).isPresent() == false) {
            throw new IllegalArgumentException("Object not annotated with @CliendEndpoint");
        }

        if (serverURI != null) {
            try {
                openSession = container.connectToServer(this, URI.create(serverURI));
            } catch (DeploymentException | IOException ex) {
                Logger.getLogger(WebSocketAutoConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            getInheritedAnnotation(getClass(), ServerEndpoint.class).ifPresent(
                    annotation -> {
                        String serverEndpoint = ((ServerEndpoint) annotation).value();
                        try {
                            openSession = container.connectToServer(this, URI.create(serverEndpoint));
                        } catch (DeploymentException | IOException ex) {
                            Logger.getLogger(WebSocketAutoConnect.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            );
        }
    }

    @PreDestroy
    public void autoDisconnect() {
        if (openSession != null && openSession.isOpen()) {
            try {
                openSession.close();
            } catch (IOException ex) {
                Logger.getLogger(WebSocketAutoConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
