/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Subject;
import exception.SubjectNotFoundException;
import filter.JWTTokenNeeded;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.SubjectSessionLocal;

/**
 * REST Web Service
 *
 * @author Owen Tay
 */
@Path("/subject")
@RequestScoped
public class SubjectResource {

    @EJB
    SubjectSessionLocal subjectSession;

    public SubjectResource() {
    }

    @GET
    @Path("/subjectList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubjectList() {
        System.out.println("Getting all subjects...");
        List<Subject> subjects = subjectSession.retrieveAllSubjects();
        GenericEntity<List<Subject>> payload = new GenericEntity<List<Subject>>(subjects) {
        };
        return Response.status(200).entity(payload).build();
    }

    @POST
    @Path("/newSubject")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubject(JsonObject json) {
        System.out.println("Adding new subject...");
        String subjectLevel = json.getString("subjectLevel");
        String subjectName = json.getString("subjectName");
        Subject newSubject = subjectSession.createSubject(subjectLevel, subjectName);
        List<Subject> subjects = subjectSession.retrieveAllSubjects();
        GenericEntity<List<Subject>> payload = new GenericEntity<List<Subject>>(subjects) {
        };
        return Response.status(201).entity(payload).build();
    }

    @DELETE
    @Path("/{subjectId}")
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubject(@PathParam("subjectId") Long subjectId) {
        try {
            System.out.println("Adding new subject...");
            subjectSession.deleteSubject(subjectId);
            List<Subject> subjects = subjectSession.retrieveAllSubjects();
            GenericEntity<List<Subject>> payload = new GenericEntity<List<Subject>>(subjects) {
            };
            return Response.status(200).entity(payload).build();
        } catch (SubjectNotFoundException ex) {
            JsonObject exception = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return Response.status(400).entity(exception).build();
        }
    }
    
}
