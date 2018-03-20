package com.dal.group7.tutorplus.db.contract;


/**
 * Contract Class for Review
 **/
public class ReviewContract {
    public static abstract class ReviewEntry{
        public static final String Review_ID = "review_id";
        public static final String TUTOR_ID = "tutor_id";
        public static final String TUTOR_REVIEW_RATING ="tutor_review_rating";
        public static final String TUTOR_REVIEW_DESCRIPTION ="tutor_review_description";
        public static final String TABLE_NAME = "tbl_review";
    }
}
