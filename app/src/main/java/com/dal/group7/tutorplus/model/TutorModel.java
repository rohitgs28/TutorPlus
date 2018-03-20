package com.dal.group7.tutorplus.model;

import java.io.Serializable;

public class TutorModel implements Serializable {
    private int tutorId;
    private UserModel userModel;
    private String name;
    private String age;
    private String phone;
    private SubjectModel subject;
    private String level;
    private String qualification;
    private String priceperSession;
    private String tutorExperience;
    private ScheduleModel timing;
    private LocationModel locationModel;
    private TimingAvailablity timingAvailablity;


    public TimingAvailablity getBookingModel() {
        return timingAvailablity;
    }

    public void setBookingModel(TimingAvailablity bookingModel) {
        this.timingAvailablity = bookingModel;
    }


    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public SubjectModel getSubject() {
        return subject;
    }

    public void setSubject(SubjectModel subject) {
        this.subject = subject;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPriceperSession() {
        return priceperSession;
    }

    public void setPriceperSession(String priceperSession) {
        this.priceperSession = priceperSession;
    }

    public String getTutorExperience() {
        return tutorExperience;
    }

    public void setTutorExperience(String tutorExperience) {
        this.tutorExperience = tutorExperience;
    }

    public ScheduleModel getTiming() {
        return timing;
    }

    public void setTiming(ScheduleModel timing) {
        this.timing = timing;
    }
}
