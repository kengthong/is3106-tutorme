/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Offer;
import entity.Rating;
import exception.OfferNotFoundException;
import exception.OfferStatusException;
import exception.RatingNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface RatingSessionLocal {

    public Rating createRatingInit(Integer ratingValue, String comments, Long offerId) throws OfferNotFoundException, OfferStatusException;

    public Rating createRating(Integer ratingValue, String comments, Long offerId) throws OfferNotFoundException, OfferStatusException;

    public List<Rating> retrieveAllRatings();

    public Rating retrieveRatingById(Long ratingId) throws RatingNotFoundException;

    public Rating retrieveRatingByOfferId(Long offerId);

    public List<Rating> retrieveRatingsByTutorId(Long tutorId);

    public List<Rating> retrieveRatingsByJobListingId(Long jobListingId);

    public void deleteRating(Long ratingId) throws RatingNotFoundException;
}
