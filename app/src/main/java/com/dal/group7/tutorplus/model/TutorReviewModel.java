package com.dal.group7.tutorplus.model;

import java.io.Serializable;


public class TutorReviewModel implements Serializable {
    private String tutorRating;
    private String tutorReview;

    private int tutorId;
    private int tutorReviewId;

    private String name;
    private String review;
    private Float rating;

    public TutorReviewModel(){

    }
    public TutorReviewModel(String name,Float rating, String review)
    {
        this.name = name;
        this.review = review;
        this.rating = rating;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {

        this.rating = rating;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public int getTutorReviewId() {
        return tutorReviewId;
    }

    public void setTutorReviewId(int tutorReviewId) {
        this.tutorReviewId = tutorReviewId;
    }

    public String getTutorRating() {
        return tutorRating;
    }

    public void setTutorRating(String tutorRating) {
        this.tutorRating = tutorRating;
    }
    public String getTutorReview() {
        return tutorReview;
    }

    public void setTutorReview(String tutorReview) {
        this.tutorReview = tutorReview;
    }
}
