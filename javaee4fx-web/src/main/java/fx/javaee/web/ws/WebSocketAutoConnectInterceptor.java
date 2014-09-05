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
import org.jboss.weld.injection.spi.InjectionContext;

@Interceptor
@AutoConnectServerEndpoint("")
public class WebSocketAutoConnectInterceptor {

    @Inject // injected by a CDI producer with ContainerProvider.getWebSocketContainer()
    private WebSocketContainer wsContainer;

    private final Map<Object, Session> openSessions = Collections.synchronizedMap(new HashMap<>());

    @AroundConstruct
    public void aroundConstructor(InjectionContext ic) {
        ic.proceed(); // proceed with construction to get target object
        Object client = ic.getTarget();

        // because of WEBSOCKET_SPEC-229 we can't do this check.
        // @CliendEndpoint is not @Inherited.
        /*
         * if (client.getClass().isAnnotationPresent(ClientEndpoint.class) == false) {
         * throw new IllegalArgumentException("Bean intercepted is not a @CliendEndpoint");
         * }
         */
        AutoConnectServerEndpoint annotation = client
                .getClass()
                .getDeclaredAnnotation(AutoConnectServerEndpoint.class);
        String serverURI = annotation.value();

        Session session;
        try {
            // Tyrus 1.8.2 and Undertow 1.1-Beta6 fail here because 'client'
            // is a CDI proxied object
            session = wsContainer.connectToServer(client, URI.create(serverURI));
            openSessions.put(client, session);
        } catch (DeploymentException | IOException ex) {
        }
    }

    @PreDestroy
    public void disconnect(InvocationContext invocationContext) {
        System.out.println("wsclient interceptor disconnect() called");
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
