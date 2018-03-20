package com.dal.group7.tutorplus.model;

import java.io.Serializable;


public class TutorVideoModel implements Serializable {
    public int getTutorVideoLinkid() {
        return tutorVideoLinkid;
    }

    public void setTutorVideoLinkid(int tutorVideoLinkid) {
        this.tutorVideoLinkid = tutorVideoLinkid;
    }

    private int tutorVideoLinkid;

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    private int tutorId;

    public String getTutorVideoLink() {
        return tutorVideoLink;
    }

    public void setTutorVideoLink(String tutorVideoLink) {
        this.tutorVideoLink = tutorVideoLink;
    }

    private String tutorVideoLink;

}
