/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.JobListing;
import exception.InvalidParamsException;
import exception.JobListingNotFoundException;
import exception.NewJobListingException;
import exception.SubjectNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface JobListingSessionLocal {

    public JobListing createJobListingInit(Long tutorId, List<Long> subjectIds, Double hourlyRates, String timeslots, String areas, String jobListingTitle, String jobListingDesc) throws NewJobListingException;

    public JobListing createJobListing(Long tutorId, String subjectName, List<String> subjectLevels, Double hourlyRates, String timeslots, String areas, String jobListingTitle, String jobListingDesc) throws SubjectNotFoundException;

    public List<JobListing> retrieveAllJobListings();

    public JobListing retrieveJobListingById(Long jobListingId) throws JobListingNotFoundException;

    public List<JobListing> retrieveJobListingsByTutorId(Long userId);

    public List<JobListing> retrieveJobListingsWithMultipleFilters(String subjectName, String subjectLevel, Double minPrice, Double maxPrice, String inputName);

    public double retrieveJobListingRatingValue(Long jobListingId) throws JobListingNotFoundException;

    public JobListing updateJobListing(Long tutorId, Double hourlyRates, String timeslots, String areas, String jobListingTitle, String jobListingDesc) throws SubjectNotFoundException, JobListingNotFoundException;

    public void activateJobListing(Long tutorId, Long jobListingId) throws JobListingNotFoundException, InvalidParamsException;

    public void deactivateJobListing(Long tutorId, Long jobListingId) throws JobListingNotFoundException, InvalidParamsException;

    // Reporting use
    public Integer getActiveJobListings();

    public Integer getJobListingGrowth();
}
