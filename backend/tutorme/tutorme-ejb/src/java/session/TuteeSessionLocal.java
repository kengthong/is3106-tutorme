/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Tutee;
import enumeration.GenderEnum;
import exception.TuteeNotFoundException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface TuteeSessionLocal {

    public Tutee createTuteeInit(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, String profileDesc, String profileImage);
    
    public Tutee createTutee(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob);

    public List<Tutee> retrieveAllTutees();

    public Tutee retrieveTuteeById(Long tuteeId) throws TuteeNotFoundException;

    public Tutee retrieveTuteeByEmail(String email) throws TuteeNotFoundException;

    public List<Tutee> retrieveTuteeByName(String inputName);

    public Tutee updateTuteeProfile(Long tuteeId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob, String profileDesc, String profileImage) throws TuteeNotFoundException;

    public Tutee activateTuteeStatus(Long tuteeId) throws TuteeNotFoundException;

    public Tutee deactivateTuteeStatus(Long tuteeId) throws TuteeNotFoundException;
    
    // Reporting use
    public Integer getActiveTutees();
    
    public Integer getTuteeGrowth();

}
