/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import enumeration.GenderEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany; 

/**
 *
 * @author Tay Z H Owen
 */
@Entity
//@JsonTypeName("tutee")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Tutee.class)
public class Tutee extends Person implements Serializable {

    private String profileDesc;

    @OneToMany(mappedBy = "tutee", fetch = FetchType.EAGER)
    private List<Offer> offers;

    public Tutee() {
        super();
        this.offers = new ArrayList();
    }

    public Tutee(String firstName, String lastName, String email, String password, String mobileNum, GenderEnum gender, Date dob, String profileDesc) {
        super(firstName, lastName, email, password, mobileNum, gender, dob);
        this.profileDesc = profileDesc;
        this.offers = new ArrayList();
    }

    public String getProfileDesc() {
        return profileDesc;
    }

    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}
