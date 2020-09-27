/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.JobListing;
import entity.Offer;
import entity.Rating;
import entity.Tutor;
import enumeration.CitizenshipEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import exception.JobListingNotFoundException;
import exception.OfferNotFoundException;
import exception.RatingNotFoundException;
import exception.TutorNotFoundException;
import exception.PersonLoginFailException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

    @EJB
    RatingSessionLocal ratingSession;
    @EJB
    OfferSessionLocal offerSession;
    @EJB
    JobListingSessionLocal jobListingSession;
    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;
    private final CryptoHelper ch = CryptoHelper.getInstance();

    @Override
    public Tutor loginTutor(String email, String password) throws PersonLoginFailException {
        try {
            Tutor tutor = retrieveTutorByEmail(email);
            String storedPassword = tutor.getPassword();
            String salt = tutor.getSalt();
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            if (storedPassword.equals(hashedPassword)) {
                tutor.setSalt(null);
                tutor.getJobListings();
                tutor.getMessages();
                return tutor;
            } else {
                throw new PersonLoginFailException("Invalid login parameters.");
            }
        } catch (TutorNotFoundException ex) {
            throw new PersonLoginFailException("Invalid login parameters.");
        }
    }

    @Override
    public Tutor createTutor(Tutor newTutor) {
        em.persist(newTutor);
        return newTutor;
    }

    @Override
    public Tutor createTutor(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc) {
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
            newTutor.setHighestQualification(highestQualification);
            newTutor.setCitizenship(citizenship);
            newTutor.setRace(race);
            newTutor.setProfileDesc(profileDesc);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TutorSession.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return createTutor(newTutor);

    }

    @Override // in use
    public List<Tutor> retrieveAllTutors() {
        Query query = em.createQuery("SELECT t FROM Tutor t");
        List<Tutor> tutors = query.getResultList();
//        for (Tutor t : tutors) {
//            t.getMessages();
//            t.getJobListings();
//        }
        return tutors;
    }

    @Override 
    public Tutor retrieveTutorByIdDetach(Long personId) throws TutorNotFoundException {
        Tutor tutor = em.find(Tutor.class, personId);
        if (tutor != null) {
            List<Rating> tutorRatings = ratingSession.retrieveRatingsByTutorId(personId);
//            em.detach(tutor);
            OptionalDouble avgRating = tutorRatings.stream()
                    .mapToDouble(r -> r.getRatingValue())
                    .average();
            if (avgRating.isPresent()) {
                tutor.setAvgRating(avgRating.getAsDouble());
            } else {
                tutor.setAvgRating(0.0);
            }
            return tutor;
        } else {
            throw new TutorNotFoundException("TutorID " + personId + " does not exists.");
        }
    }

    @Override // in use
    public Tutor retrieveTutorById(Long personId) throws TutorNotFoundException {
        Tutor tutor = em.find(Tutor.class, personId);
        if (tutor != null) {
            List<Rating> tutorRatings = ratingSession.retrieveRatingsByTutorId(personId);
            OptionalDouble avgRating = tutorRatings.stream()
                    .mapToDouble(r -> r.getRatingValue())
                    .average();
            if (avgRating.isPresent()) {
                tutor.setAvgRating(avgRating.getAsDouble());
            } else {
                tutor.setAvgRating(0.0);
            }
            return tutor;
        } else {
            throw new TutorNotFoundException("TutorID " + personId + " does not exists.");
        }
    }

    @Override
    public Tutor retrieveTutorByEmail(String email) throws TutorNotFoundException {
        Query query = em.createQuery("SELECT t FROM Tutor t WHERE t.email = :inputEmail");
        query.setParameter("inputEmail", email);
        Tutor tutor = (Tutor) query.getSingleResult();
        if (tutor != null) {
            tutor.getMessages();
            tutor.getJobListings();
            return tutor;
        } else {
            throw new TutorNotFoundException("Tutor with email " + email + " does not exists.");
        }
    }

    @Override
    public List<Tutor> retrieveTutorsByName(String inputName) throws TutorNotFoundException {
        Query query = em.createQuery("SELECT t FROM Tutor t WHERE t.firstName LIKE :inputName OR t.lastName LIKE :inputName");
        query.setParameter("inputName", inputName);
        List<Tutor> results = query.getResultList();
        if (!results.isEmpty()) { // if empty then return message in REST
            for (Tutor t : results) {
                t.getMessages();
                t.getJobListings();
            }
            return results;
        } else {
            throw new TutorNotFoundException("No tutor by the name " + inputName + " was found.");
        }
    }

    @Override
    public Tutor retrieveTutorByJobListing(Long jobListingId) throws TutorNotFoundException {
        JobListing jobListing = null;
        try {
            jobListing = jobListingSession.retrieveJobListingById(jobListingId);
        } catch (JobListingNotFoundException ex) {
            throw new TutorNotFoundException("No tutor found because JobListingID " + jobListingId + " does not exists.");
        }
        Tutor tutor = jobListing.getTutor();
        tutor.getJobListings();
        tutor.getMessages();
        return tutor;
    }

    @Override
    public Tutor retrieveTutorByOffer(Long offerId) throws TutorNotFoundException {
        Offer offer = null;
        try {
            offer = offerSession.retrieveOfferById(offerId);
        } catch (OfferNotFoundException ex) {
            throw new TutorNotFoundException("No tutor found because OfferID " + offerId + " does not exists.");
        }
        Tutor tutor = offer.getJobListing().getTutor();
        tutor.getJobListings();
        tutor.getMessages();
        return tutor;
    }

    @Override
    public Tutor retrieveTutorByRating(Long ratingId) throws TutorNotFoundException {
        Rating rating = null;
        try {
            rating = ratingSession.retrieveRatingById(ratingId);
        } catch (RatingNotFoundException ex) {
            throw new TutorNotFoundException("No tutor found because RatingID " + ratingId + " does not exists.");
        }
        Tutor tutor = rating.getOffer().getJobListing().getTutor();
        tutor.getJobListings();
        tutor.getMessages();
        return tutor;
    }

    @Override
    public Tutor updateTutorProfile(Tutor updatedTutor) {
        return em.merge(updatedTutor);
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
        return updateTutorProfile(tutor);
    }

    @Override
    public Tutor deactivateTutor(Long personId) throws TutorNotFoundException {
        Tutor tutor = retrieveTutorById(personId);
        if (tutor.getActiveStatus()) {
            tutor.setActiveStatus(false);
        } else {
            tutor.setActiveStatus(true);
        }
        return updateTutorProfile(tutor);
    }

    @Override
    public void deleteTutor(Long personId) throws TutorNotFoundException {
        Tutor tutor = retrieveTutorById(personId);
        em.remove(tutor);
    }

}
