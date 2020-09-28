/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.OfferStatusEnum;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tay Z H Owen
 */
@Entity
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @NotNull
    private Double offeredRate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tutee tutee;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private JobListing jobListing;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Subject chosenSubject;

    @NotNull
    @Enumerated
    private OfferStatusEnum offerStatus;

    private String additionalNote;

    @OneToOne(mappedBy = "offer", fetch = FetchType.LAZY, optional = true)
    private Rating rating;

    public Offer() {
    }

    public Offer(Double offeredRate, Date startDate, Tutee tutee, Subject chosenSubject, JobListing jobListing, String additionalNote) {
        this.offeredRate = offeredRate;
        this.startDate = startDate;
        this.tutee = tutee;
        this.chosenSubject = chosenSubject;
        this.jobListing = jobListing;
        this.additionalNote = additionalNote;
        this.createdDate = Date.from(Instant.now());
        this.offerStatus = OfferStatusEnum.PENDING;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getOfferedRate() {
        return offeredRate;
    }

    public void setOfferedRate(Double offeredRate) {
        this.offeredRate = offeredRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Tutee getTutee() {
        return tutee;
    }

    public void setTutee(Tutee tutee) {
        this.tutee = tutee;
    }

    public Subject getChosenSubject() {
        return chosenSubject;
    }

    public void setChosenSubject(Subject chosenSubject) {
        this.chosenSubject = chosenSubject;
    }

    public JobListing getJobListing() {
        return jobListing;
    }

    public void setJobListing(JobListing jobListing) {
        this.jobListing = jobListing;
    }

    public OfferStatusEnum getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatusEnum offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

}