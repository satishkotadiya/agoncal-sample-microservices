package org.agoncal.sample.microservice.consul;

import org.wildfly.swarm.topology.Advertise;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
@Advertise("quote2")
@Path("/quotes")
@Produces(MediaType.TEXT_PLAIN)
public class QuoteTwoEndpoint {

    // ======================================
    // =             Constants              =
    // ======================================

    public static final String QUOTE = "Seven for the Dwarf-lords in their halls of stone, ";

    // ======================================
    // =          Business methods          =
    // ======================================

    @GET
    public Response quote() {
        return Response.ok(QUOTE).build();
    }

    @GET
    @Path("/chain")
    public Response chainAllQuotes() {

        // Invoke Quote 2
        URI nextQuoteURI = ConsulRegistry.discoverServiceURI("quote3");
        String nextQuote  = ClientBuilder.newClient().target(nextQuoteURI).path("quotes/chain").request(TEXT_PLAIN).get(String.class);

        return Response.ok(QUOTE + nextQuote).build();
    }
}
