/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Timeslot;
import enumeration.DowEnum;
import enumeration.ShiftEnum;
import exception.TimeslotNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Owen Tay
 */
@Local
public interface TimeslotSessionLocal {
    public Timeslot createTimeslot(Timeslot timeslot);
    
    public Timeslot createTimeslot(DowEnum dow, ShiftEnum shift);
    
    public List<Timeslot> retrieveAllTimeslots();
    
    public Timeslot retrieveTimeslotById (Long timeslotId) throws TimeslotNotFoundException;
    
    public List<Timeslot> retrieveTimeslotsByDay(DowEnum dow) throws TimeslotNotFoundException;
    
    public List<Timeslot> retrieveTimeslotsByShift(ShiftEnum shift) throws TimeslotNotFoundException;
    
    public void updateTimeslot (Timeslot timeslot);
    
    public void updateTimeslot(Long timeslotId, DowEnum day, ShiftEnum shift) throws TimeslotNotFoundException;
    
    public void deleteTimeslot(Long timeslotId) throws TimeslotNotFoundException;
}
