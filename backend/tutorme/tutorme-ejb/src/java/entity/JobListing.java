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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tutor tutor;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    private List<Subject> subjects;

    @Column(nullable = false, precision = 2)
    private Double hourlyRates;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    private List<Timeslot> preferredTimeslots;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    private List<Area> preferredAreas;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private String jobListingDesc;

    @OneToMany(mappedBy = "jobListing", fetch = FetchType.LAZY)
    private List<Offer> offers;

    public JobListing() {
    }

    public JobListing(Tutor tutor, List<Subject> subjects, Double hourlyRates, List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc) {
        this.tutor = tutor;
        this.subjects = subjects;
        this.hourlyRates = hourlyRates;
        this.preferredTimeslots = preferredTimeslots;
        this.preferredAreas = preferredAreas;
        this.jobListingDesc = jobListingDesc;
        this.createdDate = new Date();
        this.activeStatus = true;
        this.offers = new ArrayList();
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

}