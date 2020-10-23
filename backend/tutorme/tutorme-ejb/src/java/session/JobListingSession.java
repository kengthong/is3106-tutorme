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
import entity.Tutee;
import entity.Tutor;
import exception.JobListingNotFoundException;
import exception.NewJobListingException;
import exception.OfferNotFoundException;
import exception.RatingNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public JobListing createJobListing(JobListing newJobListing) throws NewJobListingException {
        em.persist(newJobListing);
        return newJobListing;
    }

    @Override
    public JobListing createJobListing(Tutor tutor, List<Subject> subjects, Double hourlyRates, List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc) throws NewJobListingException {
        //get managed entity em.find
        Tutor managedTutor = em.find(Tutor.class, tutor.getPersonId());
        List<Subject> managedSubjects = new ArrayList<>();
        List<Timeslot> managedTimeslots = new ArrayList<>();
        List<Area> managedAreas = new ArrayList<>();
        for (Subject s : subjects) {
            managedSubjects.add(em.find(Subject.class, s.getSubjectId()));
        }
        for (Timeslot ts : preferredTimeslots) {
            managedTimeslots.add(em.find(Timeslot.class, ts.getTimeslotId()));
        }
        for (Area a : preferredAreas) {
            managedAreas.add(em.find(Area.class, a.getAreaId()));
        }
        JobListing newJobListing = new JobListing(managedTutor, managedSubjects, hourlyRates, managedTimeslots, managedAreas, jobListingDesc);
        managedTutor.getJobListings().add(newJobListing);
        String subjectName = subjects.get(0).getSubjectName();
        for (Subject s : subjects) {
            if (!s.getSubjectName().equals(subjectName)) {
                throw new NewJobListingException("JobListing can only be created with same subject name but different levels.");
            }
        }
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
    public List<JobListing> retrieveJobListingsWithMultipleFilters(String subjectName, String subjectLevel, Double minPrice, Double maxPrice, String inputName) {
        System.out.println("### inside JobListingSession's retrieveJobListingsWithMultipleFilters...");
        System.out.println("... subJectName: " + subjectName);
        System.out.println("... subjectLevel: " + subjectLevel);
        System.out.println("... minPrice: " + minPrice);
        System.out.println("... maxPrice: " + maxPrice);
        System.out.println("... inputName: " + inputName);
        // filter by name
        List<JobListing> result1 = retrieveJobListingsByTutorName(inputName);
        // filter by price
        List<JobListing> result2 = new ArrayList<>();
        for (JobListing jl : result1) {
            if (jl.getHourlyRates() >= minPrice && jl.getHourlyRates() <= maxPrice) {
                result2.add(jl);
            }
        }
        for (JobListing jl : result2) {
            System.out.println("### filtered by tutorName and price=" + jl.getHourlyRates() + " >> jobListingId:" + jl.getJobListingId());
        }
        List<JobListing> result3 = new ArrayList<>();
        // filter by subjectName
        if (!subjectName.equals(" ") && !subjectName.isEmpty()) {
            System.out.println("###%%% filtering subjectName...");
            for (JobListing jl : result2) {
                List<Subject> subjects = jl.getSubjects();
                for (Subject s : subjects) {
//                    System.out.println(">> Comparing subjectName db: " + s.getSubjectName() + " and input: " + subjectName);
                    if (s.getSubjectName().equals(subjectName)) {
                        System.out.println("###$$$ Matched subjectName db: " + s.getSubjectName() + " and input: " + subjectName);
                        result3.add(jl);
                        break;
                    }
                }
            }
        } else {
            result3.addAll(result2);
        }
        for (JobListing jl : result3) {
            System.out.println("### filtered by tutorName, price and subjectName >> jobListingId:" + jl.getJobListingId());
        }
        List<JobListing> result4 = new ArrayList<>();
        if (!subjectLevel.equals(" ") && !subjectLevel.isEmpty()) {
            System.out.println("###%%% filtering subjectLevel...");
            for (JobListing jl : result3) {
                List<Subject> subjects = jl.getSubjects();
                for (Subject s : subjects) {
//                    System.out.println(">> Comparing subjectLevel db: " + s.getSubjectLevel() + " and input: " + subjectLevel);
                    if (s.getSubjectLevel().equals(subjectLevel)) {
                        System.out.println("###$$$ Matched subjectLevel db: " + s.getSubjectLevel() + " and input: " + subjectLevel);
                        result4.add(jl);
                        break;
                    }
                }
            }
        } else {
            result4.addAll(result3);
        }
        for (JobListing jl : result4) {
            System.out.println("### filtered by tutorName, price, subjectName and subjectLevel >> jobListingId:" + jl.getJobListingId());
            em.detach(jl);
            jl.setReviewScore(retrieveJobListingRatingValue(jl.getJobListingId()));
            jl.setReviewCount(retrieveJobListingRatingCount(jl.getJobListingId()));
            Tutor tutor = jl.getTutor();
            em.detach(tutor);
            tutor.setSalt(null);
            tutor.setPassword(null);
            tutor.setSentMessages(null);
            tutor.setReceivedMessages(null);
            tutor.setJobListings(null);

            List<Offer> offers = jl.getOffers();
            for (Offer o : offers) {
                em.detach(o);
                o.setJobListing(null);
                Tutee tutee = o.getTutee();
                em.detach(tutee);
                tutee.setSalt(null);
                tutee.setPassword(null);
                tutee.setSentMessages(null);
                tutee.setReceivedMessages(null);
                tutee.setOffers(null);
            }
        }
        return result4;
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
    public int retrieveJobListingRatingCount(Long jobListingId) {
        int ratingCount = 0;
        try {
            JobListing jobListing = retrieveJobListingById(jobListingId);
            List<Offer> offers = jobListing.getOffers();
            for (Offer o : offers) {
                if (o.getRating() != null) {
                    ratingCount++;
                }
            }
        } catch (JobListingNotFoundException ex) {
            Logger.getLogger(JobListingSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ratingCount;
    }

    @Override
    public double retrieveJobListingRatingValue(Long jobListingId) {
        double ratingValue = 0;
        double ratingCount = Double.valueOf(retrieveJobListingRatingCount(jobListingId));
        try {
            JobListing jobListing = retrieveJobListingById(jobListingId);
            List<Offer> offers = jobListing.getOffers();
            for (Offer o : offers) {
                Rating rating = o.getRating();
                if (rating != null) {
                    ratingValue += rating.getRatingValue();
                }
            }
        } catch (JobListingNotFoundException ex) {
            Logger.getLogger(JobListingSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        double avgRatingValue = ratingValue / ratingCount;
        if (Double.isNaN(avgRatingValue) || Double.isInfinite(avgRatingValue)) {
            return 0;
        } else {
            return avgRatingValue;
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
