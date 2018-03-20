package com.dal.group7.tutorplus.db.contract;


/**
 * Contract Class for Timing availablity contract
 **/

public class TimingAvailablityContract {
    public static abstract class AvailablityEntry{
        public static final String AVAILABLITY_ID = "availablity_id";
        public static final String TUTOR_ID ="tutor_id";
        public static final String STUDENT_USER_ID = "student_user_id";
        public static final String DATE = "date";
        public static final String TIMING = "timing";
        public static final String EVENT_NAME = "event_name";
        public static final String EVENT_DESCRIPTION = "event_description";
        public static final String TABLE_NAME = "tbl_availablity_table";
    }

}
