/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Tutee;
import enumeration.GenderEnum;
import exception.TuteeNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.TuteeSessionLocal;

/**
 *
 * @author Enelton Satria
 */
public class TuteeResource {

    @EJB
    TuteeSessionLocal tuteeSession;

    /**
     * Creates a new instance of TutorResource
     */
    public TuteeResource() {
    }

    @GET
    @Path("/getTutees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutors() {
        List<Tutee> toReturn = null;
        toReturn = tuteeSession.retrieveAllTutees();
        if (toReturn != null) {
            GenericEntity<List<Tutee>> result = new GenericEntity<List<Tutee>>(toReturn) {
            };
            return Response.status(200).entity(result).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned null value from REST/getTutors").build();
            return Response.status(400).entity(exception).build();
        }
//        System.out.println("hello world");

    }

    @GET
    @Path("/getTuteeById")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutorById(@QueryParam("tutorId") Long tutorId) {
        Tutee toReturn = null;
        try {
            toReturn = tuteeSession.retrieveTuteeById(tutorId);
        } catch (TuteeNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tuteeId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
        if (toReturn != null) {
            GenericEntity<Tutee> result = new GenericEntity<Tutee>(toReturn) {
            };
            return Response.status(200).entity(result).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned null value from REST/getTuteeById").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @POST
    @Path("/createTutee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Tutee createTutor(Tutee tutee) {
        tutee.setCreatedDate(new Date());
        tuteeSession.createTutee(tutee);
        return tutee;
    }

    @PUT
    @Path("/editTuteeProfile/{tuteeId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTutee(@PathParam("tuteeId") Long tuteeId, Tutee tutee) {

        Tutee selectedTutee = null;

        try {
            selectedTutee = tuteeSession.retrieveTuteeById(tuteeId);
            String firstName = tutee.getFirstName();
            String lastName = tutee.getLastName();
            String mobileNum = tutee.getMobileNum();
            GenderEnum gender = tutee.getGender();
            Date dob = tutee.getDob();
            String profileDesc = tutee.getProfileDesc();
            tuteeSession.updateTutee(tuteeId, firstName, lastName, mobileNum, gender, dob, profileDesc);
            return Response.status(200).build();
        } catch (TuteeNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tuteeId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
    }
}
