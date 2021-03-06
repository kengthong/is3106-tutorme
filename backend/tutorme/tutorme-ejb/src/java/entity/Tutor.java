/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.CitizenshipEnum;
import enumeration.GenderEnum;
import enumeration.PersonEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author Tay Z H Owen
 */
@Entity
public class Tutor extends Person implements Serializable {

    @Enumerated
    private QualificationEnum highestQualification;

    @Enumerated
    private CitizenshipEnum citizenship;

    @Enumerated
    private RaceEnum race;

    private String profileDesc;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "tutor")
    private List<JobListing> jobListings;

    private Double avgRating;
    private Integer ratingCount;

    public Tutor() {
        super();
        this.setPersonEnum(PersonEnum.TUTOR);
        this.jobListings = new ArrayList<>();
        this.avgRating = 0.0;
    }

    public Tutor(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob,
            QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc) {
        super(firstName, lastName, email, password, mobileNum, gender, dob);
        this.profileDesc = profileDesc;
        this.highestQualification = highestQualification;
        this.citizenship = citizenship;
        this.race = race;
        this.setPersonEnum(PersonEnum.TUTOR);
        this.jobListings = new ArrayList<>();
        this.avgRating = 0.0;
    }

    public String getProfileDesc() {
        return profileDesc;
    }

    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    public QualificationEnum getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(QualificationEnum highestQualification) {
        this.highestQualification = highestQualification;
    }

    public CitizenshipEnum getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(CitizenshipEnum citizenship) {
        this.citizenship = citizenship;
    }

    public RaceEnum getRace() {
        return race;
    }

    public void setRace(RaceEnum race) {
        this.race = race;
    }

    public List<JobListing> getJobListings() {
        return jobListings;
    }

    public void setJobListings(List<JobListing> jobListings) {
        this.jobListings = jobListings;
    }

    public void setAvgRating(Double dummy) {
        this.avgRating = dummy;
    }

    public Double getAvgRating() {
        Integer numOffers = 0;
        Integer totalReviewValue = 0;
        if (this.jobListings != null) {
            for (JobListing jl : this.jobListings) {
                numOffers += jl.getNumOffers();
                totalReviewValue += jl.getTotalReviewScore();
            }
            Double avg = new Double(totalReviewValue) / new Double(numOffers);
            if (Double.isNaN(avg) || Double.isInfinite(avg)) {
                return 0.0;
            } else {
                return avg;
            }
        } else {
            return 0.0;
        }
    }
    
    public void setRatingCount(Integer dummy) {
        this.ratingCount = dummy;
    }

    public Integer getRatingCount() {
        Integer count = 0;
        if (this.jobListings != null) {
            for (JobListing jl : this.jobListings) {
                count++;
            }
        }
        return count;

    }

    @Override
    public String toString() {
        return "Tutor{" + "highestQualification=" + highestQualification + ", citizenship=" + citizenship + ", race=" + race + ", profileDesc=" + profileDesc + '}';
    }

}
