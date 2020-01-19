package com.gpa.myappdonation.util;

import android.app.Activity;
import android.widget.EditText;

import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Address;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.gpa.myappdonation.util.Util;
import com.gpa.myappdonation.util.ZipCodeListener;

public class Util {

    private Activity activity;
    private int[] ids;

    public Util(Activity activity, int...ids) {
        this.activity = activity;
        this.ids = ids;
    }


    public void lockFields( boolean isToLock ){
        for( int id : ids ){
            setLockField( id, isToLock );
        }
    }

    private void setLockField( int fieldId, boolean isToLock ){
        activity.findViewById( fieldId ).setEnabled( !isToLock );
    }

    public void setDataViews(Address address){
        setField( R.id.et_street, address.getLogradouro() );
        setField( R.id.et_complement, address.getComplemento() );
        setField( R.id.et_neighbor, address.getBairro() );
        setField( R.id.et_city, address.getLocalidade() );
        setSpinner( R.id.sp_state, R.array.states, address.getUf() );
    }

    private void setField( int id, String data ){
        ((EditText) activity.findViewById(id)).setText( data );
    }

    private void setSpinner( int id, int arrayId, String data ){
        String[] itens = getResources().getStringArray(arrayId);

        for( int i = 0; i < itens.length; i++ ){

            if( itens[i].endsWith( "("+data+")" ) ){
                ((Spinner) activity.findViewById(id)).setSelection( i );
                return;
            }
        }
        ((Spinner) activity.findViewById(id)).setSelection( 0 );
    }


}
