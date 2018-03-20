package com.dal.group7.tutorplus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dal.group7.tutorplus.db.contract.BookingContract;
import com.dal.group7.tutorplus.db.contract.ReviewContract;
import com.dal.group7.tutorplus.db.contract.TimingAvailablityContract;
import com.dal.group7.tutorplus.db.contract.LocationContract;
import com.dal.group7.tutorplus.db.contract.SubjectContract;
import com.dal.group7.tutorplus.db.contract.TimingContract;
import com.dal.group7.tutorplus.db.contract.TutorContract;
import com.dal.group7.tutorplus.db.contract.TutorVideoContract;
import com.dal.group7.tutorplus.db.contract.UserContract;
import com.dal.group7.tutorplus.model.BookingModel;
import com.dal.group7.tutorplus.model.TimingAvailablity;
import com.dal.group7.tutorplus.model.LocationModel;
import com.dal.group7.tutorplus.model.ScheduleModel;
import com.dal.group7.tutorplus.model.SubjectModel;
import com.dal.group7.tutorplus.model.TimeScheduleModel;
import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.model.TutorReviewModel;
import com.dal.group7.tutorplus.model.TutorVideoModel;
import com.dal.group7.tutorplus.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TutorPlus_DB123.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * * Table creation  for table user
     ***/

    private static final String CREATE_QUERY_TBL_USER = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + "("
            + UserContract.UserEntry.USER_ID + " text PRIMARY KEY,"
            + UserContract.UserEntry.USER_NAME + " text,"
            + UserContract.UserEntry.ROLE + " text);";

    /**
     * * Table creation  for timing availablity for Tutor
     ***/
    private static final String CREATE_QUERY_TBL_AVAILABLITY_TABLE = "CREATE TABLE " + TimingAvailablityContract.AvailablityEntry.TABLE_NAME + "("
            + TimingAvailablityContract.AvailablityEntry.AVAILABLITY_ID + " text PRIMARY KEY,"
            + TimingAvailablityContract.AvailablityEntry.TUTOR_ID + " INTEGER,"
            //+ TimingAvailablityContract.AvailablityEntry.TIMING + " text,"
            + TimingAvailablityContract.AvailablityEntry.DATE + " text,"
            + TimingAvailablityContract.AvailablityEntry.EVENT_NAME + " text,"
            + TimingAvailablityContract.AvailablityEntry.EVENT_DESCRIPTION + " text);";


    /**
     * * Table creation  for Booking for Tutor
     ***/
    private static final String CREATE_QUERY_TBL_BOOKING_TABLE = "CREATE TABLE " + BookingContract.BookingEntry.TABLE_NAME + "("
            + BookingContract.BookingEntry.BOOKING_ID + " text PRIMARY KEY,"
            + BookingContract.BookingEntry.TUTOR_ID + " INTEGER,"
            + BookingContract.BookingEntry.TIMING + " text,"
            + BookingContract.BookingEntry.DATE + " text,"
            + BookingContract.BookingEntry.EVENT_NAME + " text,"
            + BookingContract.BookingEntry.STUDENT_USER_NAME + " text,"
            + BookingContract.BookingEntry.EVENT_DESCRIPTION + " text);";

    /**
     * * Table creation  for Booking for Subject
     ***/
    private static final String CREATE_QUERY_TBL_SUBJECT = "CREATE TABLE " + SubjectContract.SubjectEntry.TABLE_NAME + "("
            + SubjectContract.SubjectEntry.SUBJECT_ID + " INTEGER PRIMARY KEY autoincrement,"
            + SubjectContract.SubjectEntry.SUBJECT_NAME + " text);";

    /**
     * * Table creation  for storing Location
     ***/
    private static final String CREATE_QUERY_TBL_LOCATION = "CREATE TABLE " + LocationContract.LocationEntry.TABLE_NAME + "("
            + LocationContract.LocationEntry.LOCATION_ID + " INTEGER PRIMARY KEY autoincrement,"
            + LocationContract.LocationEntry.LOCATION_NAME + " text);";

    /**
     * * Table creation  for tutor
     ***/
    private static final String CREATE_QUERY_TBL_TUTOR = "CREATE TABLE " + TutorContract.TutorEntry.TUTOR_TABLE_NAME + "("
            + TutorContract.TutorEntry.TUTOR_ID + " INTEGER PRIMARY KEY autoincrement,"
            + TutorContract.TutorEntry.TUTOR_NAME + " text,"
            + TutorContract.TutorEntry.AGE + " text,"
            + TutorContract.TutorEntry.LEVEL + " text,"
            + TutorContract.TutorEntry.PHONE + " text,"
            + TutorContract.TutorEntry.QUALIFICATION + " text,"
            + TutorContract.TutorEntry.EXPERIENCE + " text,"
            + TutorContract.TutorEntry.PRICE + " text,"
            + TutorContract.TutorEntry.SUBJECT_ID + " integer,"
            + TutorContract.TutorEntry.USER_ID + " text,"
            + TutorContract.TutorEntry.IMAGE + " text,"
            + TutorContract.TutorEntry.LOCATION_ID + " integer,"
            + " FOREIGN KEY (" + TutorContract.TutorEntry.SUBJECT_ID + ") REFERENCES " + SubjectContract.SubjectEntry.TABLE_NAME + "(" + SubjectContract.SubjectEntry.SUBJECT_ID + ")"
            + " FOREIGN KEY (" + TutorContract.TutorEntry.USER_ID + ") REFERENCES " + UserContract.UserEntry.TABLE_NAME + "(" + UserContract.UserEntry.USER_ID + ")"
            + " FOREIGN KEY (" + TutorContract.TutorEntry.LOCATION_ID + ") REFERENCES " + LocationContract.LocationEntry.TABLE_NAME + "(" + LocationContract.LocationEntry.LOCATION_ID + "));";
    /**
     * * Table creation  for timings
     ***/

    private static final String CREATE_QUERY_TBL_TIMING = "CREATE TABLE " + TimingContract.TimingEntry.TABLE_NAME + "("
            + TimingContract.TimingEntry.TIMING_ID + " INTEGER PRIMARY KEY autoincrement,"
            + TimingContract.TimingEntry.TIMING + " text,"
            + TimingContract.TimingEntry.DAY + " text,"
            //+ TimingContract.TimingEntry.AVAILABILITY + " text,"
            + TimingContract.TimingEntry.TUTOR_ID + " text,"
            + " FOREIGN KEY (" + TimingContract.TimingEntry.TUTOR_ID + ") REFERENCES " + TimingContract.TimingEntry.TABLE_NAME + "(" + TimingContract.TimingEntry.TIMING_ID + "));";

    /**
     * * Method for DBhelper
     ***/
    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("DB Created", "DB created");
    }

    /**
     * * Method for setting foreign key constraints
     ***/
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * * Method for creating the tables
     ***/
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY_TBL_USER);
        Log.d("user db created", "Database done");
        db.execSQL(CREATE_QUERY_TBL_SUBJECT);
        Log.d("subject db created", "Database done");
        db.execSQL(CREATE_QUERY_TBL_LOCATION);
        Log.d("location db created", "Database done");
        db.execSQL(CREATE_QUERY_TBL_TUTOR);
        Log.d("tutor db created", "Database done");
        db.execSQL(CREATE_QUERY_TBL_TIMING);
        Log.d("timing db created", "Database done");
        db.execSQL(CREATE_QUERY_TBL_AVAILABLITY_TABLE);
        Log.d("Booking db created", "Database done");
        /*db.execSQL(CREATE_QUERY_TBL_REVIEW);
		Log.d("Booking db created", "Database done");*/
        db.execSQL(CREATE_QUERY_TBL_BOOKING_TABLE);
        Log.d("Booking db created", "Database done");
    }

    /**
     * * Method for upgarding the SQLite Database from old version to new version
     ***/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * * Method for inserting users
     ***/
    public void insertUser(SQLiteDatabase db, UserModel userModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserEntry.USER_ID, userModel.getUserId());
        contentValues.put(UserContract.UserEntry.USER_NAME, userModel.getName());
        contentValues.put(UserContract.UserEntry.ROLE, userModel.getRole());
        db.insert(UserContract.UserEntry.TABLE_NAME, null, contentValues);
        Log.d("One row inserted", "Database done");
    }

    /**
     * * Method for inserting subjects
     ***/
    public int insertSubject(SQLiteDatabase db, SubjectModel subjectModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SubjectContract.SubjectEntry.SUBJECT_NAME, subjectModel.getSubjectName());
        long subjectId = db.insert(SubjectContract.SubjectEntry.TABLE_NAME, null, contentValues);
        Log.d("One row subject db", "Database done");
        return (int) subjectId;
    }

    /**
     * * Method for inserting location
     ***/
    public void insertLocation(SQLiteDatabase db, LocationModel locationModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocationContract.LocationEntry.LOCATION_NAME, locationModel.getLocationName());
        db.insert(LocationContract.LocationEntry.TABLE_NAME, null, contentValues);
        Log.d("One row location db", "Database done");
    }

    /**
     * * Method for inserting timing availabilty
     ***/
    public int insertAvailablity(SQLiteDatabase db, TimingAvailablity timeAvailablityModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TimingAvailablityContract.AvailablityEntry.TUTOR_ID, timeAvailablityModel.getTutorID());
        contentValues.put(TimingAvailablityContract.AvailablityEntry.DATE, timeAvailablityModel.getBookingDate());
        contentValues.put(TimingAvailablityContract.AvailablityEntry.EVENT_DESCRIPTION, timeAvailablityModel.getBookingDesc());
        //contentValues.put(TimingAvailablityContract.AvailablityEntry.TIMING, timeAvailablityModel.getBookingTime());
        long availablityid = db.insert(TimingAvailablityContract.AvailablityEntry.TABLE_NAME, null, contentValues);
        return (int) availablityid;
    }

    /**
     * * Method for inserting booking slot
     ***/
    public int insertBookingSlot(SQLiteDatabase db, BookingModel bookingModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookingContract.BookingEntry.TUTOR_ID, bookingModel.getTutorID());
        contentValues.put(BookingContract.BookingEntry.DATE, bookingModel.getBookingDate());
        contentValues.put(BookingContract.BookingEntry.EVENT_DESCRIPTION, bookingModel.getBookingDesc());
        contentValues.put(BookingContract.BookingEntry.STUDENT_USER_ID, bookingModel.getBookingTime());
        contentValues.put(BookingContract.BookingEntry.TIMING, bookingModel.getBookingTime());
        long bookingId = db.insert(BookingContract.BookingEntry.TABLE_NAME, null, contentValues);
        return (int) bookingId;
    }

    /**
     * * Method for inserting tutors
     ***/
    public int insertTutor(SQLiteDatabase db, TutorModel tutorModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TutorContract.TutorEntry.TUTOR_NAME, tutorModel.getName());
        contentValues.put(TutorContract.TutorEntry.IMAGE, tutorModel.getImage());
        contentValues.put(TutorContract.TutorEntry.AGE, tutorModel.getAge());
        contentValues.put(TutorContract.TutorEntry.EXPERIENCE, tutorModel.getTutorExperience());
        contentValues.put(TutorContract.TutorEntry.LEVEL, tutorModel.getLevel());
        contentValues.put(TutorContract.TutorEntry.PRICE, tutorModel.getPriceperSession());
        contentValues.put(TutorContract.TutorEntry.PHONE, tutorModel.getPhone());
        contentValues.put(TutorContract.TutorEntry.QUALIFICATION, tutorModel.getQualification());
        if (null != tutorModel.getLocationModel())
            contentValues.put(TutorContract.TutorEntry.LOCATION_ID, tutorModel.getLocationModel().getLocationId());
        if (null != tutorModel.getSubject())
            contentValues.put(TutorContract.TutorEntry.SUBJECT_ID, tutorModel.getSubject().getSubjectId());
        if (null != tutorModel.getUserModel())
            contentValues.put(TutorContract.TutorEntry.USER_ID, tutorModel.getUserModel().getUserId());
        long tutorId = db.insert(TutorContract.TutorEntry.TUTOR_TABLE_NAME, null, contentValues);
        Log.d("Tutor inserted", String.valueOf(tutorId));
        Log.d("Tutor row inserted", String.valueOf(tutorId));
        return (int) tutorId;
    }

    /**
     * * Method for inserting subjects based on subject name
     ***/
    public int getSubjectBasedOnSubjectName(SQLiteDatabase db, String subjectName) {
        String[] projections = {SubjectContract.SubjectEntry.SUBJECT_ID, SubjectContract.SubjectEntry.SUBJECT_NAME};
        Cursor cursor = db.query(SubjectContract.SubjectEntry.TABLE_NAME, projections, SubjectContract.SubjectEntry.SUBJECT_NAME + "=" + "'" + subjectName + "'", null, null, null, null);
        while (cursor.moveToNext()) {
            int subjectId = cursor.getInt(cursor.getColumnIndex(SubjectContract.SubjectEntry.SUBJECT_ID));
            Log.d("subject fetched", String.valueOf(subjectId));
            return subjectId;
        }
        return 0;
    }

    /**
     * * Method for inserting location based on location name
     ***/
    public int getLocationBasedOnLocationName(SQLiteDatabase db, String locationName) {
        String[] projections = {LocationContract.LocationEntry.LOCATION_ID, LocationContract.LocationEntry.LOCATION_NAME};
        Cursor cursor = db.query(LocationContract.LocationEntry.TABLE_NAME, projections, LocationContract.LocationEntry.LOCATION_NAME + "=" + "'" + locationName + "'", null, null, null, null);
        while (cursor.moveToNext()) {
            int locationId = cursor.getInt(cursor.getColumnIndex(LocationContract.LocationEntry.LOCATION_ID));
            Log.d("Location fetched", String.valueOf(locationId));
            return locationId;
        }
        return 0;
    }

    /**
     * * Method for inserting timings
     ***/
    public void insertTimings(SQLiteDatabase db, ScheduleModel timing, int tutorId) {
        for (Map.Entry<String, ArrayList<TimeScheduleModel>> dayTimeMapEntry : timing.getDayTimingMap().entrySet()) {
            for (TimeScheduleModel timeSechedule : dayTimeMapEntry.getValue()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TimingContract.TimingEntry.TUTOR_ID, tutorId);
                contentValues.put(TimingContract.TimingEntry.DAY, dayTimeMapEntry.getKey());
                contentValues.put(TimingContract.TimingEntry.AVAILABILITY, timeSechedule.isAvailability());
                db.insert(TimingContract.TimingEntry.TABLE_NAME, null, contentValues);
            }
        }
    }

    /**
     * * Method for fetching timing availabilty by tutor ID
     ***/
    public ArrayList<TimingAvailablity> fetchAvailablity(SQLiteDatabase db, int tutorid) {
        String BookingSelectorQuery = "select * from tbl_availablity_table avail" +
                " where avail.tutor_id=" + tutorid;
        Cursor cursor = db.rawQuery(BookingSelectorQuery, null);
        ArrayList<TimingAvailablity> bookingModelArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            TimingAvailablity timingAvailablity = new TimingAvailablity();
            timingAvailablity.setBookingID(cursor.getInt(cursor.getColumnIndex(TimingAvailablityContract.AvailablityEntry.AVAILABLITY_ID)));
            timingAvailablity.setBookingDate(cursor.getString(cursor.getColumnIndex(TimingAvailablityContract.AvailablityEntry.DATE)));
            timingAvailablity.setBookingDesc(cursor.getString(cursor.getColumnIndex(TimingAvailablityContract.AvailablityEntry.EVENT_NAME)));
            timingAvailablity.setBookingDesc(cursor.getString(cursor.getColumnIndex(TimingAvailablityContract.AvailablityEntry.EVENT_DESCRIPTION)));
            bookingModelArrayList.add(timingAvailablity);
        }
        return bookingModelArrayList;
    }

    /**
     * * Method for fetching timing availabilty by date
     ***/
    public ArrayList<TimingAvailablity> fetchAvailablitybyDate(SQLiteDatabase db, int tutorid, String date) {
        String BookingSelectorQuery = "select * from tbl_availablity_table avail" +
                " where avail.tutor_id=" + tutorid + " and " + "book.date='" + date + "'";
        Cursor cursor = db.rawQuery(BookingSelectorQuery, null);
        ArrayList<TimingAvailablity> bookingModelArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            TimingAvailablity timingAvailablity = new TimingAvailablity();
            timingAvailablity.setBookingID(cursor.getInt(cursor.getColumnIndex(TimingAvailablityContract.AvailablityEntry.AVAILABLITY_ID)));
            timingAvailablity.setBookingDate(cursor.getString(cursor.getColumnIndex(TimingAvailablityContract.AvailablityEntry.DATE)));
            timingAvailablity.setBookingDesc(cursor.getString(cursor.getColumnIndex(TimingAvailablityContract.AvailablityEntry.EVENT_NAME)));
            bookingModelArrayList.add(timingAvailablity);
        }
        return bookingModelArrayList;
    }

    /**
     * * Method for fetching bookings by tutor ID
     ***/
    public ArrayList<BookingModel> fetchBookingsByID(SQLiteDatabase db, int tutorid) {
        String BookingSelectorQuery = "select * from tbl_booking_table book" +
                " where book.tutor_id=" + tutorid;
        Cursor cursor = db.rawQuery(BookingSelectorQuery, null);
        ArrayList<BookingModel> bookingModelArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            BookingModel bookingModel = new BookingModel();
            bookingModel.setBookingID(cursor.getInt(cursor.getColumnIndex(BookingContract.BookingEntry.BOOKING_ID)));
            bookingModel.setStudentID(cursor.getInt(cursor.getColumnIndex(BookingContract.BookingEntry.STUDENT_USER_ID)));
            bookingModel.setBookingDate(cursor.getString(cursor.getColumnIndex(BookingContract.BookingEntry.DATE)));
            bookingModel.setStudent_user_name(cursor.getString(cursor.getColumnIndex(BookingContract.BookingEntry.STUDENT_USER_NAME)));
            bookingModel.setBookingTime(cursor.getString(cursor.getColumnIndex(BookingContract.BookingEntry.TIMING)));
            bookingModel.setBookingDesc(cursor.getString(cursor.getColumnIndex(BookingContract.BookingEntry.EVENT_NAME)));
            bookingModelArrayList.add(bookingModel);
        }
        return bookingModelArrayList;
    }

    /**
     * * Method for fetching booking by date
     ***/
    public ArrayList<BookingModel> fetchBOOKINGbyDate(SQLiteDatabase db, int tutorid, String date) {
        String BookingSelectorQuery = "select * from tbl_booking_table book" +
                " where book.tutor_id=" + tutorid + " and " + "book.date='" + date + "'";
        Cursor cursor = db.rawQuery(BookingSelectorQuery, null);
        ArrayList<BookingModel> bookingModelArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            BookingModel bookingModel = new BookingModel();
            bookingModel.setStudentID(cursor.getInt(cursor.getColumnIndex(BookingContract.BookingEntry.STUDENT_USER_ID)));
            bookingModel.setBookingDate(cursor.getString(cursor.getColumnIndex(BookingContract.BookingEntry.DATE)));
            bookingModel.setStudent_user_name(cursor.getString(cursor.getColumnIndex(BookingContract.BookingEntry.STUDENT_USER_NAME)));
            bookingModel.setBookingTime(cursor.getString(cursor.getColumnIndex(BookingContract.BookingEntry.TIMING)));
            bookingModel.setBookingDesc(cursor.getString(cursor.getColumnIndex(BookingContract.BookingEntry.EVENT_NAME)));
            bookingModelArrayList.add(bookingModel);
        }
        return bookingModelArrayList;
    }

    /**
     * * Method for fetching subjects
     ***/
    public ArrayList<SubjectModel> fecthSubjects(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select subject_name from tbl_subject", null);
        ArrayList<SubjectModel> subjectModelList = new ArrayList<>();
        while (cursor.moveToNext()) {
            SubjectModel subjectModel = new SubjectModel();
            subjectModel.setSubjectName(cursor.getString(cursor.getColumnIndex(SubjectContract.SubjectEntry.SUBJECT_NAME)));
            subjectModelList.add(subjectModel);
        }
        return subjectModelList;
    }

    /**
     * * Method for fetching a tutor by tutor ID and user ID
     ***/
    public TutorModel fetchTutorDetails(SQLiteDatabase db, String userId, int tutorId) {
        String tutorSelectQuery = null;
        if (null != userId)
            tutorSelectQuery = "SELECT * FROM tbl_tutor tut " +
                    "INNER JOIN tbl_subject sub ON tut.subject_id=sub.subject_id " +
                    "INNER JOIN tbl_location loc ON tut.location_id=loc.location_id " +
                    "INNER JOIN tbl_timings time ON tut.tutor_id=time.tutor_id " +
                    "INNER JOIN tbl_user user ON tut.user_id=user.user_id " +
                    " where tut.user_id=" + userId;
        else
            tutorSelectQuery = "SELECT * FROM tbl_tutor tut " +
                    "INNER JOIN tbl_subject sub ON tut.subject_id=sub.subject_id " +
                    "INNER JOIN tbl_location loc ON tut.location_id=loc.location_id " +
                    "INNER JOIN tbl_timings time ON tut.tutor_id=time.tutor_id " +
                    "INNER JOIN tbl_user user ON tut.user_id=user.user_id " +
                    " where tut.tutor_id=" + tutorId;

        Cursor cursor = db.rawQuery(tutorSelectQuery, null);
        TutorModel tutorModel = new TutorModel();
        SubjectModel subjectModel = new SubjectModel();
        LocationModel locationModel = new LocationModel();
        UserModel userModel = new UserModel();
        Map<String, ArrayList<TimeScheduleModel>> dayTimeMap = new HashMap<>();
        while (cursor.moveToNext()) {
            tutorModel.setTutorId(cursor.getInt(cursor.getColumnIndex(TutorContract.TutorEntry.TUTOR_ID)));
            tutorModel.setName(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.TUTOR_NAME)));
            tutorModel.setAge(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.AGE)));
            tutorModel.setPriceperSession(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.PRICE)));
            tutorModel.setTutorExperience(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.EXPERIENCE)));
            tutorModel.setLevel(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.LEVEL)));
            tutorModel.setQualification(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.QUALIFICATION)));
            tutorModel.setPhone(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.PHONE)));
            subjectModel.setSubjectId(cursor.getInt(cursor.getColumnIndex(SubjectContract.SubjectEntry.SUBJECT_ID)));
            subjectModel.setSubjectName(cursor.getString(cursor.getColumnIndex(SubjectContract.SubjectEntry.SUBJECT_NAME)));
            tutorModel.setSubject(subjectModel);
            locationModel.setLocationId(cursor.getInt(cursor.getColumnIndex(LocationContract.LocationEntry.LOCATION_ID)));
            locationModel.setLocationName(cursor.getString(cursor.getColumnIndex(LocationContract.LocationEntry.LOCATION_NAME)));
            tutorModel.setLocationModel(locationModel);
            userModel.setUserId(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.USER_ID)));
            userModel.setName(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.USER_NAME)));
            userModel.setRole(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.ROLE)));
            tutorModel.setUserModel(userModel);
            String day = cursor.getString(cursor.getColumnIndex(TimingContract.TimingEntry.DAY));
            String time = cursor.getString(cursor.getColumnIndex(TimingContract.TimingEntry.TIMING));
            String availability = cursor.getString(cursor.getColumnIndex(TimingContract.TimingEntry.AVAILABILITY));
            if (dayTimeMap.containsKey(day)) {
                ArrayList<TimeScheduleModel> timeScheduleList = dayTimeMap.get(day);
                TimeScheduleModel timing = new TimeScheduleModel(time, Boolean.parseBoolean(availability));
                timeScheduleList.add(timing);
            } else {
                ArrayList<TimeScheduleModel> timeList = new ArrayList<>();
                TimeScheduleModel timing = new TimeScheduleModel(time, Boolean.parseBoolean(availability));
                timeList.add(timing);
                dayTimeMap.put(day, timeList);
            }
        }
        ScheduleModel scheduleModel = new ScheduleModel(dayTimeMap);
        tutorModel.setTiming(scheduleModel);
        System.out.println(tutorModel);
        return tutorModel;
    }

    /**
     * * Method for tutor details by ID
     ***/
    public TutorModel fetchTutorDetailsbyID(SQLiteDatabase db, int tutorId) {
        String tutorSelectQuery = null;
        tutorSelectQuery = "SELECT * FROM tbl_tutor tut " +
                "INNER JOIN tbl_subject sub ON tut.subject_id=sub.subject_id " +
                "INNER JOIN tbl_user user ON tut.user_id=user.user_id " +
                " where tut.tutor_id=" + tutorId;

        Cursor cursor = db.rawQuery(tutorSelectQuery, null);
        TutorModel tutorModel = new TutorModel();
        SubjectModel subjectModel = new SubjectModel();
        UserModel userModel = new UserModel();
        while (cursor.moveToNext()) {
            tutorModel.setTutorId(cursor.getInt(cursor.getColumnIndex(TutorContract.TutorEntry.TUTOR_ID)));
            tutorModel.setName(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.TUTOR_NAME)));
            tutorModel.setAge(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.AGE)));
            tutorModel.setImage(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.IMAGE)));
            tutorModel.setPriceperSession(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.PRICE)));
            tutorModel.setTutorExperience(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.EXPERIENCE)));
            tutorModel.setLevel(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.LEVEL)));
            tutorModel.setQualification(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.QUALIFICATION)));
            tutorModel.setPhone(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.PHONE)));
            subjectModel.setSubjectId(cursor.getInt(cursor.getColumnIndex(SubjectContract.SubjectEntry.SUBJECT_ID)));
            subjectModel.setSubjectName(cursor.getString(cursor.getColumnIndex(SubjectContract.SubjectEntry.SUBJECT_NAME)));
            userModel.setUserId(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.USER_ID)));
            userModel.setName(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.USER_NAME)));
            userModel.setRole(cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.ROLE)));
            tutorModel.setUserModel(userModel);
            tutorModel.setSubject(subjectModel);


        }
        return tutorModel;
    }

    /**
     * * Method for all the tutors
     ***/
    public ArrayList<TutorModel> fetchListTutorDetails(SQLiteDatabase db) {
        ArrayList<TutorModel> ListofTutors = new ArrayList<TutorModel>();
        String tutorSelectQuery = "SELECT * FROM tbl_tutor tut " +
                "INNER JOIN tbl_subject sub ON tut.subject_id = sub.subject_id ";
        Cursor cursor = db.rawQuery(tutorSelectQuery, null);
        UserModel userModel = new UserModel();
        Map<String, ArrayList<TimeScheduleModel>> dayTimeMap = new HashMap<>();
        while (cursor.moveToNext()) {
            TutorModel tutorModel = new TutorModel();
            SubjectModel subjectModel = new SubjectModel();
            tutorModel.setTutorId(cursor.getInt(cursor.getColumnIndex(TutorContract.TutorEntry.TUTOR_ID)));
            tutorModel.setName(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.TUTOR_NAME)));
            tutorModel.setAge(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.AGE)));
            tutorModel.setPriceperSession(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.PRICE)));
            tutorModel.setTutorExperience(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.EXPERIENCE)));
            tutorModel.setLevel(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.LEVEL)));
            tutorModel.setQualification(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.QUALIFICATION)));
            tutorModel.setPhone(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.PHONE)));
            tutorModel.setImage(cursor.getString(cursor.getColumnIndex(TutorContract.TutorEntry.IMAGE)));
            subjectModel.setSubjectId(cursor.getInt(cursor.getColumnIndex(SubjectContract.SubjectEntry.SUBJECT_ID)));
            subjectModel.setSubjectName(cursor.getString(cursor.getColumnIndex(SubjectContract.SubjectEntry.SUBJECT_NAME)));
            tutorModel.setSubject(subjectModel);
            ListofTutors.add(tutorModel);
        }
        return ListofTutors;
    }


}