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
import exception.InvalidSubjectChoiceException;
import exception.OfferNotFoundException;
import exception.OfferWithdrawException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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
    public Offer createOffer(Offer newOffer) {
        em.persist(newOffer);
        em.flush();
        return newOffer;
    }

    @Override
    public Offer createOffer(Double offeredRate, Date startDate, Tutee tutee, Subject chosenSubject, JobListing jobListing, int numSessions, double numHoursPerSession, String additionalNote) throws InvalidSubjectChoiceException {
        if (jobListing.getSubjects().contains(chosenSubject)) {
            Offer newOffer = new Offer(offeredRate, startDate, tutee, chosenSubject, jobListing, numSessions, numHoursPerSession, additionalNote);
        return createOffer(newOffer);
        } else {
            throw new InvalidSubjectChoiceException();
        }
        
    }

    @Override
    public List<Offer> retrieveAllOffers() {
        Query query = em.createQuery("SELECT o FROM Offer o");
        List<Offer> results = query.getResultList();
        return results;
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
    public List<Offer> retrieveOffersByTuteeId(Long userId) {
        List<Offer> offers = retrieveAllOffers();
        List<Offer> filteredOffers = offers.stream()
                .filter(o -> o.getTutee().getPersonId().equals(userId))
                .collect(Collectors.toList());
        return filteredOffers;
    }

    @Override
    public List<Offer> retrieveOffersByJobListingId(Long jobListingId) {
        List<Offer> offers = retrieveAllOffers();
        List<Offer> filteredOffers = offers.stream()
                .filter(o -> o.getJobListing().getJobListingId().equals(jobListingId))
                .collect(Collectors.toList());
        return filteredOffers;
    }

    @Override
    public void updateOffer(Offer updatedOffer) {
        em.merge(updatedOffer);
    }

    @Override
    public void withdrawOffer(Long offerId) throws OfferNotFoundException, OfferWithdrawException {
        Offer offer = retrieveOfferById(offerId);
        OfferStatusEnum offerStatus = offer.getOfferStatus();
        switch (offerStatus) {
            case PENDING:
                System.out.println("OfferID " + offerId + " has been successfully withdrawn.");
                offer.setOfferStatus(OfferStatusEnum.WITHDRAWN);
                updateOffer(offer);
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
