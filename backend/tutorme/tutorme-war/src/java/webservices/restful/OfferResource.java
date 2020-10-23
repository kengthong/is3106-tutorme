/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Offer;
import exception.InvalidParamsException;
import exception.InvalidSubjectChoiceException;
import filter.JWTTokenNeeded;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.OfferSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("offer")
@RequestScoped
public class OfferResource {

    @EJB
    OfferSessionLocal offerSession;

    @GET
    @Path("/allOffers")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOffers() {
        System.out.println("Getting all offers...");
        List<Offer> offers = offerSession.retrieveAllOffers();
        GenericEntity<List<Offer>> packet = new GenericEntity<List<Offer>>(offers) {
        };
        return Response.status(200).entity(packet).build();
    }

    @GET
    @Path("/tuteeOffers")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTuteeOffers(@QueryParam("tuteeId") Long tuteeId) {
        System.out.println("Getting tutee's offers...");
        List<Offer> offers = offerSession.retrieveOffersByTuteeId(tuteeId);
        GenericEntity<List<Offer>> packet = new GenericEntity<List<Offer>>(offers) {
        };
        return Response.status(200).entity(packet).build();
    }

    @GET
    @Path("/jobListingOffers")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobListingOffers(@QueryParam("jobListingId") Long jobListingId) {
        System.out.println("Getting jobListing's offers...");
        List<Offer> offers = offerSession.retrieveOffersByJobListingId(jobListingId);
        GenericEntity<List<Offer>> packet = new GenericEntity<List<Offer>>(offers) {
        };
        return Response.status(200).entity(packet).build();
    }

    //TODO: dont know why not getting response
    @PUT
    @Path("/makeOffer")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeOffer(JsonObject json) {
        try {
            String offeredRateStr = json.getString("offeredRate");
            double offeredRate = Double.valueOf(offeredRateStr);

            String pattern = "dd-MM-YYYY";
            SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
            String startDateStr = json.getString("startDate");
            Date startDate;
            startDate = dateFormatter.parse(startDateStr);

            String tuteeIdStr = json.getString("tuteeId");
            Long tuteeId = Long.valueOf(tuteeIdStr);

            String subjectIdStr = json.getString("chosenSubjectId");
            Long subjectId = Long.valueOf(subjectIdStr);

            String jobListingIdStr = json.getString("jobListingId");
            Long jobListingId = Long.valueOf(jobListingIdStr);

            String numSessionsStr = json.getString("numSessions");
            int numSessions = Integer.valueOf(numSessionsStr);
            String hoursPerSessionStr = json.getString("hoursPerSession");
            int hoursPerSession = Integer.valueOf(hoursPerSessionStr);
            String notes = json.getString("notes");

            System.out.println("Making new offer...tuteeId: " + tuteeId + " for jobListingId:" + jobListingId);
            Offer offer = offerSession.createOffer(offeredRate, startDate, tuteeId, subjectId, jobListingId, numSessions, hoursPerSession, notes.trim());
            JsonObjectBuilder payload = Json.createObjectBuilder();
            payload.add("offerId", offer.getOfferId());
            payload.add("success", true);
            return Response.status(200).entity(payload.build()).build();
        } catch (ParseException | InvalidParamsException | InvalidSubjectChoiceException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "error ocurred when creating offer.").build();
            return Response.status(400).entity(exception).build();
        }

    }
}
