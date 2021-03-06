/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Offer;
import entity.Rating;
import enumeration.OfferStatusEnum;
import exception.OfferNotFoundException;
import exception.OfferRatingExistException;
import exception.OfferStatusException;
import exception.RatingNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class RatingSession implements RatingSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    EntityManager em;

    @Override
    public Rating createRatingInit(Integer ratingValue, String comments, Long offerId) throws OfferNotFoundException, OfferStatusException {
        Offer offer = em.find(Offer.class, offerId);
        if (offer == null) {
            throw new OfferNotFoundException("offerId does not exist");
        } else {
            offer.setOfferStatus(OfferStatusEnum.ACCEPTED);
            Rating newRating = new Rating(ratingValue, comments, offer);
            em.persist(newRating);
            offer.setRating(newRating);
            return newRating;
        }
    }

    @Override
    public Rating createRating(Integer ratingValue, String comments, Long offerId) throws OfferNotFoundException, OfferStatusException, OfferRatingExistException {
        Offer offer = em.find(Offer.class, offerId);
        if (offer == null) {
            throw new OfferNotFoundException("offerId does not exist");
        } else {
            if (offer.getOfferStatus() == OfferStatusEnum.ACCEPTED) {
                System.out.println("%%%% Offer was accepted, processing rating");
                Rating newRating = new Rating(ratingValue, comments, offer);
                try {
                    em.persist(newRating);
                    em.flush();
                } catch (PersistenceException ex) {
                    throw new OfferRatingExistException("Offer has been rated previously");
                }
                offer.setRating(newRating);
                return newRating;
            } else {
                System.out.println("%%%% Offer status is not accepted, rating aborted");
                throw new OfferStatusException("Offer has not been accepted.");
            }
        }
    }

    @Override
    public List<Rating> retrieveAllRatings() {
        Query query = em.createQuery("SELECT r FROM Rating r");
        List<Rating> results = query.getResultList();
        return results;
    }

    @Override
    public Rating retrieveRatingById(Long ratingId) throws RatingNotFoundException {
        Rating rating = em.find(Rating.class,
                 ratingId);
        if (rating != null) {
            return rating;
        } else {
            throw new RatingNotFoundException("RatingID " + ratingId + " does not exists.");
        }
    }

    @Override
    public Rating retrieveRatingByOfferId(Long offerId) {
        Query query = em.createQuery("SELECT r FROM Rating r WHERE r.offer.offerId = :inputOfferId");
        query.setParameter("inputOfferId", offerId);
        return (Rating) query.getSingleResult();
    }

    @Override
    public List<Rating> retrieveRatingsByTutorId(Long tutorId) {
        Query query = em.createQuery("SELECT r FROM Tutor t JOIN JobListing jl JOIN Offer o JOIN Rating r WHERE t.personId = :inputTutorId");
        query.setParameter("inputTutorId", tutorId);
        List<Rating> ratings = query.getResultList();
        return ratings;
    }

    @Override
    public List<Rating> retrieveRatingsByJobListingId(Long jobListingId) {
        Query query = em.createQuery("SELECT r JobListing jl JOIN Offer o JOIN Rating r WHERE t.personId = :inputJobListingId");
        query.setParameter("inputJobListingId", jobListingId);
        List<Rating> ratings = query.getResultList();
        return ratings;
    }

    @Override
    public void deleteRating(Long ratingId) throws RatingNotFoundException {
        Rating rating = retrieveRatingById(ratingId);
        em.remove(rating);
    }
}
