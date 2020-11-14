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
import exception.InvalidParamsException;
import exception.JobListingNotFoundException;
import exception.NewJobListingException;
import exception.SubjectNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
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
    public JobListing createJobListingInit(Long tutorId, List<Long> subjectIds, Double hourlyRates, String timeslots, String areas, String jobListingTitle, String jobListingDesc) throws NewJobListingException {
        Tutor managedTutor = em.find(Tutor.class, tutorId);
        List<Subject> managedSubjects = new ArrayList<>();
        for (Long subjectId : subjectIds) {
            managedSubjects.add(em.find(Subject.class, subjectId));
        }
        String subjectName = managedSubjects.get(0).getSubjectName();
        for (Subject s : managedSubjects) {
            if (!s.getSubjectName().equals(subjectName)) {
                throw new NewJobListingException("JobListing can only be created with same subject name but different levels.");
            }
        }
        JobListing newJobListing = new JobListing(managedTutor, managedSubjects, hourlyRates, timeslots, areas, jobListingTitle, jobListingDesc);
        em.persist(newJobListing);
        List<JobListing> jobListings = managedTutor.getJobListings();
        jobListings.add(newJobListing);
        return newJobListing;
    }

    @Override
    public JobListing createJobListing(Long tutorId, String subjectName, List<String> subjectLevels, Double hourlyRates, String timeslots, String areas, String jobListingTitle, String jobListingDesc) throws SubjectNotFoundException {
        Tutor managedTutor = em.find(Tutor.class, tutorId);
        List<Subject> managedSubjects = new ArrayList<>();

        for (String subjectLevel : subjectLevels) {
            Query query = em.createQuery("SELECT s FROM Subject s WHERE s.subjectLevel = :inputSubjectLevel AND s.subjectName = :inputSubjectName");
            query.setParameter("inputSubjectLevel", subjectLevel);
            query.setParameter("inputSubjectName", subjectName);
            Subject s = (Subject) query.getSingleResult();
            if (s != null) {
                managedSubjects.add(s);
            } else {
                throw new SubjectNotFoundException("No Subjects by the level " + subjectLevel + " was found.");
            }
        }

        JobListing newJobListing = new JobListing(managedTutor, managedSubjects, hourlyRates, timeslots, areas, jobListingTitle, jobListingDesc);
        em.persist(newJobListing);
        List<JobListing> jobListings = managedTutor.getJobListings();
        jobListings.add(newJobListing);
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
                + "WHERE (LOWER(jl.tutor.firstName) like :inputName OR LOWER(jl.tutor.lastName) like :inputName) "
                + "AND jl.hourlyRates >= :inputMinPrice "
                + "AND jl.hourlyRates <= :inputMaxPrice");
        query.setParameter("inputName", "%" + inputName.toLowerCase() + "%");
        query.setParameter("inputMinPrice", minPrice);
        query.setParameter("inputMaxPrice", maxPrice);
        List<JobListing> result2 = query.getResultList();
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
                    if (s.getSubjectName().toLowerCase().equals(subjectName.toLowerCase())) {
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
    public JobListing updateJobListing(Long jobListingId, Double hourlyRates, String timeslots, String areas, String jobListingTitle, String jobListingDesc) throws SubjectNotFoundException, JobListingNotFoundException {
        JobListing jobListing = em.find(JobListing.class, jobListingId);
        if (jobListing != null) {
            jobListing.setHourlyRates(hourlyRates);
            jobListing.setTimeslots(timeslots);
            jobListing.setAreas(areas);
            jobListing.setJobListingTitle(jobListingTitle);
            jobListing.setJobListingDesc(jobListingDesc);
            return jobListing;
        } else {
            throw new JobListingNotFoundException("JobListingId " + jobListingId + " does not exists.");
        }
    }

    @Override
    public void activateJobListing(Long tutorId, Long jobListingId) throws JobListingNotFoundException, InvalidParamsException {
        JobListing jobListing = em.find(JobListing.class, jobListingId);
        if (jobListing != null) {
            if (Objects.equals(jobListing.getTutor().getPersonId(), tutorId)) {
                jobListing.setActiveStatus(true);
            } else {
                throw new InvalidParamsException("Joblisting does not belong to logged in Tutor");
            }
        } else {
            throw new JobListingNotFoundException("JobListingId " + jobListingId + " does not exists.");
        }
    }

    @Override
    public void deactivateJobListing(Long tutorId, Long jobListingId) throws JobListingNotFoundException, InvalidParamsException {
        JobListing jobListing = em.find(JobListing.class, jobListingId);
        if (jobListing != null) {
            if (Objects.equals(jobListing.getTutor().getPersonId(), tutorId)) {
                jobListing.setActiveStatus(false);
            } else {
                throw new InvalidParamsException("Joblisting does not belong to logged in Tutor");
            }
        } else {
            throw new JobListingNotFoundException("JobListingId " + jobListingId + " does not exists.");
        }
    }

    @Override
    public Integer getActiveJobListings() {
        Query query = em.createQuery("SELECT jl from JobListing jl WHERE jl.activeStatus=true");
        List<JobListing> jobListings = query.getResultList();
        return jobListings.size();
    }

    @Override
    public Integer getJobListingGrowth() {
        Query query1 = em.createQuery("SELECT jl from JobListing jl WHERE jl.createdDate < :inputDate");
        Query query2 = em.createQuery("SELECT jl from JobListing jl WHERE jl.createdDate >= :inputDate");

        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        query1.setParameter("inputDate", c.getTime());
        List<JobListing> jobListings1 = query1.getResultList();

        query2.setParameter("inputDate", c.getTime());
        List<JobListing> jobListings2 = query2.getResultList();

        Integer jl1 = jobListings1.size();
        Integer jl2 = jobListings2.size();
        return jl2 - jl1;
    }
}
