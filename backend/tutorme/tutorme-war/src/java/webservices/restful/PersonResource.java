/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Person;
import entity.Staff;
import entity.Tutee;
import entity.Tutor;
import enumeration.GenderEnum;
import enumeration.StaffPositionEnum;
import exception.BannedPersonException;
import exception.PersonLoginFailException;
import exception.PersonNotFoundException;
import exception.RegistrationFailException;
import exception.StaffNotFoundException;
import exception.TuteeNotFoundException;
import exception.TutorNotFoundException;
import filter.JWTTokenNeeded;
import filter.UserPrincipal;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import session.EmailSessionLocal;
import session.PersonSessionLocal;
import session.StaffSessionLocal;
import session.TuteeSessionLocal;
import session.TutorSessionLocal;
import utils.AuthenticateUser;
import org.glassfish.jersey.media.multipart.FormDataParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import javax.ws.rs.Consumes;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/person")
@RequestScoped
public class PersonResource implements Serializable {

    @EJB
    PersonSessionLocal personSession;
    @EJB
    TuteeSessionLocal tuteeSession;
    @EJB
    TutorSessionLocal tutorSession;
    @EJB
    StaffSessionLocal staffSession;
    @EJB
    EmailSessionLocal emailSession;

    public PersonResource() {
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginPerson(JsonObject json) throws PersonLoginFailException {
        System.out.println("Logging user in...");
        String email = json.getString("email").toLowerCase();
        String password = json.getString("password");

        JsonObjectBuilder payload = Json.createObjectBuilder();
        JsonObjectBuilder exception = Json.createObjectBuilder();
        try {
            Person person = personSession.login(email, password);
            String encodedJWT = AuthenticateUser.issueJwt(person.getPersonId());
            payload.add("jwtToken", encodedJWT);
            System.out.println(encodedJWT);
            ObjectMapper mapper = new ObjectMapper();
            switch (person.getPersonEnum()) {
                case TUTOR:
                    Tutor tutor = tutorSession.retrieveTutorById(person.getPersonId());
                    tutor.setSalt(null);
                    tutor.setPassword(null);
                    tutor.setSentMessages(null);
                    tutor.setReceivedMessages(null);
                    tutor.setJobListings(null);

                    String jsonTutor = mapper.writeValueAsString(tutor);
                    payload.add("user", jsonTutor);
                    return Response.status(200).entity(payload.build()).build();
                case TUTEE:
                    Tutee tutee = tuteeSession.retrieveTuteeById(person.getPersonId());
                    tutee.setSalt(null);
                    tutee.setPassword(null);
                    tutee.setSentMessages(null);
                    tutee.setReceivedMessages(null);
                    tutee.setOffers(null);

                    String jsonTutee = mapper.writeValueAsString(tutee);
                    payload.add("user", jsonTutee);
                    return Response.status(200).entity(payload.build()).build();
                case STAFF:
                    Staff staff = staffSession.retrieveStaffById(person.getPersonId());
                    staff.setSalt(null);
                    staff.setPassword(null);
                    staff.setSentMessages(null);
                    staff.setReceivedMessages(null);

                    String jsonStaff = mapper.writeValueAsString(staff);
                    payload.add("user", jsonStaff);
                    return Response.status(200).entity(payload.build()).build();
            }
        } catch (TutorNotFoundException | TuteeNotFoundException | StaffNotFoundException | JsonProcessingException | PersonLoginFailException ex) {
            exception.add("error", ex.getMessage());
            return Response.status(401).entity(exception.build()).build();
        } catch (BannedPersonException ex) {
            exception.add("error", "User has been banned.");
            return Response.status(403).entity(exception.build()).build();
        }
        exception.add("error", "Unknown error when logging in.");
        return Response.status(400).entity(exception.build()).build();
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(JsonObject json) {
        System.out.println("Registering new user...");
        JsonObjectBuilder payload = Json.createObjectBuilder();
        JsonObjectBuilder exception = Json.createObjectBuilder();
        try {
            String firstName = json.getString("firstName");
            String lastName = json.getString("lastName");
            String email = json.getString("email").toLowerCase();
            String password = json.getString("password");
            String mobileNum = json.getString("phoneNumber");
            String gender = json.getString("gender");
            GenderEnum genderEnum = gender.equals("Male") ? GenderEnum.MALE : GenderEnum.FEMALE;
            String dobStr = json.getString("date");
            String pattern = "dd-MM-YYYY";
            SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
            Date dob;
            dob = dateFormatter.parse(dobStr);

            String userType = json.getString("accountType");
            String encodedJWT = null;

            ObjectMapper mapper = new ObjectMapper();
            switch (userType) {
                case "tutor":
                    Tutor tutor = tutorSession.createTutor(firstName, lastName, email, password, mobileNum, genderEnum, dob);
                    tutor.setSalt(null);
                    tutor.setPassword(null);
                    tutor.setSentMessages(null);
                    tutor.setReceivedMessages(null);
                    tutor.setJobListings(null);
                    encodedJWT = AuthenticateUser.issueJwt(tutor.getPersonId());
                    String jsonTutor = mapper.writeValueAsString(tutor);
                    payload.add("user", jsonTutor);
                    emailSession.send(tutor.getFirstName(), tutor.getEmail());
                    break;
                case "tutee":
                    Tutee tutee = tuteeSession.createTutee(firstName, lastName, email, password, mobileNum, genderEnum, dob);
                    tutee.setSalt(null);
                    tutee.setPassword(null);
                    tutee.setSentMessages(null);
                    tutee.setReceivedMessages(null);
                    tutee.setOffers(null);
                    encodedJWT = AuthenticateUser.issueJwt(tutee.getPersonId());
                    String jsonTutee = mapper.writeValueAsString(tutee);
                    payload.add("user", jsonTutee);
                    emailSession.send(tutee.getFirstName(), tutee.getEmail());
                    break;
                case "staff":
                    Staff staff = staffSession.createStaff(firstName, lastName, email, password, mobileNum, genderEnum, dob, StaffPositionEnum.OPERATOR);
                    staff.setSalt(null);
                    staff.setPassword(null);
                    staff.setSentMessages(null);
                    staff.setReceivedMessages(null);
                    encodedJWT = AuthenticateUser.issueJwt(staff.getPersonId());
                    String jsonStaff = mapper.writeValueAsString(staff);
                    payload.add("user", jsonStaff);
                    emailSession.send(staff.getFirstName(), staff.getEmail());
                    break;
            }
            payload.add("jwtToken", encodedJWT);
            return Response.status(201).entity(payload.build()).build();
        } catch (RegistrationFailException ex) {
            exception.add("error", ex.getMessage());
            return Response.status(409).entity(exception.build()).build();
        } catch (JsonProcessingException | ParseException ex) {
            exception.add("error", ex.getMessage());
            return Response.status(400).entity(exception.build()).build();
        }
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response getPerson(@Context SecurityContext securityContext) throws PersonLoginFailException {
        System.out.println("Getting person in...");
        UserPrincipal p = (UserPrincipal) securityContext.getUserPrincipal();
        Long personId = p.getPersonId();

        JsonObjectBuilder exception = Json.createObjectBuilder();
        try {
            Person person = personSession.retrievePersonById(personId);
            switch (person.getPersonEnum()) {
                case TUTOR:
                    Tutor tutor = tutorSession.retrieveTutorById(personId);
                    tutor.setSalt(null);
                    tutor.setPassword(null);
                    tutor.setSentMessages(null);
                    tutor.setReceivedMessages(null);
                    tutor.setJobListings(null);

                    GenericEntity<Tutor> payload1 = new GenericEntity<Tutor>(tutor) {
                    };
                    return Response.status(200).entity(payload1).build();
                case TUTEE:
                    Tutee tutee = tuteeSession.retrieveTuteeById(personId);
                    tutee.setSalt(null);
                    tutee.setPassword(null);
                    tutee.setSentMessages(null);
                    tutee.setReceivedMessages(null);
                    tutee.setOffers(null);

                    GenericEntity<Tutee> payload2 = new GenericEntity<Tutee>(tutee) {
                    };
                    return Response.status(200).entity(payload2).build();
                case STAFF:
                    Staff staff = staffSession.retrieveStaffById(personId);
                    staff.setSalt(null);
                    staff.setPassword(null);
                    staff.setSentMessages(null);
                    staff.setReceivedMessages(null);

                    GenericEntity<Staff> payload3 = new GenericEntity<Staff>(staff) {
                    };
                    return Response.status(200).entity(payload3).build();
            }
        } catch (TutorNotFoundException | TuteeNotFoundException | StaffNotFoundException | PersonNotFoundException ex) {
            exception.add("error", ex.getMessage());
            return Response.status(401).entity(exception.build()).build();
        }
        exception.add("error", "Unknown error when getting person after updating profile.");
        return Response.status(400).entity(exception.build()).build();
    }

    @POST
    @Path("/uploadImage")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@Context SecurityContext securityContext, JsonObject json) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        Long personId = person.getPersonId();
        
        String encodedImage = json.getString("profileImage");

        JsonObjectBuilder exception = Json.createObjectBuilder();
        try {
            Person user = personSession.uploadImage(personId, encodedImage);
            switch (user.getPersonEnum()) {
                case TUTOR:
                    Tutor tutor = tutorSession.retrieveTutorById(personId);
                    tutor.setSalt(null);
                    tutor.setPassword(null);
                    tutor.setSentMessages(null);
                    tutor.setReceivedMessages(null);
                    tutor.setJobListings(null);

                    GenericEntity<Tutor> payload1 = new GenericEntity<Tutor>(tutor) {
                    };
                    return Response.status(200).entity(payload1).build();
                case TUTEE:
                    Tutee tutee = tuteeSession.retrieveTuteeById(personId);
                    tutee.setSalt(null);
                    tutee.setPassword(null);
                    tutee.setSentMessages(null);
                    tutee.setReceivedMessages(null);
                    tutee.setOffers(null);

                    GenericEntity<Tutee> payload2 = new GenericEntity<Tutee>(tutee) {
                    };
                    return Response.status(200).entity(payload2).build();
                case STAFF:
                    Staff staff = staffSession.retrieveStaffById(personId);
                    staff.setSalt(null);
                    staff.setPassword(null);
                    staff.setSentMessages(null);
                    staff.setReceivedMessages(null);

                    GenericEntity<Staff> payload3 = new GenericEntity<Staff>(staff) {
                    };
                    return Response.status(200).entity(payload3).build();
            }
        } catch (TutorNotFoundException | TuteeNotFoundException | StaffNotFoundException | PersonNotFoundException ex) {
            exception.add("error", ex.getMessage());
            return Response.status(401).entity(exception.build()).build();
        }
        exception.add("error", "Unknown error when getting person after updating profile.");
        return Response.status(400).entity(exception.build()).build();
    }
}
