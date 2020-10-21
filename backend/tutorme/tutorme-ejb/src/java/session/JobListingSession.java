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
        em.persist(newJobListing);
        return newJobListing;
    }

    @Override
    public JobListing createJobListing(Tutor tutor, List<Subject> subjects, Double hourlyRates, List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc) {
        JobListing newJobListing = new JobListing(tutor, subjects, hourlyRates, preferredTimeslots, preferredAreas, jobListingDesc);
        em.persist(newJobListing);
        
        return newJobListing;
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
        List<JobListing> filteredJobListings = new ArrayList();
        for (JobListing jl : jobListings) {
            if (!filteredJobListings.contains(jl)) {
                for (Subject s : jl.getSubjects()) {
                    if (s.getSubjectLevel().equals(subjectLevel)) {
                        filteredJobListings.add(jl);
                        break;
                    }
                }
            }
        }
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsBySubjectName(String subjectName) {
        List<JobListing> jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = new ArrayList();
        for (JobListing jl : jobListings) {
            if (!filteredJobListings.contains(jl)) {
                Subject s = jl.getSubjects().get(0);
                if (s.getSubjectName().equals(subjectName)) {
                    filteredJobListings.add(jl);
                }
            }
        }
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsBySubjectLevelAndName(String subjectLevel, String subjectName) {
        List<JobListing> jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = new ArrayList();
        for (JobListing jl : jobListings) {
            if (!filteredJobListings.contains(jl)) {
                for (Subject s : jl.getSubjects()) {
                    if (s.getSubjectLevel().equals(subjectLevel) && s.getSubjectName().equals(subjectName)) {
                        filteredJobListings.add(jl);
                        break;
                    }
                }
            }
        }
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsByTutorId(Long userId) {
        List<JobListing> jobListings = new ArrayList<>();
        jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = jobListings.stream()
                .filter(jl -> jl.getTutor().getPersonId().equals(userId))
                .collect(Collectors.toList());
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsByTutorName(String inputName) {
        List<JobListing> jobListings = new ArrayList<>();
        jobListings = retrieveAllJobListings();
        List<JobListing> filteredJobListings = jobListings.stream()
                .filter(jl -> jl.getTutor().getFirstName().equals(inputName) || jl.getTutor().getLastName().equals(inputName))
                .collect(Collectors.toList());
        return filteredJobListings;
    }

    @Override
    public List<JobListing> retrieveJobListingsWithMultipleFilters(String subjectName, String subjectLevel, Double minPrice, Double maxPrice, String inputName)  {
        // filter by name
        List<JobListing> result1 = retrieveJobListingsByTutorName(inputName);
        // filter by price
        result1.stream()
                .filter(jl -> jl.getHourlyRates() >= minPrice)
                .filter(jl -> jl.getHourlyRates() <= maxPrice)
                .collect(Collectors.toList());
        List<JobListing> result2 = new ArrayList<>();
        // filter by subjectName
        if (!subjectName.equals("")) {
            for (JobListing jl : result1) {
                List<Subject> subjects = jl.getSubjects();
                for (Subject s : subjects) {
                    if (s.getSubjectName().equals(subjectName)) {
                        result2.add(jl);
                        break;
                    }
                }
            }
        } else {
            result2.addAll(result1);
        }
        List<JobListing> result3 = new ArrayList<>();
        if (!subjectLevel.equals("")) {
            for (JobListing jl : result2) {
                List<Subject> subjects = jl.getSubjects();
                for (Subject s : subjects) {
                    if (s.getSubjectLevel().equals(subjectLevel)) {
                        result3.add(jl);
                        break;
                    }
                }
            }
        } else {
            result3.addAll(result2);
        }
        return result3;
    }

    @Override
    public JobListing retrieveJobListingByOffer(Long offerId) throws JobListingNotFoundException {
        JobListing jobListing = null;
        try {
            Offer offer = offerSession.retrieveOfferById(offerId);
            jobListing = offer.getJobListing();
            return jobListing;
        } catch (OfferNotFoundException ex) {
            throw new JobListingNotFoundException("JobListing was not found because OfferID " + offerId + "provided does not exists.");
        }
    }

    @Override
    public JobListing retrieveJobListingByRating(Long ratingId) throws JobListingNotFoundException {
        JobListing jobListing = null;
        try {
            Rating rating = ratingSession.retrieveRatingById(ratingId);
            Offer offer = rating.getOffer();
            jobListing = offer.getJobListing();
            return jobListing;
        } catch (RatingNotFoundException ex) {
            throw new JobListingNotFoundException("JobListing was not found because RatingID " + ratingId + "provided does not exists.");
        }
    }

    @Override
    public void updateJobListing(JobListing updatedJobListing) {
        em.merge(updatedJobListing);
    }

    @Override
    public void updateJobListing(Long jobListingId, List<Subject> subjects, Double hourlyRates, List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc) throws JobListingNotFoundException {
        JobListing jobListing = retrieveJobListingById(jobListingId);
        jobListing.setSubjects(subjects);
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
