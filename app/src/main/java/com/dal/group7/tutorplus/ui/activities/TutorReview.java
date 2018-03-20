package com.dal.group7.tutorplus.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.TutorReviewModel;

public class TutorReview extends AppCompatActivity {
    RatingBar ratingBar;
    TextView userSetRatings;
    EditText editTutorReviewTxt;
    Button submitTutorReview;
    Button discardTutorReview;
    TutorReviewModel  tutorReviewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_review);

        init();
    }

    private void init() {
        ratingBar = (RatingBar)findViewById(R.id.tutorRatingBar);
        userSetRatings = (TextView) findViewById(R.id.userSetRatings);
        editTutorReviewTxt = (EditText) findViewById(R.id.tutorReviewEditText);
        submitTutorReview = (Button) findViewById(R.id.submitTutorReview);
        discardTutorReview = (Button) findViewById(R.id.discardTutorReview);

        ratingBar.setOnTouchListener(new View.OnTouchListener() {

            //Reference: https://stackoverflow.com/questions/13535640/capture-ratingbar-click

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = ratingBar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int)starsf + 1;
                    ratingBar.setRating(stars);

                    view.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    view.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    view.setPressed(false);
                }

                onRatingBarClicked(view);


                return true;


            }
        });

        submitTutorReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitClick(view);
            }
        });
        discardTutorReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDiscardClick(view);
            }
        });

    }

    public void onRatingBarClicked(View view){
        String userRating = ratingBar.getRating() + "";
        String ratingSet;
        //Toast.makeText(getApplicationContext(),"You have not rated the tutor", Toast.LENGTH_SHORT ).show();

        //Map Rating to it's meaning
        switch (userRating){

            case "1.0":
                ratingSet = "Hate it";
                break;
            case "2.0":
                ratingSet = "Dislike it";
                break;
            case "3.0":
                ratingSet = "Good";
                break;
            case "4.0":
                ratingSet = "Like it";
                break;
            case "5.0":
                ratingSet = "Love it";
                break;
            default:return;

        }
        userSetRatings.setText(ratingSet);
    }

    public void onSubmitClick(View view){

        Boolean isReviewValid = validateTutorReview();

        if(isReviewValid){
            setDatatoTutorModel();
            Intent myIntent = new Intent(TutorReview.this,
                    ReviewListings.class);
            startActivity(myIntent);
        }else{
            //If no tutor review values are entered
            Toast.makeText(getApplicationContext(),"You have not rated the tutor", Toast.LENGTH_SHORT ).show();
        }


    }

    public void onDiscardClick(View view){
    }

    private void setDatatoTutorModel() {
        tutorReviewModel = new TutorReviewModel("",ratingBar.getRating() ,editTutorReviewTxt.getText().toString());
        tutorReviewModel.setTutorId(2);
        tutorReviewModel.setTutorReviewId(2);
    }

    private boolean validateTutorReview(){
        if(ratingBar.getRating()==0){
            return false;
        }
        return  true;
    }
}
