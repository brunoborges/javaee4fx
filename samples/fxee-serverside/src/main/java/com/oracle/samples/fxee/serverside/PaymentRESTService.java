package com.oracle.samples.fxee.serverside;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

@Path("payment")
public class PaymentRESTService {

    @Context
    private UriInfo context;

    @GET
    @Produces("application/json")
    public String getJson() {
        return "{}";
    }

    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
        System.out.println("PaymentREST.put: " + content);
    }

}
