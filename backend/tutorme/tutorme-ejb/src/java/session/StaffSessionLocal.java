/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import exception.StaffNotFoundException;
import entity.Staff;
import enumeration.GenderEnum;
import enumeration.StaffPositionEnum;
import exception.RegistrationFailException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Owen Tay
 */
@Local
public interface StaffSessionLocal {

    public Staff createStaff(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, StaffPositionEnum staffPositionEnum) throws RegistrationFailException;

    public List<Staff> retrieveAllStaffs();

    public Staff retrieveStaffById(Long personId) throws StaffNotFoundException;

    public Staff retrieveStaffByEmail(String email) throws StaffNotFoundException;

    public Staff updateStaff(Long personId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob) throws StaffNotFoundException;

    public Staff changeStaffActiveStatus(Long personId) throws StaffNotFoundException;

    public void deleteStaff(Long personId) throws StaffNotFoundException;

}
