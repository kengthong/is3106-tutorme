/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Person;
import exception.PersonNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface PersonSessionLocal {
    
    public Person login(String email, String password);

    public Person retrievePersonById(Long userId) throws PersonNotFoundException;
    
    public List<Person> retrieveAllPersons();

    public List<Person> retrieveAllPersons();

    public List<Person> retrievePersonsByName(String inputName) throws PersonNotFoundException;

    public Person changePassword(Long userId, String oldPassword, String newPassword);

    public void changePersonActiveStatus(Long userId);
}
