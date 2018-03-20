package com.dal.group7.tutorplus.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.TutorReviewModel;
import com.dal.group7.tutorplus.ui.adapters.reviewListAdapter;

import java.util.ArrayList;

/**
 ** Class for creating tutor review page
 ***/

public class ReviewListings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_listings);

        ListView mListView = (ListView) findViewById(R.id.reviewListings);

        TutorReviewModel john = new TutorReviewModel("John Mathew", 1.0f, "Level 1");
        TutorReviewModel mat = new TutorReviewModel("Mathew John", 4.0f, "Level 1");
        TutorReviewModel sach = new TutorReviewModel("Luke Andrew", 3.0f, "Level 12");
        TutorReviewModel rak = new TutorReviewModel("Monal Peter", 3.0f, "Level 3");
        TutorReviewModel ro = new TutorReviewModel("Fir Star", 5.0f, "Level 4");
        TutorReviewModel col = new TutorReviewModel("Rang Kil", 1.0f, "Level 1");
        TutorReviewModel kar = new TutorReviewModel("Jhal Shah",4.0f, "Level 2");
        TutorReviewModel kam = new TutorReviewModel("Kam Caroline", 5.0f, "Level 3");

        ArrayList<TutorReviewModel> tutorList = new ArrayList<>();
        tutorList.add(john);
        tutorList.add(mat);
        tutorList.add(sach);
        tutorList.add(rak);
        tutorList.add(ro);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);
        tutorList.add(ro);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);
        tutorList.add(ro);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);
        tutorList.add(ro);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);
        tutorList.add(ro);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);
        tutorList.add(ro);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);
        tutorList.add(ro);
        tutorList.add(col);
        tutorList.add(kar);
        tutorList.add(kam);

        final reviewListAdapter adapter = new reviewListAdapter(this, R.layout.adapter_reviews, tutorList);
        mListView.setAdapter(adapter);
    }
}
