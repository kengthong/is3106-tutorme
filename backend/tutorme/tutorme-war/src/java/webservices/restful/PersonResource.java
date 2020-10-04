/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import entity.Person;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import session.PersonSessionLocal;
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
    
    private final String key = "hsianghui";
    private final HMACSigner signer = HMACSigner.newSHA256Signer(key);
    Verifier verifier = HMACVerifier.newVerifier(key);

    public PersonResource() {
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
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
}