/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Tutee;
import enumeration.GenderEnum;
import exception.TuteeNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public Tutee createTuteeInit(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, String profileDesc, String profileImage) {
        Tutee newTutee = new Tutee();
        try {
            String salt = ch.generateRandomString(64);
            newTutee.setSalt(salt);
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            newTutee.setPassword(hashedPassword);

            newTutee.setFirstName(firstName);
            newTutee.setLastName(lastName);
            newTutee.setEmail(email);
            newTutee.setMobileNum(mobileNum);
            newTutee.setGender(gender);
            newTutee.setDob(dob);
            newTutee.setProfileDesc(profileDesc);
            newTutee.setProfileImage(profileImage);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Hashing error when creating tutee.");
        }
        em.persist(newTutee);
        return newTutee;
    }

    @Override
    public Tutee createTutee(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob) {
        Tutee newTutee = new Tutee();
        try {
            String salt = ch.generateRandomString(64);
            newTutee.setSalt(salt);
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            newTutee.setPassword(hashedPassword);

            newTutee.setFirstName(firstName);
            newTutee.setLastName(lastName);
            newTutee.setEmail(email);
            newTutee.setMobileNum(mobileNum);
            newTutee.setGender(gender);
            newTutee.setDob(dob);
            
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Hashing error when creating tutee.");
        }
        em.persist(newTutee);
        return newTutee;
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
        query.setParameter("inputEmail", email);
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
    public Tutee updateTuteeProfile(Long tuteeId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob, String profileDesc, String profileImage) throws TuteeNotFoundException {
        Tutee tutee = em.find(Tutee.class, tuteeId);
        if (tutee != null) {
            tutee.setFirstName(firstName);
            tutee.setLastName(lastName);
            tutee.setMobileNum(mobileNum);
            tutee.setGender(gender);
            tutee.setDob(dob);
            tutee.setProfileDesc(profileDesc);
            tutee.setProfileImage(profileImage);
            return tutee;
        } else {
            throw new TuteeNotFoundException("TuteeID " + tuteeId + " does not exists.");
        }
    }

    @Override
    public Tutee activateTuteeStatus(Long tuteeId) throws TuteeNotFoundException {
        Tutee tutee = em.find(Tutee.class, tuteeId);
        if (tutee != null && !tutee.getActiveStatus()) {
            tutee.setActiveStatus(true);
            return tutee;
        } else {
            throw new TuteeNotFoundException("TuteeID " + tuteeId + " does not exists.");
        }
    }

    @Override
    public Tutee deactivateTuteeStatus(Long tuteeId) throws TuteeNotFoundException {
        Tutee tutee = em.find(Tutee.class, tuteeId);
        if (tutee != null && tutee.getActiveStatus()) {
            tutee.setActiveStatus(false);
            return tutee;
        } else {
            throw new TuteeNotFoundException("TuteeID " + tuteeId + " does not exists.");
        }
    }

}
