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
import enumeration.OfferStatusEnum;
import exception.InvalidParamsException;
import exception.InvalidSubjectChoiceException;
import exception.OfferNotFoundException;
import exception.OfferStatusException;
import java.util.Date;
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
public class OfferSession implements OfferSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    EntityManager em;

    @Override
    public Offer createOffer(Double offeredRate, Date startDate, Long tuteeId, Long subjectId, Long jobListingId, int numSessions, double numHoursPerSession, String additionalNote) throws InvalidSubjectChoiceException, InvalidParamsException {
        Tutee tutee = em.find(Tutee.class, tuteeId);
        Subject subject = em.find(Subject.class, subjectId);
        JobListing jobListing = em.find(JobListing.class, jobListingId);
        if (tutee == null || subject == null || jobListing == null) {
            throw new InvalidParamsException("### OfferSession: Either tutee, subject, jobListing does not exists.");
        }
        if (jobListing.getSubjects().contains(subject)) {
            Offer newOffer = new Offer(offeredRate, startDate, tutee, subject, jobListing, numSessions, numHoursPerSession, additionalNote);
            em.persist(newOffer);
            List<Offer> tuteeOffers = tutee.getOffers();
            tuteeOffers.add(newOffer);

            List<Offer> jobListingOffers = jobListing.getOffers();
            jobListingOffers.add(newOffer);
            return newOffer;
        } else {
            throw new InvalidSubjectChoiceException();
        }
    }

    @Override
    public Offer retrieveOfferById(Long offerId) throws OfferNotFoundException {
        Offer offer = em.find(Offer.class, offerId);
        if (offer != null) {
            return offer;
        } else {
            throw new OfferNotFoundException("OfferID " + offerId + " does not exists.");
        }
    }

    @Override
    public List<Offer> retrieveAllOffers() {
        Query query = em.createQuery("SELECT o FROM Offer o");
        List<Offer> offers = query.getResultList();
        return offers;
    }

    @Override
    public List<Offer> retrieveOffersByPeriod(Date startDate, Date endDate) {
        System.out.println("Inside OfferSession startDate " + startDate + " endDate " + endDate);
        Query query = em.createQuery("SELECT o FROM Offer o WHERE o.createdDate >= :inputStartDate AND o.createdDate <= :inputEndDate");
        query.setParameter("inputStartDate", startDate);
        query.setParameter("inputEndDate", endDate);
        List<Offer> offers = query.getResultList();
        return offers;
    }

    @Override
    public List<Offer> retrieveOffersByTuteeId(Long tuteeId) {
        Query query = em.createQuery("SELECT o from Offer o WHERE o.tutee.personId = :inputTuteeId");
        query.setParameter("inputTuteeId", tuteeId);
        return query.getResultList();
    }

    @Override
    public List<Offer> retrieveOffersByJobListingId(Long jobListingId) {
        Query query = em.createQuery("SELECT o from Offer o WHERE o.jobListing.jobListingId = :inputJobListingId");
        query.setParameter("inputJobListingId", jobListingId);
        return query.getResultList();
    }

    @Override
    public Offer acceptOffer(Long offerId) throws OfferNotFoundException, OfferStatusException {
        Offer offer = retrieveOfferById(offerId);
        OfferStatusEnum offerStatus = offer.getOfferStatus();
        switch (offerStatus) {
            case PENDING:
                System.out.println("OfferID " + offerId + " has been successfully accepted.");
                offer.setOfferStatus(OfferStatusEnum.ACCEPTED);
                break;
            case WITHDRAWN:
                throw new OfferStatusException("OfferID " + offerId + " was withdrawn previously.");
            case ACCEPTED:
                throw new OfferStatusException("OfferID " + offerId + " has already been accepted.");
            case REJECTED:
                throw new OfferStatusException("OfferID " + offerId + " has already been rejected.");
        }
        return offer;
    }

    @Override
    public Offer withdrawOffer(Long offerId) throws OfferNotFoundException, OfferStatusException {
        Offer offer = retrieveOfferById(offerId);
        OfferStatusEnum offerStatus = offer.getOfferStatus();
        switch (offerStatus) {
            case PENDING:
                System.out.println("OfferID " + offerId + " has been successfully withdrawn.");
                offer.setOfferStatus(OfferStatusEnum.WITHDRAWN);
                break;
            case WITHDRAWN:
                throw new OfferStatusException("OfferID " + offerId + " was withdrawn previously.");
            case ACCEPTED:
                throw new OfferStatusException("OfferID " + offerId + " was accepted already.");
            case REJECTED:
                throw new OfferStatusException("OfferID " + offerId + " has already been rejected.");
        }
        return offer;
    }

    @Override
    public void deleteOffer(Long offerId) throws OfferNotFoundException {
        Offer offer = retrieveOfferById(offerId);
        em.remove(offer);
    }

}
