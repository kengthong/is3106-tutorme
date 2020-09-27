/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Offer;
import entity.Rating;
import exception.RatingNotFoundException;
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
public class RatingSession implements RatingSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    EntityManager em;

    @Override
    public Rating createRating(Rating newRating) {
        em.persist(newRating);
        return newRating;
    }

    @Override
    public Rating createRating(Double ratingValue, String comments, Offer offer) {
        Rating newRating = new Rating(ratingValue, comments, offer);
        return createRating(newRating);
    }

    @Override
    public List<Rating> retrieveAllRatings() {
        Query query = em.createQuery("SELECT r FROM Rating r");
        List<Rating> results = query.getResultList();
        return results;
    }

    @Override
    public Rating retrieveRatingById(Long ratingId) throws RatingNotFoundException {
        Rating rating = em.find(Rating.class, ratingId);
        if (rating != null) {
            return rating;
        } else {
            throw new RatingNotFoundException("RatingID " + ratingId + " does not exists.");
        }
    }

    @Override // should this return be List<Rating>
    public Rating retrieveRatingByOfferId(Long offerId) {
        List<Rating> ratings = retrieveAllRatings();
        List<Rating> filteredRatings = ratings.stream()
                .filter(r -> r.getOffer().getOfferId().equals(offerId))
                .collect(Collectors.toList());
        return filteredRatings.get(0);
    }

    @Override
    public List<Rating> retrieveRatingsByTutorId(Long tutorId) {
        List<Rating> ratings = retrieveAllRatings();
        List<Rating> filteredRatings = ratings.stream()
                .filter(r -> r.getOffer().getJobListing().getTutor().getPersonId().equals(tutorId))
                .collect(Collectors.toList());
        return filteredRatings;
    }

    @Override
    public List<Rating> retrieveRatingsByJobListingId(Long jobListingId) {
        List<Rating> ratings = retrieveAllRatings();
        List<Rating> filteredRatings = ratings.stream()
                .filter(r -> r.getOffer().getJobListing().getJobListingId().equals(jobListingId))
                .collect(Collectors.toList());
        return filteredRatings;
    }

    @Override
    public void updateRating(Rating updatedRating) {
        em.merge(updatedRating);
    }

    @Override
    public void deleteRating(Long ratingId) throws RatingNotFoundException {
        Rating rating = retrieveRatingById(ratingId);
        em.remove(rating);
    }
}
