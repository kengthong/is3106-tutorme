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

//    public Tutee loginTutee(String email, String password) throws TuteeNotFoundException;

    public Tutee createTutee(Tutee newTutee);

    public Tutee createTutee(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, String profileDesc);

    public List<Tutee> retrieveAllTutees();

    public Tutee retrieveTuteeById(Long personId) throws TuteeNotFoundException;

    public Tutee retrieveTuteeByEmail(String email) throws TuteeNotFoundException;

    public List<Tutee> retrieveTuteeByName(String inputName) throws TuteeNotFoundException;

    public Tutee retrieveTuteeByOffer(Long offerId) throws TuteeNotFoundException;

    public Tutee retrieveTuteeByRating(Long ratingId) throws TuteeNotFoundException;

    public void updateTutee(Tutee updatedTutee);

    public void updateTutee(Long personId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob, String profileDesc) throws TuteeNotFoundException;

    public void changeTuteeActiveStatus(Long personId) throws TuteeNotFoundException;

    public void deleteTutee(Long personId) throws TuteeNotFoundException;

}
