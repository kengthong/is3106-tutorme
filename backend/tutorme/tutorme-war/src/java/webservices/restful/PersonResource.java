/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Person;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    private final String key = "hsianghui";
    private final HMACSigner signer = HMACSigner.newSHA256Signer(key);
    Verifier verifier = HMACVerifier.newVerifier(key);

    public PersonResource() {
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginPerson(JsonObject json) {
        System.out.println("Logging user in...");
        String email = json.getString("email").toLowerCase();
        String password = json.getString("password");
        Person person = personSession.login(email, password);
        if (person != null) {
            String encodedJWT = AuthenticateUser.issueJwt(person.getPersonId());
            return Response.status(200).entity(encodedJWT).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No such user.").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/registerTutee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerTutee(JsonObject json) {
        System.out.println("Registering new tutee...");
        String email = json.getString("email").toLowerCase();
        String password = json.getString("password");
        Person person = personSession.login(email, password);
        if (person != null) {
            String encodedJWT = AuthenticateUser.issueJwt(person.getPersonId());
            return Response.status(200).entity(encodedJWT).build();
        } else {
            JsonObject exception = Json.createObjectBuilder().add("error", "No such user.").build();
            return Response.status(400).entity(exception).build();
        }
    }

    @PUT
    @Path("/registerTutor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerTutor(JsonObject json) {
        System.out.println("Registering new tutor...");
        JsonObject exception = Json.createObjectBuilder().add("error", "No such user.").build();
        return Response.status(400).entity(exception).build();
    }

    @PUT
    @Path("/registerStaff")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerStaff(JsonObject json) {
        System.out.println("Registering new staff...");
        JsonObject exception = Json.createObjectBuilder().add("error", "No such user.").build();
        return Response.status(400).entity(exception).build();
    }
}
