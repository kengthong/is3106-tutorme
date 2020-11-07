/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Tutee;
import enumeration.GenderEnum;
import exception.TuteeNotFoundException;
import filter.JWTTokenNeeded;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("/tutees")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutees() {
        System.out.println("Getting tutees...");
        List<Tutee> tutees = new ArrayList();
        tutees = tuteeSession.retrieveAllTutees();
        if (!tutees.isEmpty()) {
            for (Tutee t : tutees) {
                t.setSalt(null);
                t.setPassword(null);
                t.setReceivedMessages(null);
                t.setSentMessages(null);
                t.setOffers(null); //to confirm not needed
            }
            GenericEntity<List<Tutee>> packet = new GenericEntity<List<Tutee>>(tutees) {
            };
            return Response.status(200).entity(packet).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned empty list from REST/getTutees").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @GET
    @Path("/{tuteeId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTuteeById(@PathParam("tuteeId") Long tuteeId) {
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
    @Path("/{tuteeId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTuteeById(@PathParam("tuteeId") Long tuteeId, JsonObject json) {
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

        try {
            Tutee tutee = tuteeSession.updateTuteeProfile(tuteeId, firstName, lastName, mobileNum, genderEnum, parsedDob, profileDesc);
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
    @Path("/ban/{tuteeId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response banTuteeById(@PathParam("tuteeId") Long tuteeId) {
        System.out.println("Banning Tutee Id is ... " + tuteeId);
        try {
            Tutee tutee = tuteeSession.deactivateTuteeStatus(tuteeId);
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
    @Path("/unban/{tuteeId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response unbanTuteeById(@PathParam("tuteeId") Long tuteeId) {
        System.out.println("Banning Tutee Id is ... " + tuteeId);
        try {
            Tutee tutee = tuteeSession.activateTuteeStatus(tuteeId);
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
