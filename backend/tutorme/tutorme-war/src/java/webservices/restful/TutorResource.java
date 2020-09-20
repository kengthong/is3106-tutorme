/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Tutor;
import exception.TutorNotFoundException;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        List<Tutor> toReturn = null;
        toReturn = tutorSession.retrieveAllTutors();
        if (toReturn != null) {
            GenericEntity<List<Tutor>> result = new GenericEntity<List<Tutor>>(toReturn) {
            };
            return Response.status(200).entity(result).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned null value from REST/getTutors").build();
            return Response.status(400).entity(exception).build();
        }
//        System.out.println("hello world");
    }

    @GET
    @Path("/getTutorById")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTutorById(@QueryParam("tutorId") Long tutorId) {
        Tutor toReturn = null;
        try {
            toReturn = tutorSession.retrieveTutorById(tutorId);
        } catch (TutorNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", "tutorId does not exists").build();
            return Response.status(400).entity(exception).build();
        }
        if (toReturn != null) {
            GenericEntity<Tutor> result = new GenericEntity<Tutor>(toReturn) {
            };
            return Response.status(200).entity(result).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "returned null value from REST/getTutorById").build();
            return Response.status(400).entity(exception).build();
        }
    }

}
