/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Tutor;
import enumeration.CitizenshipEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import exception.RegistrationFailException;
import exception.TutorNotFoundException;
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
public class TutorSession implements TutorSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;
    private final CryptoHelper ch = CryptoHelper.getInstance();

    @Override
    public Tutor createTutorInit(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc) {
        Tutor newTutor = new Tutor();
        try {
            String salt = ch.generateRandomString(64);
            newTutor.setSalt(salt);
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            newTutor.setPassword(hashedPassword);

            newTutor.setFirstName(firstName);
            newTutor.setLastName(lastName);
            newTutor.setEmail(email.toLowerCase());
            newTutor.setMobileNum(mobileNum);
            newTutor.setGender(gender);
            newTutor.setDob(dob);
            newTutor.setHighestQualification(highestQualification);
            newTutor.setCitizenship(citizenship);
            newTutor.setRace(race);
            newTutor.setProfileDesc(profileDesc);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Hashing error when creating tutor.");
        }
        em.persist(newTutor);
        return newTutor;
    }

    @Override
    public Tutor createTutor(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob) throws RegistrationFailException {
        Tutor newTutor = new Tutor();
        try {
            String salt = ch.generateRandomString(64);
            newTutor.setSalt(salt);
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            newTutor.setPassword(hashedPassword);

            newTutor.setFirstName(firstName);
            newTutor.setLastName(lastName);
            newTutor.setEmail(email.toLowerCase());
            newTutor.setMobileNum(mobileNum);
            newTutor.setGender(gender);
            newTutor.setDob(dob);
            try {
                em.persist(newTutor);
                em.flush();
            } catch (PersistenceException ex) {
                throw new RegistrationFailException("Email is in use");
            }
            return newTutor;
        } catch (NoSuchAlgorithmException ex) {
            throw new RegistrationFailException("Something when wrong in creating new user's password salt");
        }
    }

    @Override
    public List<Tutor> retrieveAllTutors() {
        System.out.println("Retrieving all tutors from tutorSession...");
        Query query = em.createQuery("SELECT t FROM Tutor t");
        List<Tutor> tutors = query.getResultList();
        return tutors;
    }

    @Override
    public Tutor retrieveTutorById(Long tutorId) throws TutorNotFoundException {
        Tutor tutor = em.find(Tutor.class, tutorId);
        if (tutor != null) {
            return tutor;
        } else {
            throw new TutorNotFoundException("TutorID " + tutorId + " does not exists.");
        }
    }

    @Override
    public Tutor retrieveTutorByEmail(String email) throws TutorNotFoundException {
        Query query = em.createQuery("SELECT t FROM Tutor t WHERE t.email = :inputEmail");
        query.setParameter("inputEmail", email.toLowerCase());
        Tutor tutor = (Tutor) query.getSingleResult();
        if (tutor != null) {
            return tutor;
        } else {
            throw new TutorNotFoundException("Tutor with email " + email + " does not exists.");
        }
    }

    @Override
    public List<Tutor> retrieveTutorsByName(String inputName) {
        Query query = em.createQuery("SELECT t FROM Tutor t WHERE t.firstName LIKE :inputName OR t.lastName LIKE :inputName");
        query.setParameter("inputName", inputName);
        List<Tutor> results = query.getResultList();
        return results;
    }

    @Override
    public Tutor updateTutorProfile(Long tutorId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob, QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc) throws TutorNotFoundException {
        Tutor tutor = em.find(Tutor.class, tutorId);
        if (tutor == null) {
            throw new TutorNotFoundException("TutorID " + tutorId + " does not exists.");
        } else {
            tutor.setFirstName(firstName);
            tutor.setLastName(lastName);
            tutor.setMobileNum(mobileNum);
            tutor.setGender(gender);
            tutor.setDob(dob);
            tutor.setHighestQualification(highestQualification);
            tutor.setCitizenship(citizenship);
            tutor.setRace(race);
            tutor.setProfileDesc(profileDesc);
            return tutor;
        }
    }

    @Override
    public Integer getActiveTutors() {
        Query query = em.createQuery("SELECT t from Tutor t WHERE t.activeStatus=true");
        List<Tutor> tutors = query.getResultList();
        return tutors.size();
    }

    @Override
    public Integer getTutorGrowth() {
        Query query1 = em.createQuery("SELECT t FROM Tutor t WHERE t.createdDate < :inputDate");
        Query query2 = em.createQuery("SELECT t FROM Tutor t WHERE t.createdDate >= :inputDate");

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        query1.setParameter("inputDate", c.getTime());
        List<Tutor> tutors1 = query1.getResultList();

        query2.setParameter("inputDate", c.getTime());
        List<Tutor> tutors2 = query2.getResultList();

        Integer t1 = tutors1.size();
        Integer t2 = tutors2.size();
        return t2 - t1;
    }

}
