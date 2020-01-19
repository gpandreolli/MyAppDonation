package com.gpa.myappdonation.util;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import com.google.gson.Gson;
import com.gpa.myappdonation.model.Address;
import com.gpa.myappdonation.view.SignUpActivity;

public class AddressRequest extends AsyncTask<Void,Void, Address> {

    private WeakReference<SignUpActivity> activity;

    public AddressRequest(SignUpActivity activity) {
        this.activity = new WeakReference<SignUpActivity>(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activity.get() != null){
            activity.get().lockfields(true);
        }
    }

    @Override
    protected Address doInBackground(Void... voids) {

        try {
            String jsonString = JsonRequest.request(activity.get().getUriZipCode() );
            Gson gson = new Gson();
            return gson.fromJson(jsonString,Address.class);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Address address) {
        super.onPostExecute(address);
        if (activity.get() != null){
            activity.get().lockfields(false);

            if (address != null){

                activity.get().setDataViews(address);

            }
        }
    }
}
