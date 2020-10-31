/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.JobListing;
import entity.Tutor;
import enumeration.CitizenshipEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import exception.TutorNotFoundException;
import filter.JWTTokenNeeded;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.TutorSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("tutor")
public class TutorResource {

    @EJB
    RatingSessionLocal ratingSession;
    @EJB
    TutorSessionLocal tutorSession;

    public TutorResource() {
    }

    @GET
    @Path("/tutors")
    @JWTTokenNeeded
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
                    jl.setOffers(null);
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
    @Path("/{tutorId}")
    @JWTTokenNeeded
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
                jl.setTutor(null);
                jl.setOffers(null);
            }
            GenericEntity<Tutor> payload = new GenericEntity<Tutor>(t) {
            };
            return Response.status(200).entity(payload).build();
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/{tutorId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTutorById(@PathParam("tutorId") Long tutorId, JsonObject json) {
        System.out.println("Updating Tutor Id is ... " + tutorId);

        String firstName = json.getJsonString("firstName").getString();
        String lastName = json.getJsonString("lastName").getString();
        String mobileNum = json.getJsonString("mobileNum").getString();

        String gender = json.getJsonString("gender").getString();
        String qualification = json.getJsonString("highestQualification").getString();
        String citizenship = json.getJsonString("citizenship").getString();
        String race = json.getJsonString("race").getString();
        String profileDesc = json.getJsonString("profileDesc").getString();

        GenderEnum genderEnum = gender.equals("Male") ? GenderEnum.MALE : GenderEnum.FEMALE;
        QualificationEnum qualiEnum = QualificationEnum.valueOf(QualificationEnum.class, qualification.toUpperCase());
        CitizenshipEnum citiEnum = CitizenshipEnum.valueOf(CitizenshipEnum.class, citizenship.toUpperCase());
        RaceEnum raceEnum = RaceEnum.valueOf(RaceEnum.class, race.toUpperCase());

        String dob = json.getJsonString("dob").getString();
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
            return Response.status(400).entity(exception).build();
        }
    }
}
