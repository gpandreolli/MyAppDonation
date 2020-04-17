package com.gpa.myappdonation.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.gpa.myappdonation.activity.CadastrarInstituicaoActivity;

public class ZipCodeListener implements TextWatcher {

    private Context context;

    public ZipCodeListener(Context context) {
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();

        if (editable.length() == 8){

            new AddressRequest((CadastrarInstituicaoActivity) context).execute();


        }


    }
}
