/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Tutee;
import enumeration.GenderEnum;
import exception.RegistrationFailException;
import exception.TuteeNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.security.CryptoHelper;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class TuteeSession implements TuteeSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;
    private final CryptoHelper ch = CryptoHelper.getInstance();

    @Override
    public Tutee createTuteeInit(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, String profileDesc) {
        Tutee newTutee = new Tutee();
        try {
            String salt = ch.generateRandomString(64);
            newTutee.setSalt(salt);
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            newTutee.setPassword(hashedPassword);

            newTutee.setFirstName(firstName);
            newTutee.setLastName(lastName);
            newTutee.setEmail(email.toLowerCase());
            newTutee.setMobileNum(mobileNum);
            newTutee.setGender(gender);
            newTutee.setDob(dob);
            newTutee.setProfileDesc(profileDesc);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Hashing error when creating tutee.");
        }
        em.persist(newTutee);
        return newTutee;
    }

    @Override
    public Tutee createTutee(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob) throws RegistrationFailException {
        Tutee newTutee = new Tutee();
        try {
            String salt = ch.generateRandomString(64);
            newTutee.setSalt(salt);
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            newTutee.setPassword(hashedPassword);

            newTutee.setFirstName(firstName);
            newTutee.setLastName(lastName);
            newTutee.setEmail(email.toLowerCase());
            newTutee.setMobileNum(mobileNum);
            newTutee.setGender(gender);
            newTutee.setDob(dob);
            try {
                em.persist(newTutee);
                em.flush();
                return newTutee;
            } catch (PersistenceException ex) {
                throw new RegistrationFailException("Email is in use");
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new RegistrationFailException("Something when wrong in creating new user's password salt");
        }
    }

    @Override
    public List<Tutee> retrieveAllTutees() {
        Query query = em.createQuery("SELECT t FROM Tutee t");
        return query.getResultList();
    }

    @Override
    public Tutee retrieveTuteeById(Long tuteeId) throws TuteeNotFoundException {
        Tutee tutee = em.find(Tutee.class, tuteeId);
        if (tutee != null) {
            return tutee;
        } else {
            throw new TuteeNotFoundException("TuteeID " + tuteeId + " does not exists.");
        }
    }

    @Override
    public Tutee retrieveTuteeByEmail(String email) throws TuteeNotFoundException {
        Query query = em.createQuery("SELECT t FROM Tutee t WHERE t.email = :inputEmail");
        query.setParameter("inputEmail", email.toLowerCase());
        Tutee tutee = (Tutee) query.getSingleResult();
        if (tutee != null) {
            return tutee;
        } else {
            throw new TuteeNotFoundException("Tutee with email " + email + " does not exists.");
        }
    }

    @Override
    public List<Tutee> retrieveTuteeByName(String inputName) {
        Query query = em.createQuery("SELECT t FROM Tutee t WHERE t.firstName LIKE :inputName OR t.lastName LIKE :inputName");
        query.setParameter("inputName", inputName);
        List<Tutee> results = query.getResultList();
        return results;
    }

    @Override
    public Tutee updateTuteeProfile(Long tuteeId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob, String profileDesc) throws TuteeNotFoundException {
        Tutee tutee = em.find(Tutee.class, tuteeId);
        if (tutee != null) {
            tutee.setFirstName(firstName);
            tutee.setLastName(lastName);
            tutee.setMobileNum(mobileNum);
            tutee.setGender(gender);
            tutee.setDob(dob);
            tutee.setProfileDesc(profileDesc);
            return tutee;
        } else {
            throw new TuteeNotFoundException("TuteeID " + tuteeId + " does not exists.");
        }
    }

    @Override
    public Integer getActiveTutees() {
        Query query = em.createQuery("SELECT t from Tutee t WHERE t.activeStatus=true");
        List<Tutee> tutees = query.getResultList();
        return tutees.size();
    }

    @Override
    public Integer getTuteeGrowth() {
        Query query1 = em.createQuery("SELECT t FROM Tutee t WHERE t.createdDate < :inputDate");
        Query query2 = em.createQuery("SELECT t FROM Tutee t WHERE t.createdDate >= :inputDate");

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        query1.setParameter("inputDate", c.getTime());
        List<Tutee> tutees1 = query1.getResultList();

        query2.setParameter("inputDate", c.getTime());
        List<Tutee> tutees2 = query2.getResultList();

        Integer t1 = tutees1.size();
        Integer t2 = tutees2.size();
        return t2 - t1;
    }

}
