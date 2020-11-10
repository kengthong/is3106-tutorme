package webservices.restful;

import entity.JobListing;
import entity.Offer;
import entity.Rating;
import entity.Tutee;
import entity.Tutor;
import exception.InvalidParamsException;
import exception.JobListingNotFoundException;
import exception.SubjectNotFoundException;
import filter.TutorJWTTokenNeeded;
import filter.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import session.JobListingSessionLocal;
/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/jobListing")
@RequestScoped
public class JobListingResource {

    @EJB
    JobListingSessionLocal jobListingSession;

    public JobListingResource() {
    }

    @GET
    @Path("/get")
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

            List<Offer> offers = jl.getOffers();
            for (Offer o : offers) {
                Tutee tutee = o.getTutee();
                tutee.setReceivedMessages(null);
                tutee.setSentMessages(null);
                tutee.setPassword(null);
                tutee.setSalt(null);
                tutee.setOffers(null);
                Rating rating = o.getRating();
                if (rating != null) {
                    rating.setOffer(null);
                }

                o.setJobListing(null);
            }
        }
        GenericEntity<List<JobListing>> packet = new GenericEntity<List<JobListing>>(jobListings) {
        };
        return Response.status(200).entity(packet).build();
    }

    @GET
    @Path("/get/{jobListingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobListing(
            @PathParam("jobListingId") Long jobListingId) {
        try {
            System.out.println("Getting jobListingID..." + jobListingId);
            JobListing jobListing = jobListingSession.retrieveJobListingById(jobListingId);

            Tutor tutor = jobListing.getTutor();
            tutor.setSalt(null);
            tutor.setPassword(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            tutor.setJobListings(null);

            List<Offer> offers = jobListing.getOffers();
            for (Offer o : offers) {
                Tutee tutee = o.getTutee();
                tutee.setReceivedMessages(null);
                tutee.setSentMessages(null);
                tutee.setPassword(null);
                tutee.setSalt(null);
                tutee.setOffers(null);
                Rating rating = o.getRating();
                if (rating != null) {
                    rating.setOffer(null);
                }

                o.setJobListing(null);
            }

            GenericEntity<JobListing> packet = new GenericEntity<JobListing>(jobListing) {
            };
            return Response.status(200).entity(packet).build();
        } catch (JobListingNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/create")
    @TutorJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJobListing(@Context SecurityContext securityContext, JsonObject json) {
        try {
            UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
            Long tutorId = person.getPersonId();
            String subjectName = json.getString("subject");

            JsonArray levelsArr = json.getJsonArray("levels");
            List<String> levels = new ArrayList<>();
            for (int i = 0; i < levelsArr.size(); i++) {
                System.out.println("Level: " + levelsArr.getString(i));
                levels.add(levelsArr.getString(i));
            }

            String title = json.getString("listingTitle");
            String desc = json.getString("listingDesc");
            String area = json.getString("area");
            String timeslot = json.getString("timeslot");
            Double rate = Double.valueOf(json.getInt("rate"));

            JobListing newJobListing = jobListingSession.createJobListing(tutorId, subjectName, levels, rate, timeslot, area, title, desc);
            List<JobListing> jobListings = jobListingSession.retrieveJobListingsByTutorId(tutorId);
            for (JobListing jl : jobListings) {
                Tutor tutor = jl.getTutor();
                tutor.setSalt(null);
                tutor.setPassword(null);
                tutor.setSentMessages(null);
                tutor.setReceivedMessages(null);
                tutor.setJobListings(null);

                List<Offer> offers = jl.getOffers();
                for (Offer o : offers) {
                    Tutee tutee = o.getTutee();
                    tutee.setReceivedMessages(null);
                    tutee.setSentMessages(null);
                    tutee.setPassword(null);
                    tutee.setSalt(null);
                    tutee.setOffers(null);
                    Rating rating = o.getRating();
                    if (rating != null) {
                        rating.setOffer(null);
                    }

                    o.setJobListing(null);
                }
            }
            GenericEntity<List<JobListing>> packet = new GenericEntity<List<JobListing>>(jobListings) {
            };
            return Response.status(201).entity(packet).build();
        } catch (SubjectNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/edit")
    @TutorJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response editJobListing(JsonObject json) {
        try {
            Long jobListingId = Long.valueOf(json.getString("jobListingId"));
            String subjectName = json.getString("subject");

            JsonArray levelsArr = json.getJsonArray("levels");
            List<String> levels = new ArrayList<>();
            for (int i = 0; i < levelsArr.size(); i++) {
                System.out.println("Level: " + levelsArr.getString(i));
                levels.add(levelsArr.getString(i));
            }

            String title = json.getString("listingTitle");
            String desc = json.getString("listingDesc");
            String area = json.getString("area");
            String timeslot = json.getString("timeslot");
            Double rate = Double.valueOf(json.getInt("rate"));

            JobListing jobListing = jobListingSession.updateJobListing(jobListingId, subjectName, levels, rate, timeslot, area, title, desc);
            Tutor tutor = jobListing.getTutor();
            tutor.setSalt(null);
            tutor.setPassword(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            tutor.setJobListings(null);

            List<Offer> offers = jobListing.getOffers();
            for (Offer o : offers) {
                Tutee tutee = o.getTutee();
                tutee.setReceivedMessages(null);
                tutee.setSentMessages(null);
                tutee.setPassword(null);
                tutee.setSalt(null);
                tutee.setOffers(null);
                Rating rating = o.getRating();
                if (rating != null) {
                    rating.setOffer(null);
                }

                o.setJobListing(null);
            }

            GenericEntity<JobListing> packet = new GenericEntity<JobListing>(jobListing) {
            };
            return Response.status(200).entity(packet).build();
        } catch (SubjectNotFoundException | JobListingNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }
    
    @PUT
    @Path("/deactivate/{jobListingId}")
    @TutorJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateJobListing(@Context SecurityContext securityContext,@PathParam("jobListingId") Long jobListingId) {
        try {
            UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
            Long tutorId = person.getPersonId();

            jobListingSession.deactivateJobListing(tutorId, jobListingId);
            return Response.status(204).build();
        } catch (JobListingNotFoundException | InvalidParamsException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }
    
    @PUT
    @Path("/activate/{jobListingId}")
    @TutorJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateJobListing(@Context SecurityContext securityContext,@PathParam("jobListingId") Long jobListingId) {
        try {
            UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
            Long tutorId = person.getPersonId();

            jobListingSession.activateJobListing(tutorId, jobListingId);
            return Response.status(204).build();
        } catch (JobListingNotFoundException | InvalidParamsException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }
}
