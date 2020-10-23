/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Tay Z H Owen
 */
@Entity
public class JobListing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobListingId;

    private Boolean activeStatus;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Tutor tutor;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn // uni-directional r/s best to use @JoinColumn and omit mappedBy
    private List<Subject> subjects;

    @Column(nullable = false, precision = 2)
    private Double hourlyRates;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn // uni-directional r/s best to use @JoinColumn and omit mappedBy
    private List<Timeslot> preferredTimeslots;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn // uni-directional r/s best to use @JoinColumn and omit mappedBy
    private List<Area> preferredAreas;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String jobListingDesc;

    @OneToMany(mappedBy = "jobListing", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Offer> offers;

    private double reviewScore;
    private int reviewCount;

    public JobListing() {
        this.createdDate = new Date();
        this.activeStatus = true;
        this.offers = new ArrayList();
    }

    public JobListing(Tutor tutor, List<Subject> subjects, Double hourlyRates, List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc) {
        this();
        this.tutor = tutor;
        this.subjects = subjects;
        this.hourlyRates = hourlyRates;
        this.preferredTimeslots = preferredTimeslots;
        this.preferredAreas = preferredAreas;
        this.jobListingDesc = jobListingDesc;
    }

    public Long getJobListingId() {
        return jobListingId;
    }

    public void setJobListingId(Long jobListingId) {
        this.jobListingId = jobListingId;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Double getHourlyRates() {
        return hourlyRates;
    }

    public void setHourlyRates(Double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    public List<Timeslot> getPreferredTimeslots() {
        return preferredTimeslots;
    }

    public void setPreferredTimeslots(List<Timeslot> preferredTimeslots) {
        this.preferredTimeslots = preferredTimeslots;
    }

    public List<Area> getPreferredAreas() {
        return preferredAreas;
    }

    public void setPreferredAreas(List<Area> preferredAreas) {
        this.preferredAreas = preferredAreas;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getJobListingDesc() {
        return jobListingDesc;
    }

    public void setJobListingDesc(String jobListingDesc) {
        this.jobListingDesc = jobListingDesc;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public double getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(double reviewScore) {
        this.reviewScore = reviewScore;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

}
