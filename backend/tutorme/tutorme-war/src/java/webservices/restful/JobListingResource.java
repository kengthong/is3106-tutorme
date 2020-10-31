/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.JobListing;
import entity.Tutor;
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

///////////////////////////////////////////////////
/// To-do
/// - create new joblisting
///////////////////////////////////////////////////
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredJobListings(
            @QueryParam("subject") String subject,
            @QueryParam("level") String level,
            @QueryParam("minPrice") String minPrice,
            @QueryParam("maxPrice") String maxPrice,
            @QueryParam("name") String name
    ) {
        System.out.println("Getting filtered jobListings...");
        double minPx = 0;
        double maxPx = 999;
        if (!minPrice.isEmpty()) {
            minPx = Double.valueOf(minPrice.trim());
        }
        if (!maxPrice.isEmpty()) {
            maxPx = Double.valueOf(maxPrice.trim());
        }
        System.out.println("###%%% filterJobListings params...");
        System.out.println("...subject: " + subject);
        System.out.println("...level: " + level);
        System.out.println("...minPrice: " + minPx);
        System.out.println("...maxPrice: " + maxPx);
        System.out.println("...tutor's name: " + name);
        List<JobListing> jobListings = jobListingSession.retrieveJobListingsWithMultipleFilters(subject.trim(), level.trim(), minPx, maxPx, name.trim());
        // return jobListing object with reviewCount and avgRatings
        for (JobListing jl : jobListings) {
            Tutor tutor = jl.getTutor();
            tutor.setSalt(null);
            tutor.setPassword(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            tutor.setJobListings(null);
            
            jl.setOffers(null);
        }
        GenericEntity<List<JobListing>> packet = new GenericEntity<List<JobListing>>(jobListings) {
        };
        return Response.status(200).entity(packet).build();
    }
}
