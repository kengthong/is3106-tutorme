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
import exception.RegistrationFailException;
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
    
    public Tutor createTutorInit(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob,QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc);
    
    public Tutor createTutor(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob) throws RegistrationFailException;

    public List<Tutor> retrieveAllTutors();

    public Tutor retrieveTutorById(Long tutorId) throws TutorNotFoundException;

    public Tutor retrieveTutorByEmail(String email) throws TutorNotFoundException;

    public List<Tutor> retrieveTutorsByName(String inputName);

    public Tutor updateTutorProfile(Long tutorId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob,
            QualificationEnum highestQualification, CitizenshipEnum citizenship, RaceEnum race, String profileDesc) throws TutorNotFoundException;
    
    // Reporting use
    public Integer getActiveTutors();
    
    public Integer getTutorGrowth();

}
