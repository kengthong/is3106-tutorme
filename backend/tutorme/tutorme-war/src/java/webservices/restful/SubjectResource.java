/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Subject;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
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
    public Response getSujectList() {
        System.out.println("Getting all subjects...");
        List<Subject> subjects = subjectSession.retrieveAllSubjects();
        for (Subject s : subjects) {
            s.setSubjectId(null);
        }
        GenericEntity<List<Subject>> payload = new GenericEntity<List<Subject>>(subjects) {
        };
        return Response.status(200).entity(payload).type(MediaType.APPLICATION_JSON).build();
    }
}
