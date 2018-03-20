package com.dal.group7.tutorplus.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.db.DBhelper;
import com.dal.group7.tutorplus.model.TimingAvailablity;
import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.ui.adapters.TutorPlusViewSectionAdapter;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {
    DBhelper dbHelper = new DBhelper(ScrollingActivity.this);
    private TutorPlusViewSectionAdapter tutorPlusViewSectionAdapter;
    private ViewPager mViewPager;
    private TutorModel tutorModel; //= new TutorModel();
    private ImageView tutor_pic_display;
    GoogleLogin_Activity emailcheck = new GoogleLogin_Activity();
    String email = emailcheck.personEmail;
    ArrayList<TimingAvailablity> bookingModelArrayList= new ArrayList<>();
    public static String ava;
    public static String messa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        // TODO get userId or tutorId from previous screen, right now passing dummy value
       // tutorModel = dbHelper.fetchTutorDetails(dbHelper.getReadableDatabase(), null, 1);
        Intent intent = getIntent();
        tutorModel = (TutorModel) intent.getSerializableExtra("tutorModel");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookingModelArrayList=dbHelper.fetchAvailablity(dbHelper.getWritableDatabase(),tutorModel.getTutorId());
        for(int i=0;i<bookingModelArrayList.size();i++){
            ava = bookingModelArrayList.get(i).getBookingDate();
            messa = bookingModelArrayList.get(i).getBookingDesc();
        }
        //Toast.makeText(ScrollingActivity.this, messa, Toast.LENGTH_SHORT).show();
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.caldroid_black));
                }else{
                    toolbar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.caldroid_transparent));
                }
            }
        });


        tutorPlusViewSectionAdapter = new TutorPlusViewSectionAdapter(getSupportFragmentManager(), tutorModel);
        tutor_pic_display = (ImageView) findViewById(R.id.tutor_image_display);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(tutorPlusViewSectionAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        showImage();
    }

    private void showImage() {
        String tutorImage = tutorModel.getImage();
        // TODO remove hard coding tutor image
       // tutorImage = "/storage/emulated/0/Download/IMG-20170912-WA0011.jpg";
        // tutorImage = "content://media/external/images/media/96784";
        tutor_pic_display.setImageURI(Uri.parse(tutorImage));
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

    //Function to set up dialogue box on pressing back key

}