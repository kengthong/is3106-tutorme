/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.JobListing;
import entity.Offer;
import entity.Rating;
import entity.Tutee;
import entity.Tutor;
import filter.StaffJWTTokenNeeded;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.JobListingSessionLocal;
import session.OfferSessionLocal;
import session.TuteeSessionLocal;
import session.TutorSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("report")
@RequestScoped
public class ReportResource {

    @EJB
    JobListingSessionLocal jobListingSession;
    @EJB
    OfferSessionLocal offerSession;
    @EJB
    TutorSessionLocal tutorSession;
    @EJB
    TuteeSessionLocal tuteeSession;

    public ReportResource() {
    }

    @GET
    @Path("/jobListings")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response reportJobListings() {
        System.out.println("Getting jobListing report...");
        List<JobListing> jobListings = jobListingSession.retrieveAllJobListings();
        JsonArrayBuilder jsonArrayPayload = Json.createArrayBuilder();
        ObjectMapper mapper = new ObjectMapper();

        for (JobListing jl : jobListings) {
            try {
                JsonObjectBuilder jsonObject = Json.createObjectBuilder();
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
                String jsonListing = mapper.writeValueAsString(jl);
                jsonObject.add(String.valueOf(jl.getJobListingId()), jsonListing);
                jsonObject.add("numOffers", offers.size());
                jsonObject.add("numSubjects", jl.getSubjects().size());
                jsonArrayPayload.add(jsonObject);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(JobListingResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Response.status(200).entity(jsonArrayPayload.build()).build();
    }

    @GET
    @Path("/offers")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOffers() {
        System.out.println("Getting all offers...");
        List<Offer> offers = offerSession.retrieveAllOffers();

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

            JobListing jobListing = o.getJobListing();
            jobListing.setOffers(null);
            jobListing.setTutor(null);
        }
        GenericEntity<List<Offer>> payload = new GenericEntity<List<Offer>>(offers) {
        };
        return Response.status(200).entity(payload).build();
    }

    @GET
    @Path("/dashboard")
    @StaffJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashboard() {
        Integer totalTutors = tutorSession.getActiveTutors();
        Integer tutorGrowth = tutorSession.getTutorGrowth();
        
        Integer totalTutees = tuteeSession.getActiveTutees();
        Integer tuteeGrowth = tuteeSession.getTuteeGrowth();
        
        System.out.println("tutorGrowth: " + tuteeGrowth);
        return Response.status(400).build();
    }

//    total jobListings
//new joblistings
//
//total offers
//new offers
//offer acceptance rate
//offer rejection rate
}
