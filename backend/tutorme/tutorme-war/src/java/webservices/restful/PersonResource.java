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
import entity.Person;
import entity.Staff;
import entity.Tutee;
import entity.Tutor;
import enumeration.GenderEnum;
import exception.PersonLoginFailException;
import exception.StaffNotFoundException;
import exception.TuteeNotFoundException;
import exception.TutorNotFoundException;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.EmailSessionLocal;
import session.PersonSessionLocal;
import session.StaffSessionLocal;
import session.TuteeSessionLocal;
import session.TutorSessionLocal;
import utils.AuthenticateUser;

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

    private final String key = "hsianghui";
    private final HMACSigner signer = HMACSigner.newSHA256Signer(key);
    Verifier verifier = HMACVerifier.newVerifier(key);

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
            ObjectMapper mapper = new ObjectMapper();
            switch (person.getPersonEnum()) {
                case TUTOR:
                    Tutor tutor = tutorSession.retrieveTutorById(person.getPersonId());
                    tutor.setSalt(null);
                    tutor.setPassword(null);
                    tutor.setSentMessages(null);
                    tutor.setReceivedMessages(null);
                    List<JobListing> jobListings = tutor.getJobListings();
                    jobListings.forEach(jl -> jl.setTutor(null));
                    for (JobListing jl : jobListings) {
                        jl.setTutor(null);
                        List<Offer> offers = jl.getOffers();
                        for (Offer o : offers) {
                            o.setJobListing(null);
                            o.setTutee(null);
                        }
                    }
                    String jsonTutor = mapper.writeValueAsString(tutor);
                    payload.add("user", jsonTutor);
                    return Response.status(200).entity(payload.build()).build();
                case TUTEE:
                    Tutee tutee = tuteeSession.retrieveTuteeById(person.getPersonId());
                    tutee.setSalt(null);
                    tutee.setPassword(null);
                    tutee.setSentMessages(null);
                    tutee.setReceivedMessages(null);
                    List<Offer> offers = tutee.getOffers();
                    for (Offer o : offers) {
                        o.setTutee(null);
                        JobListing jobListing = o.getJobListing();
                        jobListing.setOffers(null);
                        jobListing.setTutor(null);
                    }
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
        } catch (TutorNotFoundException | TuteeNotFoundException | StaffNotFoundException | JsonProcessingException ex) {
            exception.add("Login error", ex.getMessage());
            return Response.status(401).entity(exception).build();
        }
        exception.add("Unknown error", "Unknown error when logging in.");
        return Response.status(400).entity(exception).build();
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
            String mobileNum = json.getString("mobileNum");
            String gender = json.getString("gender");
            GenderEnum genderEnum = gender.equals("Male") ? GenderEnum.MALE : GenderEnum.FEMALE;
            String dobStr = json.getString("dob");
            String pattern = "dd-MM-YYYY";
            SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
            Date dob;
            dob = dateFormatter.parse(dobStr);

            String userType = json.getString("userType");
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
            }
            payload.add("jwtToken", encodedJWT);
            return Response.status(200).entity(payload.build()).build();
        } catch (ParseException | JsonProcessingException ex) {
            exception.add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }
}
