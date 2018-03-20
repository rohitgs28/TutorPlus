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
 * Created by shipra malik on 11/10/2017.
 */
public class LoginByGoogleActivityTest {
    @Rule
    public ActivityTestRule<LoginByGoogleActivity> rule  = new  ActivityTestRule<>(LoginByGoogleActivity.class);
    private LoginByGoogleActivity loginByGoogleActivity = null;
    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Student_HomeActivity.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        loginByGoogleActivity = rule.getActivity();
    }

// unable to
   /* @Test
    public void launchStudentHomeAfterSuccessfulSignIn(){
        assertNotNull(loginByGoogleActivity.findViewById(R.id.btn_sign_in));
        onView(withId(R.id.btn_sign_in)).perform(click());
        Activity student_HomeActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(student_HomeActivity);
        //assertEquals("student",loginByGoogleActivity.getIntent().getExtras().getString("role"));
        student_HomeActivity.finish();
    }
   */ @After
    public void tearDown() throws Exception {
        loginByGoogleActivity = null;
    }












    // Test connection failure


}