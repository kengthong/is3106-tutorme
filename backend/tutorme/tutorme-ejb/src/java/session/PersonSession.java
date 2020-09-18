/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Person;
import exception.PersonNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class PersonSession implements PersonSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;

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
            em.merge(user);
        } catch (PersonNotFoundException ex) {
            System.out.println("Person ID " + userId + "does not exists.");
        }
    }
}
