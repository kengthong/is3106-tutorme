/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.JobListing;
import entity.Tutor;
import enumeration.CitizenshipEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import exception.TutorNotFoundException;
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
public class TutorSession implements TutorSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;
    private final CryptoHelper ch = CryptoHelper.getInstance();

    @Override
    public Tutor createTutor(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob) {
        Tutor newTutor = new Tutor();
        try {
            String salt = ch.generateRandomString(64);
            newTutor.setSalt(salt);
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            newTutor.setPassword(hashedPassword);

            newTutor.setFirstName(firstName);
            newTutor.setLastName(lastName);
            newTutor.setEmail(email);
            newTutor.setMobileNum(mobileNum);
            newTutor.setGender(gender);
            newTutor.setDob(dob);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Hashing error when creating tutor.");
        }
        em.persist(newTutor);
        return newTutor;

    }

    @Override
    public List<Tutor> retrieveAllTutors() {
        System.out.println("Retrieving all tutors from tutorSession...");
        Query query = em.createQuery("SELECT t FROM Tutor t");
        List<Tutor> tutors = query.getResultList();
        for (Tutor t : tutors) {
            if (t.getJobListings().isEmpty()) {
                System.out.println("### empty jobListings");
            }
            for (JobListing jl : t.getJobListings()) {
                System.out.println("### JobListingId:" + jl.getJobListingId());
            }
        }
        return tutors;
    }

    @Override
    public Tutor retrieveTutorById(Long personId) throws TutorNotFoundException {
        Tutor tutor = em.find(Tutor.class, personId);
        if (tutor.getJobListings() == null ) {
            System.out.println("### tutor does not have joblisting");
        }
        if (tutor == null) {
            throw new TutorNotFoundException("TutorID " + personId + " does not exists.");
        } else {
            return tutor;
        }
    }

    @Override
    public Tutor retrieveTutorByEmail(String email) throws TutorNotFoundException {
        Query query = em.createQuery("SELECT t FROM Tutor t WHERE t.email = :inputEmail");
        query.setParameter("inputEmail", email);
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
    public Tutor updateTutorProfile(Long personId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob, QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc) throws TutorNotFoundException {
        Tutor tutor = retrieveTutorById(personId);
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

    @Override
    public Tutor deactivateTutor(Long personId) throws TutorNotFoundException {
        Tutor tutor = retrieveTutorById(personId);
        if (tutor.getActiveStatus()) {
            tutor.setActiveStatus(false);
        } else {
            tutor.setActiveStatus(true);
        }
        return tutor;
    }

    @Override
    public void deleteTutor(Long personId) throws TutorNotFoundException {
        Tutor tutor = retrieveTutorById(personId);
        em.remove(tutor);
    }

}
