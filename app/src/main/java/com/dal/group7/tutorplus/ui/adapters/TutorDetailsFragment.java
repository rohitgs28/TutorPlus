package com.dal.group7.tutorplus.ui.adapters;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.TutorModel;
import com.dal.group7.tutorplus.ui.activities.HomePage_Activity;
import com.dal.group7.tutorplus.ui.activities.ScrollingActivity;
import com.dal.group7.tutorplus.ui.activities.TutorReview;
import com.dal.group7.tutorplus.ui.activities.YouTube_Player;
import com.google.android.youtube.player.YouTubePlayer;

public class TutorDetailsFragment extends Fragment implements View.OnClickListener {
    private TutorModel tutorModel;
    private String chosenTask;
    private static final String TUTOR_MODEL_KEY = "tutorModel";
    EditText geturl;
    int pos;
    public static String video_url;
    public static TutorDetailsFragment newInstance(TutorModel tutorModel) {
        TutorDetailsFragment fragment = new TutorDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TUTOR_MODEL_KEY, tutorModel);
        fragment.setArguments(bundle);
        return fragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tutorModel = (TutorModel)getArguments().getSerializable("tutorModel");
        View rootView = inflater.inflate(R.layout.fragment_tutor_view, container, false);

        setTutorDisplayValues(rootView,tutorModel);
        hideAvailabilityPageComponents(rootView);

        if(HomePage_Activity.isStudentLogin){
            Button btnUpload = (Button)rootView.findViewById(R.id.upload);
            EditText txtUpload = (EditText) rootView.findViewById(R.id.addVideo);

            btnUpload.setVisibility(View.GONE);
            txtUpload.setVisibility(View.GONE);
        }else{
            Button btnReview = (Button)rootView.findViewById(R.id.btnWriteReview);
            btnReview.setVisibility(View.GONE);
        }

        ImageView phone_image = (ImageView) rootView.findViewById(R.id.image_call);
        phone_image.setOnClickListener(this);
        ImageView message_image = (ImageView) rootView.findViewById(R.id.image_message);
        message_image.setOnClickListener(this);
        return rootView;
    }
    public void hideAvailabilityPageComponents(View rootView){
        View viewDisplayAvailability = (View)rootView.findViewById(R.id.layoutDisplayAvailability);
        View viewDisplayMessage = (View)rootView.findViewById(R.id.layoutDisplayMessage);
        viewDisplayAvailability.setVisibility(View.GONE);
        viewDisplayMessage.setVisibility(View.GONE);

    }


    private void setTutorDisplayValues(View rootView, TutorModel tutorModel) {
        TextView name = (TextView) rootView.findViewById(R.id.tutor_name_display);
        name.setText(tutorModel.getName());
        TextView age = (TextView) rootView.findViewById(R.id.tutor_age_display);
        age.setText(tutorModel.getAge()+"years");
        age.setText(tutorModel.getAge()+"years");
        TextView qualification = (TextView) rootView.findViewById(R.id.tutor_qualification_display);
        qualification.setText(tutorModel.getQualification());
        TextView subject = (TextView) rootView.findViewById(R.id.tutor_subject_display);
        subject.setText(tutorModel.getSubject().getSubjectName());
        TextView phone = (TextView) rootView.findViewById(R.id.tutor_phone_display);
        phone.setText(tutorModel.getPhone());
        TextView level = (TextView) rootView.findViewById(R.id.tutor_level_display);
        level.setText(tutorModel.getLevel());
        TextView price = (TextView) rootView.findViewById(R.id.tutor_price_display);
        price.setText(tutorModel.getPriceperSession()+"$");
        TextView emailId = (TextView) rootView.findViewById(R.id.tutor_email_id_display);
        emailId.setText(tutorModel.getUserModel().getUserId());
        TextView experience = (TextView) rootView.findViewById(R.id.tutor_experience_display);
        experience.setText(tutorModel.getTutorExperience());

        Button writeReview = (Button) rootView.findViewById(R.id.btnWriteReview);
        Button upload = (Button) rootView.findViewById(R.id.upload);
        geturl = (EditText) rootView.findViewById(R.id.addVideo);

        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , TutorReview.class);
                startActivity(intent);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String video_url_temp = geturl.getText().toString();
                int len = video_url_temp.length();
                if(len!=0) {
                    for (int i = 0; i < video_url_temp.length(); i++) {
                        char c = video_url_temp.charAt(i);
                        if (c == '=') {
                            pos = i;
                            break;
                        }
                    }
                    video_url = video_url_temp.substring((pos + 1), video_url_temp.length());
                    Intent gotovideo = new Intent(getContext(), YouTube_Player.class);
                    startActivity(gotovideo);
                }
                else{
                    Toast.makeText(getContext(),"Enter video link first",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if("call".equalsIgnoreCase(chosenTask))
                call();
            else
                composeSMS();
        } else {
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_call:
                chosenTask = "call";
                checkCallPermission();
                break;
            case R.id.image_message:
                chosenTask = "message";
                checkMessagePermission();
                break;
        }
    }

    public void checkCallPermission() {
        int hasSMSPermission = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE);
        if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                            0);
                }
            }
        } else {
            call();
        }
    }

    public void checkMessagePermission() {
        int hasSMSPermission = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.SEND_SMS);
        if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                            0);
                }
            }
        } else {
            composeSMS();
        }
    }

    private void composeSMS(){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        String phoneNo = tutorModel.getPhone();
        sendIntent.setType("text/plain");
        sendIntent.putExtra("address", phoneNo);
        sendIntent.putExtra("sms_body","I want to connect to you as a student. Please let me know the timings to contact you");
        startActivity(sendIntent);
    }


    private void call() {
        String phoneNo = tutorModel.getPhone();
        Uri number = Uri.parse("tel:"+phoneNo);
        Intent callIntent = new Intent(Intent.ACTION_DIAL,number);
        startActivity(callIntent);
    }
}
