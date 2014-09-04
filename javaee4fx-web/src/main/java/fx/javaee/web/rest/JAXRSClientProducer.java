package fx.javaee.web.rest;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Dependent
public class JAXRSClientProducer {

    private Client client;

    @Produces
    public Client createClient() {
        if (client == null) {
            client = ClientBuilder.newClient();
        }
        return client;
    }

    public void closeClient(@Disposes Client client) {
        client.close();
    }

}
