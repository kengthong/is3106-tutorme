/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import javax.ejb.Local;

/**
 *
 * @author Owen Tay
 */
@Local
public interface EmailSessionLocal {
    
    public void send(String username, String email);
}
