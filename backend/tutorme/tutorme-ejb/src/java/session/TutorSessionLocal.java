/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Tutor;
import enumeration.CitizenshipEnum;
import enumeration.GenderEnum;
import enumeration.QualificationEnum;
import enumeration.RaceEnum;
import exception.TutorNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface TutorSessionLocal {

//    public Tutor loginTutor(String email, String password) throws PersonLoginFailException;

    public Tutor createTutor(Tutor newTutor);

    public Tutor createTutor(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob,
            QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc);

    public List<Tutor> retrieveAllTutors();

    public Tutor retrieveTutorById(Long personId) throws TutorNotFoundException;

    public Tutor retrieveTutorByEmail(String email) throws TutorNotFoundException;

    public List<Tutor> retrieveTutorsByName(String inputName) throws TutorNotFoundException;

    public Tutor retrieveTutorByJobListing(Long jobListingId)throws TutorNotFoundException;

    public Tutor retrieveTutorByOffer(Long offerId) throws TutorNotFoundException;

    public Tutor retrieveTutorByRating(Long ratingId) throws TutorNotFoundException;

    public Tutor updateTutorProfile(Tutor updatedTutor);

    public Tutor updateTutorProfile(Long personId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob,
            QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc) throws TutorNotFoundException;

    public Tutor deactivateTutor (Long personId) throws TutorNotFoundException;

    public void deleteTutor(Long personId) throws TutorNotFoundException;

}
