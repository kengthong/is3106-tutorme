/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.JobListing;
import entity.Message;
import entity.Offer;
import entity.Rating;
import entity.Tutee;
import exception.InvalidParamsException;
import exception.InvalidSubjectChoiceException;
import exception.OfferNotFoundException;
import exception.OfferStatusException;
import exception.PersonNotFoundException;
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
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.MessageSessionLocal;
import session.OfferSessionLocal;
import session.StaffSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/offer")
@RequestScoped
public class OfferResource {

    @EJB
    OfferSessionLocal offerSession;
    @EJB
    MessageSessionLocal messageSession;
    @EJB
    StaffSessionLocal staffSession;

    @GET
    @Path("/offers")
    @JWTTokenNeeded
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
    @Path("/{offerId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@PathParam("offerId") Long offerId) {
        try {
            System.out.println("Getting offerId..." + offerId);
            Offer offer = offerSession.retrieveOfferById(offerId);
            Tutee tutee = offer.getTutee();
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setOffers(null);
            Rating rating = offer.getRating();
            if (rating != null) {
                rating.setOffer(null);
            }

            JobListing jobListing = offer.getJobListing();
            jobListing.setOffers(null);
            jobListing.setTutor(null);

            GenericEntity<Offer> payload = new GenericEntity<Offer>(offer) {
            };
            return Response.status(200).entity(payload).build();
        } catch (OfferNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }

    // Considering to remove, use TuteeResources's getTutee to retrieve associated offers
    @GET
    @Path("/tuteeOffers/{tuteeId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTuteeOffers(@PathParam("tuteeId") Long tuteeId) {
        System.out.println("Getting tutee's offers...");
        List<Offer> offers = offerSession.retrieveOffersByTuteeId(tuteeId);
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

    // Considering to remove, use JobListingResource's getJobListing to retrieve associated offers
    @GET
    @Path("/jobListingOffers/{jobListingId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobListingOffers(@PathParam("jobListingId") Long jobListingId) {
        System.out.println("Getting jobListing's offers...");
        List<Offer> offers = offerSession.retrieveOffersByJobListingId(jobListingId);
        for (Offer o : offers) {
            Tutee tutee = o.getTutee();
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setOffers(null);
//            Rating rating = o.getRating();
//            if (rating == null) {
//                rating.setOffer(null);
//            }

            JobListing jobListing = o.getJobListing();
            jobListing.setOffers(null);
            jobListing.setTutor(null);
        }
        GenericEntity<List<Offer>> payload = new GenericEntity<List<Offer>>(offers) {
        };
        return Response.status(200).entity(payload).build();
    }

    @POST
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
            double hoursPerSession = Double.valueOf(hoursPerSessionStr);
            String notes = json.getString("notes");

            System.out.println("Making new offer...tuteeId: " + tuteeId + " for jobListingId:" + jobListingId);
            Offer offer = offerSession.createOffer(offeredRate, startDate, tuteeId, subjectId, jobListingId, numSessions, hoursPerSession, notes.trim());
            List<Offer> offers = offerSession.retrieveOffersByTuteeId(tuteeId);
            for (Offer o : offers) {
                Tutee tutee = o.getTutee();
                tutee.setReceivedMessages(null);
                tutee.setSentMessages(null);
                tutee.setOffers(null);
                Rating rating = o.getRating();
                if (rating != null) {
                    rating.setOffer(null);
                }

                JobListing jobListing = o.getJobListing();
                jobListing.setOffers(null);
                jobListing.setTutor(null);
            }

            // Create offer message notification
            Message message = messageSession.createOfferMessage(tuteeId, jobListingId, "TuteeId: " + tuteeId + " has made an offer for your post with jobListingID: " + jobListingId);

            GenericEntity<List<Offer>> payload = new GenericEntity<List<Offer>>(offers) {
            };
            return Response.status(200).entity(payload).build();
        } catch (ParseException | InvalidParamsException | InvalidSubjectChoiceException | PersonNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/accept/{offerId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response acceptOffer(@PathParam("offerId") Long offerId) {
        try {
            System.out.println("Accepting offerId..." + offerId);
            Offer offer = offerSession.acceptOffer(offerId);
            Tutee tutee = offer.getTutee();
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setOffers(null);
            Rating rating = offer.getRating();
            if (rating != null) {
                rating.setOffer(null);
            }

            JobListing jobListing = offer.getJobListing();
            jobListing.setOffers(null);
            jobListing.setTutor(null);
            GenericEntity<Offer> payload = new GenericEntity<Offer>(offer) {
            };
            return Response.status(200).entity(payload).build();
        } catch (OfferNotFoundException | OfferStatusException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/withdraw/{offerId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response withdrawOffer(@PathParam("offerId") Long offerId) {
        try {
            System.out.println("Withdrawing offerId..." + offerId);
            Offer offer = offerSession.withdrawOffer(offerId);
            Tutee tutee = offer.getTutee();
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setOffers(null);
            Rating rating = offer.getRating();
            if (rating != null) {
                rating.setOffer(null);
            }

            JobListing jobListing = offer.getJobListing();
            jobListing.setOffers(null);
            jobListing.setTutor(null);
            GenericEntity<Offer> payload = new GenericEntity<Offer>(offer) {
            };
            return Response.status(200).entity(payload).build();
        } catch (OfferNotFoundException | OfferStatusException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }
}
