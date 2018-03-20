package com.dal.group7.tutorplus.db.contract;

/**
 * Contract Class for Timing  contract
 **/
public class TimingContract {
    public static abstract class TimingEntry{
        public static final String TIMING_ID = "timing_id";
        public static final String TUTOR_ID="tutor_id"; // foreign key to tutor table
        public static final String TIMING ="timing";
        public static final String DAY="day";
        public static final String AVAILABILITY="availability"; // boolan to show the booking
        public static final String TABLE_NAME="tbl_timings";
    }
}
