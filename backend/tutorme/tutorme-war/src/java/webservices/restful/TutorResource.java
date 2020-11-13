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
import entity.Tutor;
import enumeration.CitizenshipEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import exception.TutorNotFoundException;
import filter.TutorJWTTokenNeeded;
import filter.UserPrincipal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import session.JobListingSessionLocal;
import session.TutorSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/tutor")
public class TutorResource {

    @EJB
    TutorSessionLocal tutorSession;
    @EJB
    JobListingSessionLocal jobListingSession;

    public TutorResource() {
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutors() {
        System.out.println("Getting tutors... ");
        List<Tutor> tutors = new ArrayList();
        tutors = tutorSession.retrieveAllTutors();
        if (!tutors.isEmpty()) {
            for (Tutor t : tutors) {
                t.setPassword(null);
                t.setSalt(null);
                t.setSentMessages(null);
                t.setReceivedMessages(null);
                for (JobListing jl : t.getJobListings()) {
                    jl.setTutor(null);

                    for (Offer o : jl.getOffers()) {
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
            }
            GenericEntity<List<Tutor>> payload = new GenericEntity<List<Tutor>>(tutors) {
            };
            return Response.status(200).entity(payload).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned empty list from REST/getTutors").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Path("/get/{tutorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutorById(@PathParam("tutorId") Long tutorId) {
        System.out.println("Tutor Id is... " + tutorId);
        try {
            Tutor t = tutorSession.retrieveTutorById(tutorId);
            t.setPassword(null);
            t.setSalt(null);
            t.setSentMessages(null);
            t.setReceivedMessages(null);
            for (JobListing jl : t.getJobListings()) {
                jl.setReviewCount(jl.getReviewCount());
                jl.setReviewScore(jl.getReviewScore());
                jl.setTutor(null);

                for (Offer o : jl.getOffers()) {
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
            GenericEntity<Tutor> payload = new GenericEntity<Tutor>(t) {
            };
            return Response.status(200).entity(payload).build();
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/update")
    @TutorJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTutorById(@Context SecurityContext securityContext, JsonObject json) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        Long tutorId = person.getPersonId();
        System.out.println("Updating Tutor Id is ... " + tutorId);

        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        String mobileNum = json.getString("mobileNum");

        String gender = json.getString("gender");
        String qualification = json.getString("highestQualification");
        String citizenship = json.getString("citizenship");
        String race = json.getString("race");
        String profileDesc = json.getString("profileDesc");

        GenderEnum genderEnum = gender.equals("Male") ? GenderEnum.MALE : GenderEnum.FEMALE;
        QualificationEnum qualiEnum = QualificationEnum.valueOf(QualificationEnum.class, qualification.toUpperCase());
        CitizenshipEnum citiEnum = CitizenshipEnum.valueOf(CitizenshipEnum.class, citizenship.toUpperCase());
        RaceEnum raceEnum = RaceEnum.valueOf(RaceEnum.class, race.toUpperCase());

        String dob = json.getString("dob");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
        Date parsedDob = new Date();
        try {
            parsedDob = formatter.parse(dob);
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        try {
            Tutor tutor = tutorSession.updateTutorProfile(tutorId, firstName, lastName, mobileNum, genderEnum, parsedDob, qualiEnum, citiEnum, raceEnum, profileDesc);
            tutor.setPassword(null);
            tutor.setSalt(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            for (JobListing jl : tutor.getJobListings()) {
                jl.setTutor(null);
                jl.setOffers(null);
            }
            GenericEntity<Tutor> payload = new GenericEntity<Tutor>(tutor) {
            };
            return Response.status(200).entity(payload).build();
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(404).entity(exception).build();
        }
    }

    @GET
    @Path("/jobListings")
    @TutorJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutorJobListings(@Context SecurityContext securityContext) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        Long tutorId = person.getPersonId();
        System.out.println("Getting jobListings by Tutor Id is ... " + tutorId);
        List<JobListing> jobListings = jobListingSession.retrieveJobListingsByTutorId(tutorId);
        for (JobListing jl : jobListings) {
            jl.setReviewCount(jl.getReviewCount());
            jl.setReviewScore(jl.getReviewScore());
            jl.setTutor(null);

            for (Offer o : jl.getOffers()) {
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
        GenericEntity<List<JobListing>> payload = new GenericEntity<List<JobListing>>(jobListings) {
        };
        return Response.status(200).entity(payload).build();
    }
}
