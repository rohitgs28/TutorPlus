package com.dal.group7.tutorplus.model;

public class BookingModel {
    private int tutorID;
    private int studentID;

    public int getStudentUsername() {
        return studentUsername;
    }

    private int studentUsername;
    private int bookingID;
    private String bookingDate;
    private String bookingDesc;
    private String bookingTime;

    public String getStudent_user_name() {
        return student_user_name;
    }

    public void setStudent_user_name(String student_user_name) {
        this.student_user_name = student_user_name;
    }

    private String student_user_name;

    public BookingModel(){

    }
    public BookingModel(int tutorID, int studentID, int bookingID, String bookingDate, String bookingDesc, String bookingTime) {
        this.tutorID = tutorID;
        this.studentID = studentID;
        this.bookingID = bookingID;
        this.bookingDate = bookingDate;
        this.bookingDesc = bookingDesc;
        this.bookingTime = bookingTime;
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

    public String getBookingTime(){
        return bookingTime;
    }
    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

}
