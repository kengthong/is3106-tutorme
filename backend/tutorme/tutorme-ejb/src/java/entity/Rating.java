/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tay Z H Owen
 */
@Entity
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Double ratingValue;

    private String comments;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Offer offer;

    public Rating() {
    }

    public Rating(Double ratingValue, String comments, Offer offer) {
        this.createdDate = Date.from(Instant.now());
        this.ratingValue = ratingValue;
        this.comments = comments;
        this.offer = offer;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    public Double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Double starValue) {
        this.ratingValue = starValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
