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
import exception.InvalidSubjectChoiceException;
import exception.OfferNotFoundException;
import exception.OfferWithdrawException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface OfferSessionLocal {

    public Offer createOffer(Offer newOffer);

    public Offer createOffer(Double offeredRate, Date startDate, Tutee tutee, Subject chosenSubject, JobListing jobListing, String additionalNote) throws InvalidSubjectChoiceException;

    public List<Offer> retrieveAllOffers();

    public Offer retrieveOfferById(Long offerId) throws OfferNotFoundException;

    public List<Offer> retrieveOffersByTuteeId(Long userId);

    public List<Offer> retrieveOffersByJobListingId(Long jobListingId);

    public void updateOffer(Offer updatedOffer);

    public void withdrawOffer(Long offerId) throws OfferNotFoundException, OfferWithdrawException;

    public void deleteOffer(Long offerId) throws OfferNotFoundException;

}
