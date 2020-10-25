/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.JobListing;
import entity.Subject;
import entity.Tutor;
import exception.JobListingNotFoundException;
import exception.NewJobListingException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface JobListingSessionLocal {

    public JobListing createJobListing(Long tutorId, List<Long> subjectIds, Double hourlyRates, String timeslots, String areas, String jobListingDesc) throws NewJobListingException;

    public List<JobListing> retrieveAllJobListings();

    public JobListing retrieveJobListingById(Long jobListingid) throws JobListingNotFoundException;

    public List<JobListing> retrieveJobListingsByTutorId(Long userId);

    public List<JobListing> retrieveJobListingsWithMultipleFilters(String subjectName, String subjectLevel, Double minPrice, Double maxPrice, String inputName);

    public double retrieveJobListingRatingValue(Long jobListingId) throws JobListingNotFoundException;

    public void updateJobListing(Long jobListingId, List<Subject> subjects, Double hourlyRates, String timeslots, String areas, String jobListingDesc) throws JobListingNotFoundException;

    public void changeJobListingActiveStatus(Long jobListingId) throws JobListingNotFoundException;

    public void deleteJobListing(Long jobListingId) throws JobListingNotFoundException;
}
