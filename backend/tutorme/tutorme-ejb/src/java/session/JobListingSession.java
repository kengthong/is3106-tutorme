/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.JobListing;
import entity.Offer;
import entity.Rating;
import entity.Subject;
import entity.Tutor;
import exception.JobListingNotFoundException;
import exception.NewJobListingException;
import java.util.ArrayList;
import java.util.List;
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

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;

    @Override
    public JobListing createJobListing(Long tutorId, List<Long> subjectIds, Double hourlyRates, String timeslots, String areas, String jobListingDesc) throws NewJobListingException {
        Tutor managedTutor = em.find(Tutor.class, tutorId);
        List<Subject> managedSubjects = new ArrayList<>();
        for (Long subjectId : subjectIds) {
            managedSubjects.add(em.find(Subject.class, subjectId));
        }
        JobListing newJobListing = new JobListing(managedTutor, managedSubjects, hourlyRates, timeslots, areas, jobListingDesc);
        em.persist(newJobListing);
        managedTutor.getJobListings().add(newJobListing);
        String subjectName = managedSubjects.get(0).getSubjectName();
        for (Subject s : managedSubjects) {
            if (!s.getSubjectName().equals(subjectName)) {
                throw new NewJobListingException("JobListing can only be created with same subject name but different levels.");
            }
        }
        return newJobListing;
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
    public List<JobListing> retrieveAllJobListings() {
        Query query = em.createQuery("SELECT jl FROM JobListing jl");
        List<JobListing> results = query.getResultList();
        return results;
    }

    @Override
    public List<JobListing> retrieveJobListingsByTutorId(Long tutorId) {
        Query query = em.createQuery("SELECT jl FROM JobListing jl WHERE jl.tutor.personId = :inputTutorId");
        query.setParameter("inputTutorId", tutorId);
        return query.getResultList();
    }

    @Override
    public List<JobListing> retrieveJobListingsWithMultipleFilters(String subjectName, String subjectLevel, Double minPrice, Double maxPrice, String inputName) {
        System.out.println("### inside JobListingSession's retrieveJobListingsWithMultipleFilters...");
        System.out.println("... subJectName: " + subjectName);
        System.out.println("... subjectLevel: " + subjectLevel);
        System.out.println("... minPrice: " + minPrice);
        System.out.println("... maxPrice: " + maxPrice);
        System.out.println("... inputName: " + inputName);
        // filter by name & price
        Query query = em.createQuery("SELECT jl FROM JobListing jl "
                + "WHERE (jl.tutor.firstName = :inputName OR jl.tutor.lastName = :inputName) "
                + "AND jl.hourlyRates >= :inputMinPrice "
                + "AND jl.hourlyRates <= :inputMaxPrice");
        query.setParameter("inputName", inputName);
        query.setParameter("inputMinPrice", minPrice);
        query.setParameter("inputMaxPrice", maxPrice);
        List<JobListing> result2 = query.getResultList();
        // filter by price
//        List<JobListing> result2 = new ArrayList<>();
//        for (JobListing jl : result1) {
//            if (jl.getHourlyRates() >= minPrice && jl.getHourlyRates() <= maxPrice) {
//                result2.add(jl);
//            }
//        }
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
        }
        return result4;
    }

    @Override
    public double retrieveJobListingRatingValue(Long jobListingId) throws JobListingNotFoundException {
        double ratingValue = 0;
        double ratingCount = 0;

        JobListing jobListing = retrieveJobListingById(jobListingId);
        List<Offer> offers = jobListing.getOffers();
        for (Offer o : offers) {
            Rating rating = o.getRating();
            if (rating != null) {
                ratingValue += rating.getRatingValue();
                ratingCount++;
            }
        }

        double avgRatingValue = ratingValue / ratingCount;
        if (Double.isNaN(avgRatingValue) || Double.isInfinite(avgRatingValue)) {
            return 0;
        } else {
            return avgRatingValue;
        }
    }

    @Override
    public void updateJobListing(Long jobListingId, List<Subject> subjects, Double hourlyRates, String timeslots, String areas, String jobListingDesc) throws JobListingNotFoundException {
        JobListing jobListing = retrieveJobListingById(jobListingId);
        jobListing.setSubjects(subjects);
        jobListing.setHourlyRates(hourlyRates);
        jobListing.setTimeslots(timeslots);
        jobListing.setAreas(areas);
        jobListing.setHourlyRates(hourlyRates);
        jobListing.setJobListingDesc(jobListingDesc);
    }

    @Override
    public void changeJobListingActiveStatus(Long jobListingId) throws JobListingNotFoundException {
        JobListing jobListing = retrieveJobListingById(jobListingId);
        if (jobListing.getActiveStatus()) {
            jobListing.setActiveStatus(false);
        } else {
            jobListing.setActiveStatus(true);
        }
    }

    @Override
    public void deleteJobListing(Long jobListingId) throws JobListingNotFoundException {
        JobListing jobListing = retrieveJobListingById(jobListingId);
        em.remove(jobListing);
    }

}
