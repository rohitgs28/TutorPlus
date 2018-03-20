package com.dal.group7.tutorplus.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.constants.Constants;
import com.dal.group7.tutorplus.db.DBhelper;
import com.dal.group7.tutorplus.model.TimingAvailablity;
import com.dal.group7.tutorplus.model.LocationModel;
import com.dal.group7.tutorplus.model.ScheduleModel;
import com.dal.group7.tutorplus.model.SubjectModel;
import com.dal.group7.tutorplus.model.TimeScheduleModel;
import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.model.UserModel;
import com.dal.group7.tutorplus.ui.adapters.TutorAdapterTest;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dal.group7.tutorplus.ui.activities.TutorRegistrationActivity.tutorId;

public class StudentBooking_activity extends AppCompatActivity {

    EditText eventDate,eventDescription;
    CaldroidFragment caldroidFragment;
    SimpleDateFormat sdf;
    Spinner s;
    ArrayAdapter<String> adapter;
    List<String> list;
    String text;
    DBhelper dbHelper = new DBhelper(StudentBooking_activity.this);
    Resources res;
    TutorModel tutorModel;
    LocationModel locationModel;

    TimingAvailablity timingAvailablity = new TimingAvailablity();
    Context ctx;
    TutorAdapterTest tutorAdapterTest;
    ListView listView;
    ArrayList<TutorModel> listofTutors;
    Activity activity = new Activity();
    android.app.AlertDialog.Builder editBioDetails;
    ImageView mImageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_GALLERY_CAPTURE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentbooking_layout);
        DBhelper dbhelper = new DBhelper(StudentBooking_activity.this);
        res = getResources();
        loadCalendar();

        Button add = (Button) findViewById(R.id.add_event);
        Button clear = (Button) findViewById(R.id.clear_event);
        eventDate = (EditText) findViewById(R.id.event_date);
        //eventTitle = (EditText) findViewById(R.id.event_title);
        eventDescription = (EditText) findViewById(R.id.event_desription);
        s=(Spinner)findViewById(R.id.spinTimings);
        ArrayList<TimingAvailablity> listofAvailableTutortimings;
        listofAvailableTutortimings = dbhelper.fetchAvailablity(dbHelper.getWritableDatabase(),tutorModel.getTutorId());

        list.add("9:00a.m to 10:00a.m");
        list.add("10:00a.m to 11:00a.m");
        list.add("11:00a.m to 12:00p.m");
        list.add("12:00p.m to 1:00p.m");
        list.add("1:00p.m to 2:00p.m");
        list.add("2:00p.m to 3:00p.m");
        list.add("3:00p.m to 4:00p.m");
        list.add("4:00p.m to 5:00p.m");
        list.add("5:00p.m to 6:00p.m");

        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {

                text = s.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDate.setText("");
                // eventTitle.setText("");
                eventDescription.setText("");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateString = eventDate.getText().toString().trim();
                //String nameString = eventTitle.getText().toString().trim();
                String descString = eventDescription.getText().toString().trim();

                if(dateString.isEmpty() || dateString.length()==0 || dateString.equals("") || dateString == null){
                    // if (nameString.isEmpty() || nameString.length()==0 || nameString.equals("") || nameString == null) {
                    if(descString.isEmpty() || descString.length()==0 || descString.equals("") || descString == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(StudentBooking_activity.this);
                        builder.setMessage("Please fill all the required information");
                        builder.setCancelable(true);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    //  }
                }else{
                    String event_date = eventDate.getText().toString();
                    // String event_title = eventTitle.getText().toString();
                    String event_description = eventDescription.getText().toString();

                    String event_time = s.getSelectedItem().toString();

                    timingAvailablity.setBookingDate(event_date);
                    // objectCalendar.eventname=event_title;
                    timingAvailablity.setBookingDesc(event_description);
                 //   timingAvailablity.setBookingTime(event_time);
                    // sampleInsertions();
                    timingAvailablity.setTutorID(tutorId);
                    ArrayList<TimingAvailablity> bookinglist;
                    int bookingId = dbHelper.insertAvailablity(dbHelper.getWritableDatabase(),timingAvailablity);
                    bookinglist=dbHelper.fetchAvailablity(dbHelper.getWritableDatabase(),tutorId);
                    /*boolean createSuccessful = new CalendarController(getApplicationContext()).create(objectCalendar);

                    if(createSuccessful){
                        Toast.makeText(getApplicationContext(),"Slot Successfully saved", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Unable to save slot", Toast.LENGTH_SHORT).show();
                    }*/
                    // Toast.makeText(Main_Calendar_Activity.this, "Registered Successfully",Toast.LENGTH_SHORT).show();
                }
                compareDate();
                caldroidFragment.refreshView();
            }
        });

        compareDate();

    }

    public void loadCalendar(){

        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();

        args.putInt(CaldroidFragment.MONTH,cal.get(Calendar.MONTH)+1);
        args.putInt(CaldroidFragment.YEAR,cal.get(Calendar.YEAR));

        caldroidFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1,caldroidFragment);
        t.commit();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        final CaldroidListener caldroidListener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {

                eventDate.setText(sdf.format(date));

                if (!caldroidFragment.isSelectedDate(date)) {
                    caldroidFragment.clearBackgroundDrawableForDate(date);
                    caldroidFragment.refreshView();
                }else{

                    ColorDrawable select = new ColorDrawable(getResources().getColor(R.color.caldroid_light_red));
                    caldroidFragment.setBackgroundDrawableForDate(select, date);
                    caldroidFragment.refreshView();
                }

            }
        };

        caldroidFragment.setCaldroidListener(caldroidListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.add:
                Intent i = new Intent(StudentBooking_activity.this,DisplayEventActivity.class);
                startActivity(i);
        }
        return(super.onOptionsItemSelected(item));
    }


    public void compareDate(){
        List<ObjectCalendar> calendar = new CalendarController(this).readEvent();
        if(calendar.size() > 0){

            for(ObjectCalendar obj : calendar){
                String comDate = obj.compareDate;
                try{
                    Date date = sdf.parse(comDate);
                    ColorDrawable back = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
                    caldroidFragment.setBackgroundDrawableForDate(back,date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("Compare Date:---",comDate);
            }
        }
    }
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
                locationModel = (LocationModel) intent.getSerializableExtra("locationModel");
                // take tutor model from previous screens and append the scheduling
                //setDataToTutorModel(tutorModel);
                Toast.makeText(this, "Registered Successfully",Toast.LENGTH_SHORT).show();



                // TODO Delisia screen portion, please check the below commented code
                // TODO sample fetching for tutorId 1 (Delisia)
                // TutorModel tutorModelFetched = new TutorModel();
                // tutorModelFetched = fetchTutorDetails(1); // this is sample tutorId
                // TODO (Delesia) make an intent and pass this tutorModel to another activity and populate all the details in the onCreate method
                // TODO There is no need of ListAdapter in your case , Em commenting a sample code, please check
              /*  Intent intent = new Intent(this, TutorScheduleActivity.class);
                intent.putExtra("tutorModelFetched",tutorModelFetched);
                startActivity(intent);*/


                // TODO Rohit and Nibir , please check the below commented code
                //sampleInsertions();
                //    insertTutor();
                // insert data to db

                Intent goToProfileView = new Intent(this, ScrollingActivity.class);
                goToProfileView.putExtra("tutorModel",tutorModel);
                startActivity(goToProfileView);

                break;
            default:
                break;
        }

    }

    private TutorModel fetchTutorDetails(int tutorId) {
        return dbHelper.fetchTutorDetails(dbHelper.getReadableDatabase(),null,tutorId);
    }

    /** TODO IMPORTANT
     * I am doing all the sample insertions of all the tables as I dont have all the screens
     * Please refer this
     */
    private void sampleInsertions() {
        // user table insertion

    }

