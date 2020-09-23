/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Subject;
import exception.SubjectNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Tay Z H Owen
 */
@Local
public interface SubjectSessionLocal {

    public Subject createSubject(Subject newSubject);

    public Subject createSubject(String subjectName, String subjectLevel);

    public Subject retrieveSubjectById(Long subjectId) throws SubjectNotFoundException;
    
    public List<Subject> retrieveAllSubjects();

    public List<Subject> retrieveSubjectsByName(String subjectName) throws SubjectNotFoundException;

    public List<Subject> retrieveSubjectsLevel(String subjectLevel) throws SubjectNotFoundException;

    public Subject retrieveSubject(String subjectName, String subjectLevel) throws SubjectNotFoundException;

    public void updateSubject(Subject subject) throws SubjectNotFoundException;

    public void updateSubject(Long subjectId, String subjectName, String subjectLevel) throws SubjectNotFoundException;

    public void deleteSubject(Long subjectId) throws SubjectNotFoundException;

}
