/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Subject;
import exception.SubjectNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tay Z H Owen
 */
@Stateless
public class SubjectSession implements SubjectSessionLocal {

    @PersistenceContext(unitName = "tutorme-ejbPU")
    EntityManager em;

    @Override
    public Subject createSubject(Subject newSubject) {
        em.persist(newSubject);
        em.flush();
        return newSubject;
    }

    @Override
    public Subject createSubject(String subjectName, String subjectLevel) {
        Subject newSubject = new Subject(subjectName, subjectLevel);
        return createSubject(newSubject);
    }

    @Override
    public List<Subject> retrieveAllSubjects() {
        Query query = em.createQuery("SELECT s from Subject s");
        List<Subject> subjects = query.getResultList();
        return subjects;
    }

    @Override
    public Subject retrieveSubjectById(Long subjectId) throws SubjectNotFoundException {
        Subject subject = em.find(Subject.class, subjectId);
        if (subject != null) {
            return subject;
        } else {
            throw new SubjectNotFoundException("SubjectID " + subjectId + " does not exists.");
        }
    }

    @Override
    public List<Subject> retrieveSubjectsByName(String subjectName) throws SubjectNotFoundException {
        Query query = em.createQuery("SELECT s FROM Subject s WHERE s.subjectName = :inputSubjectName");
        query.setParameter("inputSubjectName", subjectName);
        List<Subject> results = query.getResultList();
        if (!results.isEmpty()) { // if empty then return message in REST
            return results;
        } else {
            throw new SubjectNotFoundException("No Subjects by the name " + subjectName + " was found.");
        }
    }

    @Override
    public List<Subject> retrieveSubjectsLevel(String subjectLevel) throws SubjectNotFoundException {
        Query query = em.createQuery("SELECT s FROM Subject s WHERE s.subjectLevel = :inputSubjectLevel");
        query.setParameter("inputSubjectLevel", subjectLevel);
        List<Subject> results = query.getResultList();
        if (!results.isEmpty()) { // if empty then return message in REST
            return results;
        } else {
            throw new SubjectNotFoundException("No Subjects by the level " + subjectLevel + " was found.");
        }
    }

    @Override
    public Subject retrieveSubject(String subjectName, String subjectLevel) throws SubjectNotFoundException {
        Query query = em.createQuery("SELECT s FROM Subject s WHERE s.subjectLevel = :inputSubjectLevel AND s.subjectName = :inputSubjectName");
        query.setParameter("inputSubjectLevel", subjectLevel);
        query.setParameter("inputSubjectName", subjectName);
        Subject result = (Subject) query.getSingleResult();
        if (result != null) { // if empty then return message in REST
            return result;
        } else {
            throw new SubjectNotFoundException("No Subjects by the level " + subjectLevel + " was found.");
        }
    }

    @Override
    public void updateSubject(Subject subject) throws SubjectNotFoundException {
        em.merge(subject);
    }

    @Override
    public void updateSubject(Long subjectId, String subjectName, String subjectLevel) throws SubjectNotFoundException {
        Subject subject = retrieveSubjectById(subjectId);
        subject.setSubjectName(subjectName);
        subject.setSubjectLevel(subjectLevel);
        updateSubject(subject);
    }

    @Override
    public void deleteSubject(Long subjectId) throws SubjectNotFoundException {
        Subject subject = retrieveSubjectById(subjectId);
        em.remove(subject);
    }
}
