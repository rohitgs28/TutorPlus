package com.dal.group7.tutorplus.ui.activities;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import com.dal.group7.tutorplus.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by shipra malik on 11/9/2017.
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);
    private MainActivity mACtivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginByGoogleActivity.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        mACtivity = rule.getActivity();

    }
    @Test
    public void launchGoogleLoginActitityOnStudentClick(){
        assertNotNull(mACtivity.findViewById(R.id.studentBtn));
        onView(withId(R.id.studentBtn)).perform(click());
        Activity loginByGoogleActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(loginByGoogleActivity);
        assertEquals("student",loginByGoogleActivity.getIntent().getExtras().getString("role"));
        loginByGoogleActivity.finish();
    }
    @Test
    public void launchGoogleLoginActitityOnTutorClick(){
        assertNotNull(mACtivity.findViewById(R.id.tutorBtn));
        onView(withId(R.id.tutorBtn)).perform(click());
        Activity loginByGoogleActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(loginByGoogleActivity);
        assertEquals("tutor",loginByGoogleActivity.getIntent().getExtras().getString("role"));
        loginByGoogleActivity.finish();
    }

    @After
    public void tearDown() throws Exception {
        mACtivity = null;
    }

}