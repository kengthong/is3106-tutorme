/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import entity.Staff;
import enumeration.PersonEnum;
import java.security.Principal;
import java.util.Set;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Owen Tay
 */
public class Authorizer implements SecurityContext {

        Set<PersonEnum> roles;
        String username;
        boolean isSecure;

        public Authorizer(Set<PersonEnum> roles, final String username,
                boolean isSecure) {
            this.roles = roles;
            this.username = username;
            this.isSecure = isSecure;
        }

        @Override
        public Principal getUserPrincipal() {
            return new Staff();
        }

        @Override
        public boolean isUserInRole(String role) {
            if (role.equals("Staff")) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isSecure() {
            return isSecure;
        }

        @Override
        public String getAuthenticationScheme() {
            return "Your Scheme";
        }
    }
