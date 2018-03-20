package com.dal.group7.tutorplus.service;

import android.content.Context;
import android.content.Intent;

import com.dal.group7.tutorplus.ui.activities.Student_HomeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by shipra malik on 11/10/2017.
 */

public class LoginByGoogleService {
    // Create DB instance

    public void handleSignInResult(Context context,GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            // pass three parameters personName, email, role to DB for insertion
            Intent intent = new Intent(context, Student_HomeActivity.class);
            intent.putExtra("name", personName);
            context.startActivity(intent);
        } else {

        }
    }
}
