package fx.javaee.web.ws;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

@Dependent
public class WebSocketClientProducer {

    private WebSocketContainer client;

    @Produces
    public WebSocketContainer create() {
        if (client == null) {
            client = ContainerProvider.getWebSocketContainer();
        }
        return client;
    }

}
