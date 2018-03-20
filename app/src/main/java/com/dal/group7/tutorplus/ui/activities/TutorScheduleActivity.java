package com.dal.group7.tutorplus.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.constants.Constants;
import com.dal.group7.tutorplus.db.DBhelper;
import com.dal.group7.tutorplus.model.LocationModel;
import com.dal.group7.tutorplus.model.ScheduleModel;
import com.dal.group7.tutorplus.model.SubjectModel;
import com.dal.group7.tutorplus.model.TimeScheduleModel;
import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.model.UserModel;
import com.dal.group7.tutorplus.ui.adapters.TutorAdapter;
import com.dal.group7.tutorplus.ui.adapters.TutorAdapterTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TutorScheduleActivity extends AppCompatActivity {
    DBhelper dbHelper = new DBhelper(TutorScheduleActivity.this);
    Resources res;
    TutorModel tutorModel;
    Context ctx;
    TutorAdapterTest tutorAdapterTest;
    ListView listView;
    Activity activity = new Activity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_schedule);
        res = getResources();
    }


    // This method is setting the Tutor'a availability for Monday, 9-10am
    // Structure is , arraylist of days , contain arraylist of time
    private void setDataToTutorModel(TutorModel tutorModel) {
        // day and set of timings for that day
        Map<String,ArrayList<TimeScheduleModel>> dayTimeMap = new HashMap<>();
        ArrayList<TimeScheduleModel> timeScheduleList = new ArrayList<>();
        // make a schedule model
        ScheduleModel scheduleModel = new ScheduleModel(dayTimeMap);
        // set day and add timings to the day
        TimeScheduleModel timeSchedule = new TimeScheduleModel(Constants.TIMING_9_10AM,true);
        timeScheduleList.add(timeSchedule);
        dayTimeMap.put(Constants.MONDAY,timeScheduleList);
        // put another day and set an arrayList of time for that day
        tutorModel.setTiming(scheduleModel);
    }

    // Insertion into database for Tutor
    public void onSubmitClick(View v) {
        switch (v.getId()) {
            case R.id.submit_tutor:
                Intent intent = getIntent();
                tutorModel = (TutorModel) intent.getSerializableExtra("tutorModel");
                // take tutor model from previous screens and append the scheduling
                setDataToTutorModel(tutorModel);
                Toast.makeText(this, "Registered Successfully",Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(TutorScheduleActivity.this, Main_Calendar_Activity.class);
                TutorScheduleActivity.this.startActivity(myIntent);
                break;
            default:
                break;
        }


    }

    private TutorModel fetchTutorDetails(int tutorId) {
        return dbHelper.fetchTutorDetails(dbHelper.getReadableDatabase(),null,tutorId);
    }

    private void sampleInsertions() {
        // user table insertion
        UserModel userModel = new UserModel("rohit@gmail.com","rohit","student");
        dbHelper.insertUser(dbHelper.getWritableDatabase(),userModel);
        Log.d("user insertion","insertions");
        // subject table insertions
        SubjectModel subjectModel = new SubjectModel(0,tutorModel.getSubject().getSubjectName());
        dbHelper.insertSubject(dbHelper.getWritableDatabase(),subjectModel);
        Log.d("user insertion","insertions");
        // Location Insertion
        LocationModel locationModel = new LocationModel(0,"Canada");
        dbHelper.insertLocation(dbHelper.getWritableDatabase(),locationModel);
        Log.d("location insertion","insertions");
        // Tutor Insertion
        insertTutor();
    }

    private void insertTutor() {

        // fetch subjectId
        int subjectId = dbHelper.getSubjectBasedOnSubjectName(dbHelper.getReadableDatabase(),tutorModel.getSubject().getSubjectName());
        tutorModel.getSubject().setSubjectId(subjectId);
        // fetch locationId
        int locationId = dbHelper.getLocationBasedOnLocationName(dbHelper.getReadableDatabase(),tutorModel.getLocationModel().getLocationName());
        tutorModel.getLocationModel().setLocationId(locationId);
        // till now, I have got all the dependencies, now tutor insertion
        int tutorId = dbHelper.insertTutor(dbHelper.getWritableDatabase(),tutorModel);
        // set the inserted tutorId in the tutor model
        dbHelper.fetchListTutorDetails(dbHelper.getWritableDatabase());
        dbHelper.insertTimings(dbHelper.getWritableDatabase(),tutorModel.getTiming(),tutorId);
        Log.d("location insertion","insertions");
    }
    private void setDummyDataToTimingTable(ScheduleModel timing) {
        Map<String,ArrayList<TimeScheduleModel>> dayTimeMap = new HashMap<>();
        ArrayList<TimeScheduleModel> timeList = new ArrayList<>();
        TimeScheduleModel timeSchedule9_10 = new TimeScheduleModel(Constants.TIMING_9_10AM,true);
        timeList.add(timeSchedule9_10);
        ArrayList<TimeScheduleModel> timeListTue = new ArrayList<>();
        TimeScheduleModel timeSchedule10_11 = new TimeScheduleModel(Constants.TIMING_10_11AM,true);
        timeListTue.add(timeSchedule9_10);
        timeListTue.add(timeSchedule10_11);
        dayTimeMap.put(Constants.MONDAY,timeList);
        dayTimeMap.put(Constants.TUESDAY,timeListTue);
        timing.setDayTimingMap(dayTimeMap);
    }
}
