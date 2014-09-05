package fx.javaee.web.ws;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.interceptor.AroundConstruct;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@Interceptor
@AutoConnectServerEndpoint("")
public class WebSocketAutoConnectInterceptor {

    private static final Logger LOGGER = Logger.getLogger(WebSocketAutoConnectInterceptor.class.getName());

    @Inject // injected by a CDI producer with ContainerProvider.getWebSocketContainer()
    private WebSocketContainer wsContainer;

    private final Map<Object, Session> openSessions = Collections.synchronizedMap(new HashMap<>());

    @AroundConstruct
    public void aroundConstructor(InvocationContext ic) {
        LOGGER.info("aroundConstructor");

        try {
            ic.proceed(); // proceed with construction to get target object
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        Object client = ic.getTarget();

        // because of WEBSOCKET_SPEC-229 we can't do this check.
        // @CliendEndpoint is not @Inherited.
        /*
         * if (client.getClass().isAnnotationPresent(ClientEndpoint.class) == false) {
         * throw new IllegalArgumentException("Bean intercepted is not a @CliendEndpoint");
         * }
         */
        AutoConnectServerEndpoint annotation = getInheritedAnnotation(client.getClass());
        String serverURI = annotation.value();

        LOGGER.log(Level.INFO, "serverURI = {0}", serverURI);

        Session session;
        // Tyrus 1.8.2 and Undertow 1.1-Beta6 fail here because 'client'
        // is a CDI proxied object
        LOGGER.log(Level.INFO, "Opening session...");
        try {
            session = wsContainer.connectToServer(client, URI.create(serverURI));
            openSessions.put(client, session);
        } catch (DeploymentException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        LOGGER.log(Level.INFO, "Session opened...");
    }

    private AutoConnectServerEndpoint getInheritedAnnotation(Class clazz) {
        AutoConnectServerEndpoint annot = null;
        while (clazz != Object.class && annot == null) {
            annot = (AutoConnectServerEndpoint) clazz.getDeclaredAnnotation(AutoConnectServerEndpoint.class);
            clazz = clazz.getSuperclass();
        }
        return annot;
    }

    @PreDestroy
    public void disconnect(InvocationContext invocationContext) {
        LOGGER.info("disconnect");
        try {
            Session session = openSessions.get(invocationContext.getTarget());
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(WebSocketAutoConnectInterceptor.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

}
