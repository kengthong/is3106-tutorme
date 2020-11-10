/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Person;
import exception.PersonLoginFailException;
import exception.PersonNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface PersonSessionLocal {

    public Person login(String email, String password) throws PersonLoginFailException;

    public Person retrievePersonById(Long personId) throws PersonNotFoundException;

    public List<Person> retrieveAllPersons();

    public List<Person> retrievePersonsByName(String inputName) throws PersonNotFoundException;

    public Person activatePerson(Long personId) throws PersonNotFoundException;

    public Person deactivatePerson(Long personId) throws PersonNotFoundException;
}
