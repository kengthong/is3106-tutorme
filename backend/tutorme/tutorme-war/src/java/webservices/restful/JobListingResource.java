/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.JobListing;
import filter.JWTTokenNeeded;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
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
    public Response getFilteredJobListings(@QueryParam("subject") String subject,
            @QueryParam("level") String level,
            @QueryParam("minPrice") String minPrice,
            @QueryParam("maxPrice") String maxPrice,
            @QueryParam("name") String name
    ) {
        System.out.println("Getting filtered jobListings...");
        double minPx = 0;
        double maxPx = 999;
        if (!minPrice.isEmpty() && !minPrice.equals(" ")) {
            minPx = Double.valueOf(minPrice);
        }
        if (!maxPrice.isEmpty() && !maxPrice.equals(" ")) {
            maxPx = Double.valueOf(maxPrice);
        }
        System.out.println("###%%% filterJobListings params...");
        System.out.println("...subject: " + subject);
        System.out.println("...level: " + level);
        System.out.println("...minPrice: " + minPx);
        System.out.println("...maxPrice: " + maxPx);
        System.out.println("...tutor's name: " + name);
        List<JobListing> jobListings = jobListingSession.retrieveJobListingsWithMultipleFilters(subject.trim(), level.trim(), minPx, maxPx, name.trim());
        // return jobListing object with reviewCount and avgRatings
        GenericEntity<List<JobListing>> packet = new GenericEntity<List<JobListing>>(jobListings) {
        };
        return Response.status(200).entity(packet).build();
//        } else {
//            JsonObject exception = Json.createObjectBuilder().add("error", "Unauthorized or missing JWT.").build();
//            return Response.status(400).entity(exception).build();
//        }
    }

//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public JobListing find(@PathParam("id") Long id) {
//        return jobListingSession.retrieveJobListingById(id);
//    }
}
