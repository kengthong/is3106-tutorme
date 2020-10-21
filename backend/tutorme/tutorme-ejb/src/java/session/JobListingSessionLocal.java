/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Area;
import entity.JobListing;
import entity.Subject;
import entity.Timeslot;
import entity.Tutor;
import exception.JobListingNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface JobListingSessionLocal {

    public JobListing createJobListing(JobListing newJobListing);

    public JobListing createJobListing(Tutor tutor, List<Subject> subjects, Double hourlyRates, List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc);

    public List<JobListing> retrieveAllJobListings();

    public JobListing retrieveJobListingById(Long jobListingid) throws JobListingNotFoundException;

    public List<JobListing> retrieveJobListingsBySubjectLevel(String subjectLevel);

    public List<JobListing> retrieveJobListingsBySubjectName(String subjectName);
    
    public List<JobListing> retrieveJobListingsBySubjectLevelAndName (String subjectLevel, String subjectName);

    public List<JobListing> retrieveJobListingsByTutorId(Long userId);

    public List<JobListing> retrieveJobListingsByTutorName(String inputName);
    
    public List<JobListing> retrieveJobListingsWithMultipleFilters(String subjectName, String subjectLevel, Double minPrice, Double maxPrice, String inputName) ;
    
    public JobListing retrieveJobListingByOffer(Long offerId) throws JobListingNotFoundException;
    
    public JobListing retrieveJobListingByRating(Long ratingId) throws JobListingNotFoundException;

    public void updateJobListing(JobListing updatedJobListing);

    public void updateJobListing(Long jobListingId, List<Subject> subjects, Double hourlyRates,  List<Timeslot> preferredTimeslots, List<Area> preferredAreas, String jobListingDesc) throws JobListingNotFoundException;

    public void changeJobListingActiveStatus(Long jobListingId) throws JobListingNotFoundException;

    public void deleteJobListing(Long jobListingId) throws JobListingNotFoundException;
}
