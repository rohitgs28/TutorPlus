package com.dal.group7.tutorplus.ui.activities;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 ** Class for internet connectivity
 ***/
public class ConnectionDetect {

    Context context;

    public ConnectionDetect(Context context) {

        this.context = context;
    }

    /**
     ** Method for internet connectivity
     ***/
    public boolean isconnect() {

        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }
}


