package com.dal.group7.tutorplus.db.contract;


/**
 * Contract Class for Tutor contract
 **/

public final class TutorContract {
    TutorContract(){

    }
    public static abstract class TutorEntry{
        public static final String TUTOR_TABLE_NAME = "tbl_tutor";
        public static final String TUTOR_ID = "tutor_id";
        public static final String USER_ID="user_id"; // foreign key to user table
        public static final String TUTOR_NAME ="tutor_name";
        public static final String AGE="age";
        public static final String SUBJECT_ID="subject_id"; // foreign key to category table
        public static final String PHONE="phone";
        public static final String LEVEL="level";
        public static final String QUALIFICATION="qualification";
        public static final String PRICE="price";
        public static final String EXPERIENCE="experience";
        public static final String IMAGE="image";
        public static final String LOCATION_ID="location_id"; // foreign key to location table (need to ask nibir)



    }
}
