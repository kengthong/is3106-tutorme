/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import entity.Staff;
import exception.StaffNotFoundException;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import session.StaffSessionLocal;

/**
 *
 * @author Owen Tay
 */
@Provider
@StaffJWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class StaffJWTTokenNeededFilter implements ContainerRequestFilter {

    @EJB
    StaffSessionLocal staffSession;
    private static final String key = "hsianghui";
    private static final Verifier verifier = HMACVerifier.newVerifier(key);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        System.out.println("#### authorizationHeader : " + authorizationHeader);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("#### invalid authorizationHeader : " + authorizationHeader);
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validate the token
            JWT jwt = JWT.getDecoder().decode(token, verifier);
            Map<String, Object> claims = jwt.getAllClaims();
            System.out.println(claims);
            String personId = String.valueOf(claims.get("personId"));
            System.out.println("#### JWTToken's personId : " + personId);
            System.out.println("#### valid token : " + token);

            Staff staff = staffSession.retrieveStaffById(Long.valueOf(personId));
            
            System.out.println("#### Running secuityContext : ");
            SecurityContext originalContext = requestContext.getSecurityContext();
            Authorizer authorizer = new Authorizer("staff", Long.valueOf(personId), true);
            requestContext.setSecurityContext(authorizer);
        } catch (StaffNotFoundException e) {
            System.out.println("#### invalid token : " + token);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
