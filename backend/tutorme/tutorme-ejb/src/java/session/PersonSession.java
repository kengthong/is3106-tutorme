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
    public Person login(String email, String password) throws PersonLoginFailException {
        Query query = em.createQuery("SELECT p from Person p WHERE p.email=:email");
        query.setParameter("email", email);
        try {
            Person person = (Person) query.getSingleResult();
            String hashedInputPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(person.getSalt())));
            if (person.getPassword().equals(hashedInputPassword)) {
                return person;
            } else {
                throw new PersonLoginFailException("Invalid login credentials.");
            }
        } catch (NoResultException ex) {
            throw new PersonLoginFailException("Invalid login credentials.");
        }
    }

    @Override
    public Person retrievePersonById(Long userId) throws PersonNotFoundException {
        Person user = em.find(Person.class, userId);
        if (user != null) {
            return user;
        } else {
            throw new PersonNotFoundException("Person ID " + userId + "does not exists.");
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

    
    // TODO
    @Override
    public Person changePassword(Long userId, String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changePersonActiveStatus(Long userId) {
        Person user;
        try {
            user = retrievePersonById(userId);
            if (user.getActiveStatus()) {
                user.setActiveStatus(false);
            } else {
                user.setActiveStatus(true);
            }
        } catch (PersonNotFoundException ex) {
            System.out.println("Person ID " + userId + "does not exists.");
        }
    }

}
