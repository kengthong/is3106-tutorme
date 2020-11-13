/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Person;
import exception.BannedPersonException;
import exception.PersonLoginFailException;
import exception.PersonNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.security.CryptoHelper;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class PersonSession implements PersonSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;
    private final CryptoHelper ch = CryptoHelper.getInstance();

    @Override
    public Person login(String email, String password) throws PersonLoginFailException, BannedPersonException {
        Query query = em.createQuery("SELECT p from Person p WHERE p.email=:email");
        query.setParameter("email", email.toLowerCase());
        try {
            Person person = (Person) query.getSingleResult();
            String hashedInputPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(person.getSalt())));
            if (person.getPassword().equals(hashedInputPassword)) {
                if (person.getActiveStatus()) {
                    return person;
                } else {
                    throw new BannedPersonException ("User was banned.");
                }
            } else {
                throw new PersonLoginFailException("Invalid login credentials.");
            }
        } catch (NoResultException ex) {
            throw new PersonLoginFailException("Invalid login credentials.");
        }
    }

    @Override
    public Person retrievePersonById(Long personId) throws PersonNotFoundException {
        Person user = em.find(Person.class, personId);
        if (user != null) {
            return user;
        } else {
            throw new PersonNotFoundException("Person ID " + personId + "does not exists.");
        }
    }

    @Override
    public List<Person> retrieveAllPersons() {
        Query query = em.createQuery("SELECT p from Person p");
        List<Person> results = query.getResultList();
        return results;
    }

    @Override
    public List<Person> retrievePersonsByName(String inputName) throws PersonNotFoundException {
        Query query = em.createQuery("SELECT u FROM Person u WHERE u.firstName LIKE :inputName OR u.lastName LIKE :inputName");
        query.setParameter("inputName", inputName);
        List<Person> results = query.getResultList();
        if (!results.isEmpty()) {
            return results;
        } else {
            throw new PersonNotFoundException("No Persons by the name " + inputName + " was found.");
        }
    }

    @Override
    public Person activatePerson(Long personId) throws PersonNotFoundException {
        Person person = em.find(Person.class, personId);
        if (person == null) {
            throw new PersonNotFoundException("PersonId " + personId + " does not exists.");
        } else {
            person.setActiveStatus(true);
            return person;
        }
    }

    @Override
    public Person deactivatePerson(Long personId) throws PersonNotFoundException {
        Person person = em.find(Person.class, personId);
        if (person == null) {
            throw new PersonNotFoundException("PersonId " + personId + " does not exists.");
        } else {
            person.setActiveStatus(false);
            return person;
        }
    }
}
