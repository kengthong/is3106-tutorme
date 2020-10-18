/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import filter.JWTTokenNeeded;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.JobListingSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("jobListing")
@RequestScoped
public class JobListingResource {

    @EJB
    private JobListingSessionLocal jobListingSession;

    public JobListingResource() {
    }

    @GET
    @Path("/jobListingList")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredJobListings(JsonObject json) {
        JsonObject exception = Json.createObjectBuilder().add("error", "Unauthorized or missing JWT.").build();
        return Response.status(400).entity(exception).build();
    }
}
