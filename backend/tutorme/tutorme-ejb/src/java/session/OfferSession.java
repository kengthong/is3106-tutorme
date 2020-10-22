/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.JobListing;
import entity.Offer;
import entity.Subject;
import entity.Tutee;
import entity.Tutor;
import enumeration.OfferStatusEnum;
import exception.InvalidParamsException;
import exception.InvalidSubjectChoiceException;
import exception.JobListingNotFoundException;
import exception.OfferNotFoundException;
import exception.OfferWithdrawException;
import exception.SubjectNotFoundException;
import exception.TuteeNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class OfferSession implements OfferSessionLocal {

    @EJB
    TuteeSessionLocal tuteeSession;
    @EJB
    SubjectSessionLocal subjectSession;
    @EJB
    JobListingSessionLocal jobListingSession;
    @PersistenceContext(unitName = "tutorme-ejbPU")
    EntityManager em;

    @Override
    public Offer createOffer(Offer newOffer) {
        em.persist(newOffer);
        return newOffer;
    }

    @Override
    public Offer createOffer(Double offeredRate, Date startDate, Long tuteeId, Long subjectId, Long jobListingId, int numSessions, double numHoursPerSession, String additionalNote) throws InvalidSubjectChoiceException, InvalidParamsException {
        try {
            Tutee tutee = tuteeSession.retrieveTuteeById(tuteeId);
            Subject subject = subjectSession.retrieveSubjectById(subjectId);
            JobListing jobListing = jobListingSession.retrieveJobListingById(jobListingId);
            if (jobListing.getSubjects().contains(subject)) {
                Offer newOffer = new Offer(offeredRate, startDate, tutee, subject, jobListing, numSessions, numHoursPerSession, additionalNote);
                em.persist(newOffer);
                
                List<Offer> tuteeOffers = tutee.getOffers();
                tuteeOffers.add(newOffer);
                tutee.setOffers(tuteeOffers);
                em.merge(tutee);
                
                List<Offer> jobListingOffers = jobListing.getOffers();
                jobListingOffers.add(newOffer);
                jobListing.setOffers(jobListingOffers);
                em.merge(jobListing);
                return newOffer;
            } else {
                throw new InvalidSubjectChoiceException();
            }
        } catch (JobListingNotFoundException | SubjectNotFoundException | TuteeNotFoundException ex) {
            throw new InvalidParamsException();
        }
    }

    @Override
    public List<Offer> retrieveAllOffers() {
        Query query = em.createQuery("SELECT o FROM Offer o");
        List<Offer> offers = query.getResultList();
        for (Offer o : offers) {
            em.detach(o);

            JobListing jobListing = o.getJobListing();
            em.detach(jobListing);
            jobListing.setOffers(null);

            Tutor tutor = jobListing.getTutor();
            em.detach(tutor);
            tutor.setJobListings(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            tutor.setSalt(null);
            tutor.setPassword(null);

            Tutee tutee = o.getTutee();
            em.detach(tutee);
            tutee.setOffers(null);
            tutee.setSentMessages(null);
            tutee.setReceivedMessages(null);
            tutee.setSalt(null);
            tutee.setPassword(null);
        }
        return offers;
    }

    @Override
    public Offer retrieveOfferById(Long offerId) throws OfferNotFoundException {
        Offer offer = em.find(Offer.class, offerId);
        if (offer != null) {
            JobListing jobListing = offer.getJobListing();
            em.detach(jobListing);
            jobListing.setOffers(null);

            Tutor tutor = jobListing.getTutor();
            em.detach(tutor);
            tutor.setJobListings(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            tutor.setSalt(null);
            tutor.setPassword(null);

            Tutee tutee = offer.getTutee();
            em.detach(tutee);
            tutee.setOffers(null);
            tutee.setSentMessages(null);
            tutee.setReceivedMessages(null);
            tutee.setSalt(null);
            tutee.setPassword(null);
            return offer;
        } else {
            throw new OfferNotFoundException("OfferID " + offerId + " does not exists.");
        }
    }

    @Override
    public List<Offer> retrieveOffersByTuteeId(Long userId) {
        List<Offer> offers = retrieveAllOffers();
        List<Offer> filteredOffers = offers.stream()
                .filter(o -> o.getTutee().getPersonId().equals(userId))
                .collect(Collectors.toList());
        for (Offer o : filteredOffers) {
            em.detach(o);

            JobListing jobListing = o.getJobListing();
            em.detach(jobListing);
            jobListing.setOffers(null);

            Tutor tutor = jobListing.getTutor();
            em.detach(tutor);
            tutor.setJobListings(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            tutor.setSalt(null);
            tutor.setPassword(null);

            Tutee tutee = o.getTutee();
            em.detach(tutee);
            tutee.setOffers(null);
            tutee.setSentMessages(null);
            tutee.setReceivedMessages(null);
            tutee.setSalt(null);
            tutee.setPassword(null);
        }
        return filteredOffers;
    }

    @Override
    public List<Offer> retrieveOffersByJobListingId(Long jobListingId) {
        List<Offer> offers = retrieveAllOffers();
        List<Offer> filteredOffers = offers.stream()
                .filter(o -> o.getJobListing().getJobListingId().equals(jobListingId))
                .collect(Collectors.toList());
        for (Offer o : filteredOffers) {
            em.detach(o);

            JobListing jobListing = o.getJobListing();
            em.detach(jobListing);
            jobListing.setOffers(null);

            Tutor tutor = jobListing.getTutor();
            em.detach(tutor);
            tutor.setJobListings(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            tutor.setSalt(null);
            tutor.setPassword(null);

            Tutee tutee = o.getTutee();
            em.detach(tutee);
            tutee.setOffers(null);
            tutee.setSentMessages(null);
            tutee.setReceivedMessages(null);
            tutee.setSalt(null);
            tutee.setPassword(null);
        }
        return filteredOffers;
    }

    @Override
    public void withdrawOffer(Long offerId) throws OfferNotFoundException, OfferWithdrawException {
        Offer offer = retrieveOfferById(offerId);
        OfferStatusEnum offerStatus = offer.getOfferStatus();
        switch (offerStatus) {
            case PENDING:
                System.out.println("OfferID " + offerId + " has been successfully withdrawn.");
                offer.setOfferStatus(OfferStatusEnum.WITHDRAWN);
                em.merge(offer);
                break;
            case WITHDRAWN:
                throw new OfferWithdrawException("OfferID " + offerId + " was withdrawn previously.");
            case ACCEPTED:
                throw new OfferWithdrawException("OfferID " + offerId + " has already been accepted and cannot be withdrawn.");
            case REJECTED:
                throw new OfferWithdrawException("OfferID " + offerId + " has already been rejected.");
        }
    }

    @Override
    public void deleteOffer(Long offerId) throws OfferNotFoundException {
        Offer offer = retrieveOfferById(offerId);
        em.remove(offer);
    }

}
