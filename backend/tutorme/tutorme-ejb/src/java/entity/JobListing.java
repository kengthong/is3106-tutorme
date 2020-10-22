/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Subject> subjects;

    @Column(nullable = false, precision = 2)
    private Double hourlyRates;

    private String timeslots;
    private String areas;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createdDate;

    private String jobListingDesc;

    @OneToMany(mappedBy = "jobListing", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Offer> offers;

//    private double reviewScore;
//    private int reviewCount;

    public JobListing() {
        this.createdDate = new Date();
        this.activeStatus = true;
        this.offers = new ArrayList();
    }

    public JobListing(Tutor tutor, List<Subject> subjects, Double hourlyRates, String timeslots, String areas, String jobListingDesc) {
        this();
        this.tutor = tutor;
        this.subjects = subjects;
        this.hourlyRates = hourlyRates;
        this.timeslots = timeslots;
        this.areas = areas;
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

    public String getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(String timeslots) {
        this.timeslots = timeslots;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
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
        double sum = 0;
        int count = 0;
        if (this.offers != null) {
            for (Offer o : this.offers) {
                Rating rating = o.getRating();
                if (rating != null) {
                    sum = rating.getRatingValue();
                    count++;
                }
            }
        }
        double avg = sum / count;
        if (Double.isNaN(avg) || Double.isInfinite(avg)) {
            return 0;
        } else {
            return avg;
        }
    }

    public int getReviewCount() {
        int count = 0;
        if (this.offers != null) {
            for (Offer o : this.offers) {
                Rating rating = o.getRating();
                if (rating != null) {
                    count++;
                }
            }
        }
        return count;
    }

}
