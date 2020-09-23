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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

/**
 * REST Web Service
 *
 * @author Owen Tay
 */

@Path("/tutor")
public class TutorResource {

//    @Inject 
//    RatingSessionLocal ratingSession;
    RatingSessionLocal ratingSession = lookupRatingSessionLocal();
//    @Inject 
//    TutorSessionLocal tutorSession;
    TutorSessionLocal tutorSession = lookupTutorSessionLocal();

    /**
     * Creates a new instance of TutorResource
     */
    public TutorResource() {
    }

    /**
     * Retrieves representation of an instance of
     * webservices.restful.TutorResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/getTutors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutors() {
        System.out.println("Getting tutors... ");
        List<Tutor> tutors = new ArrayList();
        tutors = tutorSession.retrieveAllTutors();
        if (!tutors.isEmpty()) {
            for (Tutor t : tutors) {
                t.setSalt(null);
                t.setPassword(null);
                t.setMessages(null);
                t.setJobListings(null);
            }
            GenericEntity<List<Tutor>> packet = new GenericEntity<List<Tutor>>(tutors) {
            };
            return Response.status(200).entity(packet).type(MediaType.APPLICATION_JSON).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned empty list from REST/getTutors").build();
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
            return Response.status(200).entity(packet).type(MediaType.APPLICATION_JSON).build();
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tutorId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/tutorProfile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTutorById(@QueryParam("tutorId") Long tutorId,
            @QueryParam("firstName") String firstName,
            @QueryParam("lastName") String lastName,
            @QueryParam("mobileNum") String mobileNum,
            @QueryParam("gender") String gender,
            @QueryParam("dob") String dob,
            @QueryParam("highestQualification") String highestQualification,
            @QueryParam("citizenship") String citizenship,
            @QueryParam("race") String race,
            @QueryParam("profileDesc") String profileDesc
    ) {
        System.out.println("Updating Tutor Id is ... " + tutorId);
        try {
            GenderEnum genderEnum = gender.equals('M') ? GenderEnum.MALE : GenderEnum.FEMALE;
            QualificationEnum qualiEnum = QualificationEnum.valueOf(QualificationEnum.class, highestQualification.toUpperCase());
            CitizenshipEnum citiEnum = CitizenshipEnum.valueOf(CitizenshipEnum.class, citizenship.toUpperCase());
            RaceEnum raceEnum = RaceEnum.valueOf(RaceEnum.class, race.toUpperCase());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
            Date parsedDob = new Date();
            try {
                parsedDob = formatter.parse(dob);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            Tutor result = tutorSession.updateTutorProfile(tutorId, firstName, lastName, mobileNum, genderEnum, parsedDob, qualiEnum, citiEnum, raceEnum, profileDesc);
            result.setPassword(null);
            result.setSalt(null);
            result.setMessages(null);
            result.setJobListings(null);
            GenericEntity<Tutor> packet = new GenericEntity<Tutor>(result) {
            };
            return Response.status(200).entity(packet).type(MediaType.APPLICATION_JSON).build();
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tutorId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }

    private TutorSessionLocal lookupTutorSessionLocal() {
        try {
            Context c = new InitialContext();
            return (TutorSessionLocal) c.lookup("java:global/tutorme/tutorme-ejb/TutorSession!session.TutorSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RatingSessionLocal lookupRatingSessionLocal() {
        try {
            Context c = new InitialContext();
            return (RatingSessionLocal) c.lookup("java:global/tutorme/tutorme-ejb/RatingSession!session.RatingSessionLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
