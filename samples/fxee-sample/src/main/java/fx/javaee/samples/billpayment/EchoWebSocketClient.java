package fx.javaee.samples.billpayment;

import fx.javaee.web.ws.AutoConnectServerEndpoint;
import fx.javaee.web.ws.WebSocketAutoConnect;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@Dependent
@ClientEndpoint
// Uncomment annotation below to see bug with using CDI beans as @ClientEndpoint
// Uncomment the interceptor inside beans.xml
// Comment the 'extends WebSocketAutoConnect'
//@AutoConnectServerEndpoint("ws://echo.websocket.org")
public class EchoWebSocketClient extends WebSocketAutoConnect {

    private Session session;

    // Comment this constructor if you commented the 'extends WebSocketAutoConnect'
    public EchoWebSocketClient() {
        super("ws://echo.websocket.org");
    }

    @OnOpen
    public void onOpen(Session p) {
        try {
            this.session = p;
            p.getBasicRemote().sendText("Hello!");
        } catch (IOException e) {
        }
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(String.format("%s %s", "Received message: ", message));
    }

    public void send(String hey_there) {
        try {
            session.getBasicRemote().sendText(hey_there);
        } catch (IOException ex) {
            Logger.getLogger(EchoWebSocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
