package com.dal.group7.tutorplus.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.db.DBhelper;
import com.dal.group7.tutorplus.model.LocationModel;
import com.dal.group7.tutorplus.model.SubjectModel;
import com.dal.group7.tutorplus.model.TutorModel;

import java.util.ArrayList;
/**
 ** Class for displaying tutors for the selected subject
 ***/
public class DisplayTutors extends Activity {
    private static final String TAG = "MainTutorListing_Activity";
    DBhelper dbHelper = new DBhelper(DisplayTutors.this);
    ArrayList<TutorModel> listofTutors = new ArrayList<>();
    ArrayList<TutorModel>listcat=new ArrayList<>();
    ArrayList<TutorModel>listcat1=new ArrayList<>();
    ListView mListView;
    String selectLevel;
    String selectPrice;
    Button button2;
    String message1;
    TutorModel tutorModel;
    LocationModel locationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        mListView = (ListView) findViewById(R.id.listView);
        Spinner spinnerprice;
        Spinner spinnerlevel;
        ArrayAdapter<CharSequence> arrayadapter1;
        ArrayAdapter<CharSequence> arrayadapter2;
        listofTutors = dbHelper.fetchListTutorDetails(dbHelper.getWritableDatabase());

        spinnerprice = (Spinner) findViewById(R.id.spinner1);
        spinnerlevel = (Spinner) findViewById(R.id.spinner2);
        button2 = (Button) findViewById(R.id.button2);

        final tutorListAdapter adapter = new tutorListAdapter(this, R.layout.adaptor_layout, listofTutors);
        Intent intent = getIntent();
        message1 = intent.getStringExtra("message");
        listcat.clear();
        for (int i = 0; i < adapter.getCount(); i++)
        {
            if (adapter.getItem(i).getSubject().getSubjectName().contains(message1)) {
                listcat.add(adapter.getItem(i));
            }

            else {

            }
        }

        final tutorListAdapter adapter1 = new tutorListAdapter(this, R.layout.adaptor_layout, listcat);
        mListView.setAdapter(adapter1);
        arrayadapter1 = ArrayAdapter.createFromResource(this, R.array.level, android.R.layout.simple_spinner_item);
        arrayadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlevel.setAdapter(arrayadapter1);
        /**
         ** Method for fetching level from the spinner
         ***/
        spinnerlevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                selectLevel = parent.getItemAtPosition(position).toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        arrayadapter2 = ArrayAdapter.createFromResource(this, R.array.price, android.R.layout.simple_spinner_item);
        arrayadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerprice.setAdapter(arrayadapter2);

        /**
         ** Method for fetching price from the spinner
         ***/

        spinnerprice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                selectPrice = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        /**
         ** Method for filtering list of tutors using price and level
         ***/
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listcat1.clear();
                for (int i = 0; i < adapter1.getCount(); i++)
                {
                    if((selectPrice.equals(adapter1.getItem(i).getPriceperSession()))&&(selectLevel.equals(adapter1.getItem(i).getLevel()))){
                        listcat1.add(adapter1.getItem(i));
                    }
                    else {
                        Toast.makeText(DisplayTutors.this, " Not Found", Toast.LENGTH_SHORT).show();
                    }
                }

                tutorListAdapter adapter2 = new tutorListAdapter(DisplayTutors.this, R.layout.adaptor_layout, listcat1);
                mListView.setAdapter(adapter2);

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tchr_id = ((TextView) view.findViewById(R.id.storeid)).getText().toString();
                int tutor_id = Integer.parseInt(tchr_id);
                try {
                    tutorModel = new TutorModel();
                    Intent intent = getIntent();
                    tutorModel = dbHelper.fetchTutorDetailsbyID(dbHelper.getWritableDatabase(), tutor_id);
                    Intent goToProfileView = new Intent(DisplayTutors.this, ScrollingActivity.class);
                    goToProfileView.putExtra("tutorModel", tutorModel);
                    startActivity(goToProfileView);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     ** Method to get tutor model
     ***/
    public TutorModel getTutorModel(String id)
    {
        TutorModel tutorModel = new TutorModel();
        Intent intent = getIntent();
        return tutorModel;


    }

}


