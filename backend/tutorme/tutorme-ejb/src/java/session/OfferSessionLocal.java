/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Offer;
import exception.InvalidParamsException;
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

    public Offer createOffer(Double offeredRate, Date startDate, Long tuteeId, Long subjectId, Long jobListingId, int numSessions, double numHoursPerSession, String additionalNote) throws InvalidSubjectChoiceException, InvalidParamsException;

    public List<Offer> retrieveAllOffers();

    public Offer retrieveOfferById(Long offerId) throws OfferNotFoundException;

    public List<Offer> retrieveOffersByTuteeId(Long userId);

    public List<Offer> retrieveOffersByJobListingId(Long jobListingId);

    public void withdrawOffer(Long offerId) throws OfferNotFoundException, OfferWithdrawException;

    public void deleteOffer(Long offerId) throws OfferNotFoundException;

}
