package com.dal.group7.tutorplus.ui.activities;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dal.group7.tutorplus.R;

import java.util.List;



public class DisplayEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_events);

        readEvents();
    }

    public void readEvents(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutEvents);
        linearLayoutRecords.removeAllViews();

        List<ObjectCalendar> calendars = new CalendarController(this).read();

        if (calendars.size() > 0) {

            for (ObjectCalendar obj : calendars) {

                int id = obj.id;
                String date = obj.date;
                String edescription = obj.eventdescription;

                String textViewContents = date + " - " + " _ " +edescription;

                TextView textViewStudentItem= new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));

                linearLayoutRecords.addView(textViewStudentItem);
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No events yet.");

            linearLayoutRecords.addView(locationItem);
        }
    }
}

