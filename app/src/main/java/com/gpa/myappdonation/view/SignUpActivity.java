package com.gpa.myappdonation.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.gpa.myappdonation.R;
import com.gpa.myappdonation.util.Util;
import com.gpa.myappdonation.util.ZipCodeListener;

public class SignUpActivity extends AppCompatActivity {

    private EditText etZipCode;
    private Util util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_instituicao);

        etZipCode = (EditText) findViewById(R.id.et_zip_code);
        etZipCode.addTextChangedListener(new ZipCodeListener());


        Spinner spStates = (Spinner) findViewById(R.id.sp_state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.states,
                        android.R.layout.simple_spinner_item);
        spStates.setAdapter(adapter);

        util = new Util(this,
                R.id.et_street,
                R.id.tv_zip_code_search,
                R.id.et_complement,
                R.id.et_neighbor,
                R.id.et_city,
                R.id.sp_state,
                R.id.et_number);
    }

    public String getUriZipCode(){

        return "https://viacep.com.br/ws/"+etZipCode.getText()+"/json/";

    }

    public void lockfields(boolean isToLock){
        util.lockFields(isToLock);
    }


}
