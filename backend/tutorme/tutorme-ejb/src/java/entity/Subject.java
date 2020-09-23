/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Tay Z H Owen
 */
@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"subjectName", "subjectLevel"})
)
@Entity
public class Subject implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;
    
    @NotNull
    @Size(min = 1, max = 50)
    private String subjectName;
    
    @NotNull
    @Size(min = 1, max = 50)
    private String subjectLevel;

    public Subject() {
    }
    
    public Subject (String subjectName, String subjectLevel) {
        this.subjectName = subjectName;
        this.subjectLevel = subjectLevel;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectLevel() {
        return subjectLevel;
    }

    public void setSubjectLevel(String subjectLevel) {
        this.subjectLevel = subjectLevel;
    }

    @Override
    public String toString() {
        return "Subject{" + "subjectId=" + subjectId + ", subjectName=" + subjectName + ", subjectLevel=" + subjectLevel + '}';
    }
    
}
