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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Owen Tay
 */
@Stateless
public class TimeslotSession implements TimeslotSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    EntityManager em;

    @Override
    public Timeslot createTimeslot(Timeslot timeslot) {
        em.persist(timeslot);
        em.flush();
        return timeslot;
    }

    @Override
    public Timeslot createTimeslot(DowEnum dow, ShiftEnum shift) {
        Timeslot timeslot = new Timeslot(dow, shift);
        return createTimeslot(timeslot);
    }

    @Override
    public List<Timeslot> retrieveAllTimeslots() {
        Query query = em.createQuery("SELECT t FROM Timeslot t");
        return query.getResultList();
    }

    @Override
    public Timeslot retrieveTimeslotById(Long timeslotId) throws TimeslotNotFoundException {
        Timeslot timeslot = em.find(Timeslot.class, timeslotId);
        if (timeslot != null) {
            return timeslot;
        } else {
            throw new TimeslotNotFoundException("TimeslotID " + timeslotId + " does not exists.");
        }
    }

    @Override
    public List<Timeslot> retrieveTimeslotsByDay(DowEnum day) throws TimeslotNotFoundException {
        Query query = em.createQuery("SELECT t FROM Timeslot t WHERE t.dow = :inputDay");
        query.setParameter("inputDay", day);
        List<Timeslot> timeslots = query.getResultList();
        if (timeslots != null) {
            return timeslots;
        } else {
            throw new TimeslotNotFoundException("No Timeslots fall on " + day + ".");
        }
    }

    @Override
    public List<Timeslot> retrieveTimeslotsByShift(ShiftEnum shift) throws TimeslotNotFoundException {
        Query query = em.createQuery("SELECT t FROM Timeslot t WHERE t.shift = :inputShift");
        query.setParameter("inputShift", shift);
        List<Timeslot> timeslots = query.getResultList();
        if (timeslots != null) {
            return timeslots;
        } else {
            throw new TimeslotNotFoundException("No Timeslots fall on " + shift + ".");
        }
    }

    @Override
    public void updateTimeslot(Timeslot timeslot) {
        em.merge(timeslot);
    }

    @Override
    public void updateTimeslot(Long timeslotId, DowEnum day, ShiftEnum shift) throws TimeslotNotFoundException {
        Timeslot timeslot = retrieveTimeslotById(timeslotId);
        timeslot.setDow(day);
        timeslot.setShift(shift);
        updateTimeslot(timeslot);
    }

    @Override
    public void deleteTimeslot(Long timeslotId) throws TimeslotNotFoundException {
        Timeslot timeslot = retrieveTimeslotById(timeslotId);
        em.remove(timeslot);
    }

}
