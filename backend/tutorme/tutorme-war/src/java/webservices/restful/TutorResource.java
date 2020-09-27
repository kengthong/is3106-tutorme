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
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

//    RatingSessionLocal ratingSession = lookupRatingSessionLocal();
//
//    TutorSessionLocal tutorSession = lookupTutorSessionLocal();
    @EJB
    RatingSessionLocal ratingSession;
    @EJB
    TutorSessionLocal tutorSession;

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
                System.out.println(t);
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
    @Consumes(MediaType.APPLICATION_JSON)
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

    @POST
    @Path("/createTutor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Tutor createTutor(Tutor tutor) {
        tutor.setCreatedDate(new Date());
        tutorSession.createTutor(tutor);
        return tutor;
    }

    @PUT
    @Path("/editTutorProfile/{tutorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTutor(@PathParam("tutorId") Long tutorId, Tutor tutor) {

        Tutor selectedTutor = null;

        try {
            selectedTutor = tutorSession.retrieveTutorById(tutorId);
            String firstName = tutor.getFirstName();
            String lastName = tutor.getLastName();
            String mobileNum = tutor.getMobileNum();
            GenderEnum gender = tutor.getGender();
            Date dob = tutor.getDob();
            QualificationEnum highestQualification = tutor.getHighestQualification();
            CitizenshipEnum citizenship = tutor.getCitizenship();
            RaceEnum race = tutor.getRace();
            String profileDesc = tutor.getProfileDesc();
            tutorSession.updateTutorProfile(tutorId, firstName, lastName, mobileNum, gender, dob, highestQualification, citizenship, race, profileDesc);
            return Response.status(200).build();
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tutorId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }
}
