package com.dal.group7.tutorplus.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dal.group7.tutorplus.R;
import com.dal.group7.tutorplus.model.UserModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

/**
 ** Class for logging in using google account
 ***/
public class GoogleLogin_Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = GoogleLogin_Activity.class.getSimpleName();
    private static final int RC_SIGN_IN = 102;
    private TextView tvResult;
    private ImageView imageView;
    private GoogleApiClient googleApiClient;
    private SignInButton btnLogin;
    private ImageButton btnSignOut;
    private Button btnFindTutor;
    Button btnRegister;
    TextView tvaccount;
    HomePage_Activity obj=new HomePage_Activity();
    int branch=obj.flag;
    UserModel usermodel;
    static String personEmail;
    static String personName;
    static String role="";
    //TextView tvregcheck;
   // Button btnVIewProf;
    GoogleSignInResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlelogin_layout);
        addControls();
        handleGPlusSettings();
        tvaccount = (TextView) findViewById(R.id.tvaccount);
        //tvregcheck = (TextView) findViewById(R.id.tvregcheck);
      //  btnVIewProf = (Button) findViewById(R.id.btnViewProf);
      /*  btnVIewProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofileview=new Intent(GoogleLogin_Activity.this,ScrollingActivity.class);
                startActivity(gotoprofileview);
            }
        });*/
        tvaccount.setText("Account Login");
        HomePage_Activity ob=new HomePage_Activity();
        int r=ob.flag;
        if(r==1){
            /*
            *  Tutor Role is logged in
            * */
            role="Tutor";
        }
        if(r==2){
             /*
            *  Student Role is logged in
            * */
            role="Student";
        }
        imageView.setImageResource(R.drawable.blankpic);


    }



    private void handleGPlusSettings() {
         /*
          *  Ask user for the Google Sign in options
          * */
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        btnLogin.setSize(SignInButton.SIZE_STANDARD);

    }

    private void addControls() {
         /*
          *  Initialize the controls
          * */
        tvResult = (TextView) findViewById(R.id.tvResult);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnLogin = (SignInButton) findViewById(R.id.btnSignGoogle);
        btnSignOut = (ImageButton) findViewById(R.id.btnSignOut);
        btnFindTutor = (Button) findViewById(R.id.btnFindTutor);
        btnRegister =(Button) findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnFindTutor.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(GoogleLogin_Activity.this, MainTutorListing_Activity.class);
                GoogleLogin_Activity.this.startActivity(myIntent);            }
        });
        btnRegister.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(GoogleLogin_Activity.this, TutorRegistrationActivity.class);
                GoogleLogin_Activity.this.startActivity(myIntent);            }
        });

    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignGoogle:
                signIn();
                break;
            case R.id.btnSignOut:
                AlertDialog alertbox = new AlertDialog.Builder(this)
                        .setMessage("Sign Out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                personEmail=null;
                                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                                        new ResultCallback<Status>() {
                                            @Override
                                            public void onResult(Status status) {
                                                updateUI(false);
                                            }
                                        });
                                Toast.makeText(GoogleLogin_Activity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Log.e(TAG, "Name:  " + personName + ", email: " + personEmail + ", Image: " + personPhoto
                    + ", FamilyName: " + personFamilyName);
            tvResult.setText("Hi "+personName + ",");
            Picasso.with(this).load(personPhoto).into(imageView);

            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    private void updateUI(boolean b) {
        if(branch==2) {
            if (b) {
                btnLogin.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.VISIBLE);
                btnFindTutor.setVisibility(View.VISIBLE);
                tvaccount.setText("");

            } else {
                btnLogin.setVisibility(View.VISIBLE);
                btnSignOut.setVisibility(View.GONE);
                btnFindTutor.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.blankpic);
                tvaccount.setText("Account Login");
                tvResult.setText("");
            }
        }
        if(branch==1) {
            if (b) {
                btnLogin.setVisibility(View.GONE);
                btnSignOut.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
                tvaccount.setText("");
              //  tvregcheck.setVisibility(View.VISIBLE);
               // btnVIewProf.setVisibility(View.VISIBLE);

            } else {
                btnLogin.setVisibility(View.VISIBLE);
                btnSignOut.setVisibility(View.GONE);
                btnRegister.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.blankpic);
                tvaccount.setText("Account Login");
                tvResult.setText("");
                //tvregcheck.setVisibility(View.INVISIBLE);
               // btnVIewProf.setVisibility(View.INVISIBLE);
            }
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {
         /*
          *  Action on navigating back from  screen
          * */
        if (personEmail!=null) {
            AlertDialog alertbox = new AlertDialog.Builder(this)
                    .setMessage("Do you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            personEmail=null;
                            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            updateUI(false);
                                        }
                                    });
                            Toast.makeText(GoogleLogin_Activity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    })
                    .show();
        }
        else{
            finish();
        }
    }
}
