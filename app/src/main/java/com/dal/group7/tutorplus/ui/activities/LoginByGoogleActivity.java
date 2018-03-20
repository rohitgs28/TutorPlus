package com.dal.group7.tutorplus.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dal.group7.tutorplus.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class LoginByGoogleActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = LoginByGoogleActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private SignInButton btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_google);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    @Override
    public void onClick(View v) {
        signIn();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    // DB test cases
    // 1. check if the corrosponding entry is in the DB, keep him signed in , show him screens according to the role
    // 2. insert userId, userName, role in DB, show him screen according to the role
    // 3. if the insertion is not successful, (primary key violation constraint, show error to user) that use diferent TUTOR_ID for different user
    // 4. student should be displaye student screen
    // 5. tutor should be displayed tutor screen
    // 6.
    // Do these java coding in service which further connects to Database
    /**
     * if the signin is successful,
     * @param result
     */
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            Intent intent = null;
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.e(TAG, "display name: " + acct.getDisplayName());
            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            // pass three parameters personName, email, role to DB for insertion
            Bundle bundle = getIntent().getExtras();
            String role = bundle.getString("role");
            if("tutor".equalsIgnoreCase(role))
                intent = new Intent(this, Tutor_HomeActivity.class);
            else intent = new Intent(this, Student_HomeActivity.class);
            intent.putExtra("name", personName);
            startActivity(intent);
        } else {

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}

