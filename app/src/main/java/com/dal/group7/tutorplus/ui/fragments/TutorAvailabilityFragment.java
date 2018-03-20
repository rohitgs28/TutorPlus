package com.dal.group7.tutorplus.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.ui.activities.Main_Calendar_Activity;
import com.dal.group7.tutorplus.ui.activities.ScrollingActivity;

import static com.dal.group7.tutorplus.R.id.tutor_message_display;

public class TutorAvailabilityFragment extends Fragment {
    private static final String TUTOR_MODEL_KEY = "tutorModel";
    public static TutorAvailabilityFragment newInstance(TutorModel tutorModel) {
        TutorAvailabilityFragment fragment = new TutorAvailabilityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TUTOR_MODEL_KEY, tutorModel);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tutor_view, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        hideDetailPageComponents(rootView);
        setAvailabilityValues(rootView);

        //Toast.makeText(getContext(), ScrollingActivity.messa, Toast.LENGTH_SHORT).show();
       // textView.setText("Hi I am in Tutor Availability Page");
        Button writeReview = (Button) rootView.findViewById(R.id.btnWriteReview);
        writeReview.setVisibility(View.INVISIBLE);
        return rootView;
    }


    public void setAvailabilityValues(View rootView){
        TextView txtMessage = (TextView)rootView.findViewById(R.id.tutor_message_display) ;
        TextView txtAvailability = (TextView)rootView.findViewById(R.id.tutor_availability_display) ;

        txtMessage.setText(Main_Calendar_Activity.mesa);
        txtAvailability.setText(Main_Calendar_Activity.ava);
    }
    public void hideDetailPageComponents(View rootView){
        View viewDisplayName = (View)rootView.findViewById(R.id.layoutDisplayName);
        View viewDisplayAge = (View)rootView.findViewById(R.id.layoutDisplayAge);
        View viewDisplayQual = (View)rootView.findViewById(R.id.layoutDisplayQualification);
        View viewDisplaySubject = (View)rootView.findViewById(R.id.layoutDisplaySubject);
        View viewDisplayPhone = (View)rootView.findViewById(R.id.layoutDisplayPhone);
        View viewDisplayLvl = (View)rootView.findViewById(R.id.layoutDisplayLevel);
        View viewDisplayPrice = (View)rootView.findViewById(R.id.layoutDisplayPrice);
        View viewDisplayEmail = (View)rootView.findViewById(R.id.layoutDisplayEmail);
        View viewDisplayExperience = (View)rootView.findViewById(R.id.layoutDisplayExperience);
        Button btnWriteReview = (Button)rootView.findViewById(R.id.btnWriteReview);
        Button btnUploadVideo = (Button)rootView.findViewById(R.id.upload);
        EditText txtUploadVideo = (EditText) rootView.findViewById(R.id.addVideo);


        viewDisplayName.setVisibility(View.GONE);
        viewDisplayAge.setVisibility(View.GONE);
        viewDisplayQual.setVisibility(View.GONE);
        viewDisplaySubject.setVisibility(View.GONE);
        viewDisplayPhone.setVisibility(View.GONE);
        viewDisplayLvl.setVisibility(View.GONE);
        viewDisplayPrice.setVisibility(View.GONE);
        viewDisplayEmail.setVisibility(View.GONE);
        viewDisplayExperience.setVisibility(View.GONE);
        btnWriteReview.setVisibility(View.GONE);
        btnUploadVideo.setVisibility(View.GONE);
        txtUploadVideo.setVisibility(View.GONE);

    }

}
