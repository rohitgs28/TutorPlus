package com.dal.group7.tutorplus.model;

import java.io.Serializable;

public class SubjectModel implements Serializable {
    private int subjectId;
    private String subjectName;

    public SubjectModel(){

    }
    public SubjectModel(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
