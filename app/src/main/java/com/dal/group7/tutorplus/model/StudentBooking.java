package com.dal.group7.tutorplus.model;

import java.io.Serializable;


public class StudentBooking implements Serializable {
    private int tutorID;
    private int studentID;
    private int bookingID;

    public StudentBooking(){

    }
    public StudentBooking(int tutorID, int studentID, int bookingID){
        this.tutorID = tutorID;
        this.studentID = studentID;
        this.bookingID = bookingID;
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
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

}
