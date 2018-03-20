package com.dal.group7.tutorplus.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.db.DBhelper;
import com.dal.group7.tutorplus.model.StudentBooking;
import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.ui.adapters.TutorPlusViewSectionAdapter;

public class StudentScrolling_activity extends AppCompatActivity {
    DBhelper dbHelper = new DBhelper(StudentScrolling_activity.this);
    private TutorPlusViewSectionAdapter tutorPlusViewSectionAdapter;
    private ViewPager mViewPager;
    private TutorModel tutorModel; //= new TutorModel();
    private ImageView tutor_pic_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentactivity_scrolling);
        // TODO get userId or tutorId from previous screen, right now passing dummy value
       // tutorModel = dbHelper.fetchTutorDetails(dbHelper.getReadableDatabase(), null, 1);
        Intent intent = getIntent();
        tutorModel = (TutorModel) intent.getSerializableExtra("tutorModel");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tutorPlusViewSectionAdapter = new TutorPlusViewSectionAdapter(getSupportFragmentManager(), tutorModel);
        tutor_pic_display = (ImageView) findViewById(R.id.tutor_image_display);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(tutorPlusViewSectionAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //showImage();
    }

    private void showImage() {
        String tutorImage = tutorModel.getImage();
        // TODO remove hard coding tutor image
        // tutorImage = "/storage/emulated/0/Download/IMG-20170912-WA0011.jpg";
        // tutorImage = "content://media/external/images/media/96784";
     //   tutor_pic_display.setImageURI(Uri.parse(tutorImage));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tutor_profile_page, menu);
        return true;
    }

    /**TODO : hide call and message button based on the role whether student or tutor , put that condition in if block
     * hide call and message button based on the rolw whether student or tutor
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(true){
            //    menu.findItem(R.id.action_call).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_call:
                tutorPlusViewSectionAdapter.getFragment().checkCallPermission();
                break;
            case R.id.action_message:
                tutorPlusViewSectionAdapter.getFragment().checkMessagePermission();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}