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

    public Subject createSubject(String subjectLevel, String subjectName);

    public Subject retrieveSubjectById(Long subjectId) throws SubjectNotFoundException;
    
    public List<Subject> retrieveAllSubjects();

    public List<Subject> retrieveSubjectsByName(String subjectName) throws SubjectNotFoundException;

    public List<Subject> retrieveSubjectsLevel(String subjectLevel) throws SubjectNotFoundException;

    public Subject retrieveSubjectByNameAndLevel(String subjectName, String subjectLevel) throws SubjectNotFoundException;

    public Subject updateSubject(Long subjectId, String subjectName, String subjectLevel) throws SubjectNotFoundException;

    public void deleteSubject(Long subjectId) throws SubjectNotFoundException;

}
