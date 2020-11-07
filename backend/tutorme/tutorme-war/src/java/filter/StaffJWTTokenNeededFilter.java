/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import enumeration.PersonEnum;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Owen Tay
 */
@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class StaffJWTTokenNeededFilter implements ContainerRequestFilter {

    private static final String key = "hsianghui";
    private static final Verifier verifier = HMACVerifier.newVerifier(key);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("AM i hjere");
        SecurityContext originalContext = requestContext.getSecurityContext();
        Set<PersonEnum> roles = new HashSet<>();
        roles.add(PersonEnum.STAFF);
        Authorizer authorizer = new Authorizer(roles, "admin",
                originalContext.isSecure());
        requestContext.setSecurityContext(authorizer);
    }

    

//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//
//        // Get the HTTP Authorization header from the request
//        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//        System.out.println("#### authorizationHeader : " + authorizationHeader);
//
//        // Check if the HTTP Authorization header is present and formatted correctly
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            System.out.println("#### invalid authorizationHeader : " + authorizationHeader);
//            throw new NotAuthorizedException("Authorization header must be provided");
//        }
//
//        // Extract the token from the HTTP Authorization header
//        String token = authorizationHeader.substring("Bearer".length()).trim();
//
//        try {
//            // Validate the token
//            JWT jwt = JWT.getDecoder().decode(token, verifier);
//            Map<String, Object> claims = jwt.getAllClaims();
//            System.out.println(claims);
//            Object personId = claims.get("personId");
//            System.out.println(personId);
//            System.out.println("#### valid token : " + token);
//        } catch (Exception e) {
//            System.out.println("#### invalid token : " + token);
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//        }
//    }
//    
}
