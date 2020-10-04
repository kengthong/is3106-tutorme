/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Tutor;
import enumeration.CitizenshipEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import exception.TutorNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.RatingSessionLocal;
import session.TutorSessionLocal;
import utils.AuthenticateUser;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/tutor")
public class TutorResource {

    @EJB
    RatingSessionLocal ratingSession;
    @EJB
    TutorSessionLocal tutorSession;

    public TutorResource() {
    }

    @POST
    @Path("/getTutors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutors(JsonObject json) {
        System.out.println("Getting tutors... ");
        if (AuthenticateUser.verifyJwt(json)) {
            List<Tutor> tutors = new ArrayList();
            tutors = tutorSession.retrieveAllTutors();
            if (!tutors.isEmpty()) {
                for (Tutor t : tutors) {
                    t.setSalt(null);
                    t.setPassword(null);
                    t.setMessages(null);
                    t.setJobListings(null);
                    System.out.println(t);
                }
                GenericEntity<List<Tutor>> packet = new GenericEntity<List<Tutor>>(tutors) {
                };
                return Response.status(200).entity(packet).build();
            } else {
                JsonObject exception = Json.createObjectBuilder().add("error", "returned empty list from REST/getTutors").build();
                return Response.status(400).entity(exception).build();
            }
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "Unauthorized or missing JWT.").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Path("/tutorProfile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutorById(@QueryParam("tutorId") Long tutorId) {
        System.out.println("Tutor Id is... " + tutorId);
        try {
            Tutor result = tutorSession.retrieveTutorById(tutorId);
            result.setPassword(null);
            result.setSalt(null);
            result.setMessages(null);
            result.setJobListings(null);
            GenericEntity<Tutor> packet = new GenericEntity<Tutor>(result) {
            };
            return Response.status(200).entity(packet).build();
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tutorId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/tutorProfile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTutorById(JsonObject json) {
        Long tutorId = Long.valueOf(json.getJsonString("tutorId").getString());
        System.out.println("Updating Tutor Id is ... " + tutorId);

        String firstName = json.getJsonString("firstName").getString();
        String lastName = json.getJsonString("lastName").getString();
        String mobileNum = json.getJsonString("mobileNum").getString();

        String gender = json.getJsonString("gender").getString();
        String qualification = json.getJsonString("highestQualification").getString();
        String citizenship = json.getJsonString("citizenship").getString();
        String race = json.getJsonString("race").getString();
        String profileDesc = json.getJsonString("profileDesc").getString();

        GenderEnum genderEnum = gender.equals("M") ? GenderEnum.MALE : GenderEnum.FEMALE;
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
            Tutor result = tutorSession.updateTutorProfile(tutorId, firstName, lastName, mobileNum, genderEnum, parsedDob, qualiEnum, citiEnum, raceEnum, profileDesc);
//            *Start debug code
//            result.setPassword(null);
//            result.setSalt(null);
//            result.setMessages(null);
//            result.setJobListings(null);
//            GenericEntity<Tutor> packet = new GenericEntity<Tutor>(result) {
//            };
//            *End debug code
            JsonObjectBuilder payload = Json.createObjectBuilder();
            payload.add("tutorId", tutorId);
            payload.add("success", true);
            return Response.status(200).entity(payload.build()).build();
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tutorId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }
}
