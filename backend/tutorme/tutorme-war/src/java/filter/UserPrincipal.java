/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.security.Principal;

/**
 *
 * @author Owen Tay
 */
public class UserPrincipal implements Principal {

    String name;
    Long personId;

    public UserPrincipal(String name, Long personId) {
        this.name = name;
        this.personId = personId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPersonId() {
        return this.personId;
    }

    @Override
    public String getName() {
        return name;
    }
}
