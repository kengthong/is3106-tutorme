/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.JobListing;
import entity.Offer;
import entity.Rating;
import entity.Tutee;
import exception.OfferNotFoundException;
import filter.TuteeJWTTokenNeeded;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.RatingSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/rating")
@RequestScoped
public class RatingResource {

    @EJB
    RatingSessionLocal ratingSession;

    public RatingResource() {
    }

    @POST
    @Path("/rate")
    @TuteeJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeRating(JsonObject json) {

        String ratingValueStr = json.getString("ratingValue");
        String comments = json.getString("comments");
        String offerIdStr = json.getString("offerId");
        Integer ratingValue = Integer.valueOf(ratingValueStr);
        Long offerId = Long.valueOf(offerIdStr);
        System.out.println("Making new rating on offerId..." + offerIdStr);

        try {
            Rating rating = ratingSession.createRating(ratingValue, comments, offerId);
            Offer o = rating.getOffer();
            Tutee tutee = o.getTutee();
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setOffers(null);
            rating.setOffer(null);

            JobListing jobListing = o.getJobListing();
            jobListing.setOffers(null);
            jobListing.setTutor(null);

            GenericEntity<Rating> payload = new GenericEntity<Rating>(rating) {
            };
            return Response.status(201).entity(payload).build();
        } catch (OfferNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(404).entity(exception).build();
        } catch (Exception ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "Rating for this offer has already been made.").build();
            return Response.status(409).entity(exception).build();
        }
    }
}
