package com.gpa.myappdonation.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;


public class Util {

    private Activity activity;
    private int[] ids;

    public Util(Activity activity, int... ids) {
        this.activity = activity;
        this.ids = ids;
    }


    public void lockFields(boolean isToLock) {
        for (int id : ids) {
            setLockField(id, isToLock);
        }
    }

    private void setLockField(int fieldId, boolean isToLock) {
        activity.findViewById(fieldId).setEnabled(!isToLock);
    }



}