/*    private void insertTutor() {
        // TODO fetch userId based on userEmail, which we will get from previous screens (Rohit)
        // fetch subjectId
        int subjectId = dbHelper.getSubjectBasedOnSubjectName(dbHelper.getReadableDatabase(),tutorModel.getSubject().getSubjectName());
        tutorModel.getSubject().setSubjectId(subjectId);
        // TODO first do location insertion copy the above method's code (Nibir) , you will get location id from insertion

        // fetch locationId
        int locationId = dbHelper.getLocationBasedOnLocationName(dbHelper.getReadableDatabase(),tutorModel.getLocationModel().getLocationName());
        tutorModel.getLocationModel().setLocationId(locationId);
        // till now, I have got all the dependencies, now tutor insertion
        tutorId = dbHelper.insertTutor(dbHelper.getWritableDatabase(),tutorModel);
        timingAvailablity.setTutorID(tutorId);
        ArrayList<TimingAvailablity> bookinglist;
        int bookingId = dbHelper.insertBOOKING(dbHelper.getWritableDatabase(),timingAvailablity);
        bookinglist=dbHelper.fecthBookings(dbHelper.getWritableDatabase(),tutorId);
        // set the inserted tutorId in the tutor model

        // Now we have to insert the timings , we got the tutor Id
        // TODO em setting dummy values to Timing model for sample insertion
        // u can delete this aferwards
       // setDummyDataToTimingTable(tutorModel.getTiming());
        //dbHelper.insertTimings(dbHelper.getWritableDatabase(),tutorModel.getTiming(),tutorId);
        Log.d("location insertion","insertions");
    }*/

    // TODO can be deleted Total 3 rows should be inserted for the below sampling code
    // em scheduling it got Mon 9-10 and Tue 9-10,10-11
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

