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
import exception.OfferStatusException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface OfferSessionLocal {

    public Offer createOffer(Double offeredRate, Date startDate, Long tuteeId, Long subjectId, Long jobListingId, int numSessions, double numHoursPerSession, String additionalNote) throws InvalidSubjectChoiceException, InvalidParamsException;

    public Offer retrieveOfferById(Long offerId) throws OfferNotFoundException;

    public List<Offer> retrieveAllOffers();

    public List<Offer> retrieveOffersByPersonId(Long personId);

    public List<Offer> retrieveOffersByJobListingId(Long jobListingId);

    public Offer acceptOffer(Long offerId) throws OfferNotFoundException, OfferStatusException;

    public Offer rejectOffer(Long offerId) throws OfferNotFoundException, OfferStatusException;

    public Offer withdrawOffer(Long offerId) throws OfferNotFoundException, OfferStatusException;

    public void deleteOffer(Long offerId) throws OfferNotFoundException;
    
    // Reporting use
    public Integer getActiveOffers();
    
    public Integer getOfferGrowth();    
    
    public Double getOfferAcceptanceRate();
    
    public Double getOfferRejectionRate();

}
