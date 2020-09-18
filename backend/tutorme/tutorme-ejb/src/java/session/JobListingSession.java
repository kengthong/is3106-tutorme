/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Area;
import entity.JobListing;
import entity.Offer;
import entity.Rating;
import entity.Subject;
import entity.Timeslot;
import entity.Tutor;
import exception.JobListingNotFoundException;
import exception.OfferNotFoundException;
import exception.RatingNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class JobListingSession implements JobListingSessionLocal {

    @EJB
    RatingSessionLocal ratingSession;
    @EJB
    OfferSessionLocal offerSession;
    @PersistenceContext(unitName = "tutorme-ejbPU")
    EntityManager em;

    @Override
    public JobListing createJobListing(JobListing newJobListing) {
        em.merge(newJobListing);
        em.flush();
        return newJobListing;
    }

    @Override
    public JobListing createJobListing(Tutor tutor, Subject subject, Double hourlyRates, List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc) {
        JobListing newJobListing = new JobListing(tutor, subject, hourlyRates, preferredTimeslots, preferredAreas, jobListingDesc);
        return createJobListing(newJobListing);
    }

    @Override
    public List<JobListing> retrieveAllJobListings() {
        Query query = em.createQuery("SELECT jl FROM JobListing jl");
        List<JobListing> results = query.getResultList();
        return results;
    }

    @Override
    public JobListing retrieveJobListingById(Long jobListingid) throws JobListingNotFoundException {
        JobListing jobListing = em.find(JobListing.class, jobListingid);
        if (jobListing != null) {
            return jobListing;
        } else {
            throw new JobListingNotFoundException("JobListingId " + jobListingid + " does not exists.");
        }
    }

    @Override
    public List<JobListing> retrieveJobListingsBySubjectLevel(String subjectLevel) {
        List<JobListing> jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = jobListings.stream()
                .filter(jl -> jl.getSubject().getSubjectLevel().equals(subjectLevel))
                .collect(Collectors.toList());
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsBySubjectName(String subjectName) {
        List<JobListing> jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = jobListings.stream()
                .filter(jl -> jl.getSubject().getSubjectName().equals(subjectName))
                .collect(Collectors.toList());
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsBySubjectLevelAndName(String subjectLevel, String subjectName) {
        List<JobListing> jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = jobListings.stream()
                .filter(jl -> jl.getSubject().getSubjectLevel().equals(subjectLevel))
                .filter(jl -> jl.getSubject().getSubjectName().equals(subjectName))
                .collect(Collectors.toList());
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsByTutorId(Long userId) throws JobListingNotFoundException {
        List<JobListing> jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = jobListings.stream()
                .filter(jl -> jl.getTutor().getPersonId().equals(userId))
                .collect(Collectors.toList());
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsByTutorName(String inputName) throws JobListingNotFoundException {
        List<JobListing> jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = jobListings.stream()
                .filter(jl -> jl.getTutor().getFirstName().equals(inputName) || jl.getTutor().getLastName().equals(inputName))
                .collect(Collectors.toList());
        return filteredJobListings;
    }

    @Override
    public JobListing retrieveJobListingByOffer(Long offerId) throws JobListingNotFoundException {
        JobListing jobListing = null;
        try {
            Offer offer = offerSession.retrieveOfferById(offerId);
            jobListing = offer.getJobListing();
        } catch (OfferNotFoundException ex) {
            throw new JobListingNotFoundException("JobListing was not found because OfferID " + offerId + "provided does not exists.");
        }
        return jobListing;
    }

    @Override
    public JobListing retrieveJobListingByRating(Long ratingId) throws JobListingNotFoundException {
        JobListing jobListing = null;
        try {
            Rating rating = ratingSession.retrieveRatingById(ratingId);
            Offer offer = rating.getOffer();
            jobListing = offer.getJobListing();
        } catch (RatingNotFoundException ex) {
            throw new JobListingNotFoundException("JobListing was not found because RatingID " + ratingId + "provided does not exists.");
        }
        return jobListing;
    }

    @Override
    public void updateJobListing(JobListing updatedJobListing) {
        em.merge(updatedJobListing);
    }

    @Override
    public void updateJobListing(Long jobListingId, Subject subject, Double hourlyRates,  List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc) throws JobListingNotFoundException {
        JobListing jobListing = retrieveJobListingById(jobListingId);
        jobListing.setSubject(subject);
        jobListing.setHourlyRates(hourlyRates);
        jobListing.setPreferredTimeslots(preferredTimeslots);
        jobListing.setPreferredAreas(preferredAreas);
        jobListing.setHourlyRates(hourlyRates);
        jobListing.setJobListingDesc(jobListingDesc);
        updateJobListing(jobListing);
    }

    @Override
    public void changeJobListingActiveStatus(Long jobListingId) throws JobListingNotFoundException {
        JobListing jobListing = retrieveJobListingById(jobListingId);
        if (jobListing.getActiveStatus()) {
            jobListing.setActiveStatus(false);
        } else {
            jobListing.setActiveStatus(true);
        }
        updateJobListing(jobListing);
    }

    @Override
    public void deleteJobListing(Long jobListingId) throws JobListingNotFoundException {
        JobListing jobListing = retrieveJobListingById(jobListingId);
        em.remove(jobListing);
    }
}
