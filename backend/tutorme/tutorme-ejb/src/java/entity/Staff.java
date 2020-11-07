/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.StaffPositionEnum;
import enumeration.GenderEnum;
import enumeration.PersonEnum;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tay Z H Owen
 */
@Entity
public class Staff extends Person implements Serializable, Principal {

    @NotNull
    @Enumerated
    private StaffPositionEnum staffPositionEnum;

    public Staff() {
        super();
        this.setPersonEnum(PersonEnum.STAFF);
    }

    public Staff(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob) {
        super(firstName, lastName, email, password, mobileNum, gender, dob);
        this.setPersonEnum(PersonEnum.STAFF);
        this.staffPositionEnum = staffPositionEnum;
    }

    public StaffPositionEnum getStaffPositionEnum() {
        return staffPositionEnum;
    }

    public void setStaffPositionEnum(StaffPositionEnum adminPositionEnum) {
        this.staffPositionEnum = adminPositionEnum;
    }
    
    @Override
    public String getName() {
        return String.valueOf(this.getPersonId());
    }

}
