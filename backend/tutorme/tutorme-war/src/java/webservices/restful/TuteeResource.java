/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Tutee;
import enumeration.GenderEnum;
import exception.TuteeNotFoundException;
import filter.TuteeJWTTokenNeeded;
import filter.UserPrincipal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import session.TuteeSessionLocal;

/**
 *
 * @author Enelton Satria
 */
@Path("/tutee")
public class TuteeResource {

    @EJB
    TuteeSessionLocal tuteeSession;

    public TuteeResource() {
    }

    @GET
    @Path("/get")
    @TuteeJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTuteeById(@Context SecurityContext securityContext) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        Long tuteeId = person.getPersonId();
        System.out.println("Tutee Id is... " + tuteeId);
        try {
            Tutee tutee = tuteeSession.retrieveTuteeById(tuteeId);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setOffers(null); // to confirm not needed
            GenericEntity<Tutee> packet = new GenericEntity<Tutee>(tutee) {
            };
            return Response.status(200).entity(packet).build();
        } catch (TuteeNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tuteeId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/update")
    @TuteeJWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTuteeById(@Context SecurityContext securityContext, JsonObject json) {
        UserPrincipal person = (UserPrincipal) securityContext.getUserPrincipal();
        Long tuteeId = person.getPersonId();
        System.out.println("Updating Tutee Id is ... " + tuteeId);

        String firstName = json.getJsonString("firstName").getString();
        String lastName = json.getJsonString("lastName").getString();
        String mobileNum = json.getJsonString("mobileNum").getString();
        String gender = json.getJsonString("gender").getString();
        GenderEnum genderEnum = gender.equals("M") ? GenderEnum.MALE : GenderEnum.FEMALE;
        String dob = json.getJsonString("dob").getString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
        Date parsedDob = new Date();
        try {
            parsedDob = formatter.parse(dob);
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        String profileDesc = json.getJsonString("profileDesc").getString();
        String encodedProfileImage = json.getString("profileImage");
        byte[] profileImage = Base64.getDecoder().decode(encodedProfileImage);

        try {
            Tutee tutee = tuteeSession.updateTuteeProfile(tuteeId, firstName, lastName, mobileNum, genderEnum, parsedDob, profileDesc, profileImage);
            tutee.setPassword(null);
            tutee.setSalt(null);
            tutee.setReceivedMessages(null);
            tutee.setSentMessages(null);
            tutee.setOffers(null); // to confirm not needed
            GenericEntity<Tutee> packet = new GenericEntity<Tutee>(tutee) {
            };
            return Response.status(200).entity(packet).build();
        } catch (TuteeNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tuteeId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }
}
