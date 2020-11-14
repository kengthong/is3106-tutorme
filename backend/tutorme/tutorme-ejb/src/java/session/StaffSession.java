/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Staff;
import enumeration.GenderEnum;
import enumeration.StaffPositionEnum;
import exception.RegistrationFailException;
import exception.StaffNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.security.CryptoHelper;

/**
 *
 * @author Owen Tay
 */
@Stateless
public class StaffSession implements StaffSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    private EntityManager em;
    private final CryptoHelper ch = CryptoHelper.getInstance();

    @Override
    public Staff createStaff(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, StaffPositionEnum staffPositionEnum) throws RegistrationFailException {
        Staff newStaff = new Staff();
        try {
            String salt = ch.generateRandomString(64);
            newStaff.setSalt(salt);
            String hashedPassword = ch.byteArrayToHexString(ch.doHashPassword(password.concat(salt)));
            newStaff.setPassword(hashedPassword);

            newStaff.setFirstName(firstName);
            newStaff.setLastName(lastName);
            newStaff.setEmail(email.toLowerCase());
            newStaff.setMobileNum(mobileNum);
            newStaff.setGender(gender);
            newStaff.setDob(dob);
            newStaff.setStaffPositionEnum(staffPositionEnum);
            em.persist(newStaff);
            try {
                em.persist(newStaff);
                em.flush();
                return newStaff;
            } catch (PersistenceException ex) {
                throw new RegistrationFailException("Email is in use");
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new RegistrationFailException("Something when wrong in creating new user's password salt");
        }
    }

    @Override
    public List<Staff> retrieveAllStaffs() {
        Query query = em.createQuery("SELECT a FROM Staff a");
        return query.getResultList();
    }

    @Override
    public Staff retrieveStaffById(Long personId) throws StaffNotFoundException {
        Staff staff = em.find(Staff.class, personId);
        if (staff != null) {
            return staff;
        } else {
            throw new StaffNotFoundException("StaffID " + personId + " does not exists.");
        }
    }

    @Override
    public Staff retrieveStaffByEmail(String email) throws StaffNotFoundException {
        Query query = em.createQuery("SELECT a FROM Staff a WHERE a.email = :inputEmail");
        query.setParameter("inputEmail", email.toLowerCase());
        Staff staff = (Staff) query.getSingleResult();
        if (staff != null) {
            return staff;
        } else {
            throw new StaffNotFoundException("Staff with email " + email + " does not exists.");
        }
    }

    @Override
    public Staff updateStaff(Long personId, String firstName, String lastName, String mobileNum, GenderEnum gender, Date dob) throws StaffNotFoundException {
        Staff staff = em.find(Staff.class, personId);
        if (staff != null) {
            staff.setFirstName(firstName);
            staff.setLastName(lastName);
            staff.setMobileNum(mobileNum);
            staff.setGender(gender);
            staff.setDob(dob);
            return staff;
        } else {
            throw new StaffNotFoundException("StaffID " + personId + " does not exists.");
        }
    }

    @Override
    public Staff changeStaffActiveStatus(Long personId) throws StaffNotFoundException {
        Staff staff = em.find(Staff.class, personId);
        if (staff != null) {
            if (staff.getActiveStatus()) {
                staff.setActiveStatus(true);
            } else {
                staff.setActiveStatus(false);
            }
            return staff;
        } else {
            throw new StaffNotFoundException("StaffID " + personId + " does not exists.");
        }
    }

    @Override
    public void deleteStaff(Long personId) throws StaffNotFoundException {
        Staff staff = retrieveStaffById(personId);
        em.remove(staff);
    }
}
