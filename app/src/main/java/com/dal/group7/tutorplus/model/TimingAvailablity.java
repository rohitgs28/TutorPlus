package com.dal.group7.tutorplus.model;

import java.io.Serializable;


public class TimingAvailablity implements Serializable {
    private int tutorID;
    private int studentID;
    private int bookingID;
    private String bookingDate;
    private String bookingDesc;

    public TimingAvailablity(){

    }
    public TimingAvailablity(int tutorID, int studentID, int bookingID, String bookingDate, String bookingDesc, String bookingTime) {
        this.tutorID = tutorID;
        this.studentID = studentID;
        this.bookingID = bookingID;
        this.bookingDate = bookingDate;
        this.bookingDesc = bookingDesc;
    }
    public int getTutorID(){
        return tutorID;
    }
    public void setTutorID(int tutorID) {
        this.tutorID = tutorID;
    }

    public int getStudentID(){
        return studentID;
    }
    public void setStudentID(int tutorID) {
        this.studentID = studentID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingDesc(){
        return bookingDesc;
    }
    public void setBookingDesc(String bookingDesc) {
        this.bookingDesc = bookingDesc;
    }

}
