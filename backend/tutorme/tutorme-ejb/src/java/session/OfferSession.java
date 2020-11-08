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
import java.util.ArrayList;
import java.util.Calendar;
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
        Offer offer = em.find(Offer.class, offerId);
        if (offer != null) {
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
        } else {
            throw new OfferNotFoundException("OfferID " + offerId + " does not exists.");
        }
    }

    @Override
    public Offer rejectOffer(Long offerId) throws OfferNotFoundException, OfferStatusException {
        Offer offer = em.find(Offer.class, offerId);
        if (offer != null) {
            OfferStatusEnum offerStatus = offer.getOfferStatus();
            switch (offerStatus) {
                case PENDING:
                    System.out.println("OfferID " + offerId + " has been successfully rejected.");
                    offer.setOfferStatus(OfferStatusEnum.REJECTED);
                    break;
                case WITHDRAWN:
                    throw new OfferStatusException("OfferID " + offerId + " was withdrawn previously.");
                case ACCEPTED:
                    throw new OfferStatusException("OfferID " + offerId + " has already been accepted.");
                case REJECTED:
                    throw new OfferStatusException("OfferID " + offerId + " has already been rejected.");
            }
            return offer;
        } else {
            throw new OfferNotFoundException("OfferID " + offerId + " does not exists.");
        }
    }

    @Override
    public Offer withdrawOffer(Long offerId) throws OfferNotFoundException, OfferStatusException {
        Offer offer = em.find(Offer.class, offerId);
        if (offer != null) {
            OfferStatusEnum offerStatus = offer.getOfferStatus();
            switch (offerStatus) {
                case PENDING:
                    System.out.println("OfferID " + offerId + " has been successfully rejected.");
                    offer.setOfferStatus(OfferStatusEnum.WITHDRAWN);
                    break;
                case WITHDRAWN:
                    throw new OfferStatusException("OfferID " + offerId + " was withdrawn previously.");
                case ACCEPTED:
                    throw new OfferStatusException("OfferID " + offerId + " has already been accepted.");
                case REJECTED:
                    throw new OfferStatusException("OfferID " + offerId + " has already been rejected.");
            }
            return offer;
        } else {
            throw new OfferNotFoundException("OfferID " + offerId + " does not exists.");
        }
    }

    @Override
    public void deleteOffer(Long offerId) throws OfferNotFoundException {
        Offer offer = retrieveOfferById(offerId);
        em.remove(offer);
    }

    @Override
    public Integer getActiveOffers() {
        Query query = em.createQuery("SELECT o from Offer o");
        List<Offer> offers = query.getResultList();
        return offers.size();
    }

    @Override
    public Integer getOfferGrowth() {
        Query query1 = em.createQuery("SELECT o FROM Offer o WHERE o.createdDate < :inputDate");
        Query query2 = em.createQuery("SELECT o FROM Offer o WHERE o.createdDate >= :inputDate");

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        query1.setParameter("inputDate", c.getTime());
        List<Offer> offers1 = query1.getResultList();

        query2.setParameter("inputDate", c.getTime());
        List<Offer> offers2 = query2.getResultList();

        Integer o1 = offers1.size();
        Integer o2 = offers2.size();
        return o2 - o1;
    }

    @Override
    public Double getOfferAcceptanceRate() {
        Query query1 = em.createQuery("SELECT o FROM Offer o WHERE o.createdDate >= :inputDate");
        List<Offer> allOffers = new ArrayList<>();
        List<Offer> acceptedOffers = new ArrayList<>();

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        query1.setParameter("inputDate", c.getTime());
        allOffers = query1.getResultList();

        acceptedOffers = allOffers.stream()
                .filter(o -> o.getOfferStatus().equals(OfferStatusEnum.ACCEPTED))
                .collect(Collectors.toList());

        Double o1 = Double.valueOf(allOffers.size());
        Double o2 = Double.valueOf(acceptedOffers.size());
        if (o1 == 0.0) {
            return (o2 / 1) * 100;
        } else {
            return (o2 / o1) * 100;
        }
    }

    @Override
    public Double getOfferRejectionRate() {
        Query query1 = em.createQuery("SELECT o FROM Offer o WHERE o.createdDate >= :inputDate");
        List<Offer> allOffers = new ArrayList<>();
        List<Offer> rejectedOffers = new ArrayList<>();

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        query1.setParameter("inputDate", c.getTime());
        allOffers = query1.getResultList();

        rejectedOffers = allOffers.stream()
                .filter(o -> o.getOfferStatus().equals(OfferStatusEnum.REJECTED))
                .collect(Collectors.toList());

        Double o1 = Double.valueOf(allOffers.size());
        Double o2 = Double.valueOf(rejectedOffers.size());
        if (o1 == 0.0) {
            return (o2 / 1) * 100;
        } else {
            return (o2 / o1) * 100;
        }
    }

}
