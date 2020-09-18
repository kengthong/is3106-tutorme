/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.DowEnum;
import enumeration.ShiftEnum;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Tay Z H Owen
 */
@Table(
        uniqueConstraints
        = @UniqueConstraint(columnNames = {"dow", "shift"})
)
@Entity
public class Timeslot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeslotId;

    @Column(nullable = false)
    private DowEnum dow;

    @Column(nullable = false)
    private ShiftEnum shift;

    public Timeslot() {
    }

    public Timeslot(DowEnum dow, ShiftEnum shift) {
        this.dow = dow;
        this.shift = shift;
    }

    public Long getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(Long timeslotId) {
        this.timeslotId = timeslotId;
    }

    public DowEnum getDow() {
        return dow;
    }

    public void setDow(DowEnum dow) {
        this.dow = dow;
    }

    public ShiftEnum getShift() {
        return shift;
    }

    public void setShift(ShiftEnum shift) {
        this.shift = shift;
    }
}
