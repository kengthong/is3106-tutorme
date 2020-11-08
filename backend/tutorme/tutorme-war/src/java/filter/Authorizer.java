/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import entity.Person;
import exception.PersonNotFoundException;
import java.security.Principal;
import javax.ejb.EJB;
import javax.ws.rs.core.SecurityContext;
import session.PersonSessionLocal;

/**
 *
 * @author Owen Tay
 */
public class Authorizer implements SecurityContext {

//        Set<PersonEnum> roles;
//        String username;
//        boolean isSecure;
//        public Authorizer(Set<PersonEnum> roles, final String username,
//                boolean isSecure) {
//            this.roles = roles;
//            this.username = username;
//            this.isSecure = isSecure;
//        }
    @EJB
    PersonSessionLocal personSession;

    String role;
    Long personId;
    boolean isSecure;

    public Authorizer(String role, Long staffId, Boolean isSecure) {
        this.role = role;
        this.personId = staffId;
        this.isSecure = isSecure;
    }

    @Override
    public Principal getUserPrincipal() {
        System.out.println("$$$$ Authorizer's getUserPrincipal PersonId: " + this.personId);
        UserPrincipal up = new UserPrincipal(this.role, this.personId);
        return up;
    }

    @Override
    public boolean isUserInRole(String role) {
        return this.isUserInRole(role);
    }

    @Override
    public boolean isSecure() {
        return this.isSecure();
    }

    @Override
    public String getAuthenticationScheme() {
        return "Your Scheme";
    }
}
