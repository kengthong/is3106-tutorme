/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Tutee;
import enumeration.GenderEnum;
import exception.InvalidParamsException;
import exception.RegistrationFailException;
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

    public Tutee createTuteeInit(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, String profileDesc);
    
    public Tutee createTutee(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob) throws RegistrationFailException;

    public List<Tutee> retrieveAllTutees();

    public Tutee retrieveTuteeById(Long tuteeId) throws TuteeNotFoundException;

    public Tutee retrieveTuteeByEmail(String email) throws TuteeNotFoundException;

    public List<Tutee> retrieveTuteeByName(String inputName);

    public Tutee updateTuteeProfile(Long tuteeId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob, String profileDesc) throws TuteeNotFoundException;
    
    // Reporting use
    public Integer getActiveTutees();
    
    public Integer getTuteeGrowth();

}
