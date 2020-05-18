package com.gpa.myappdonation.util;

import androidx.appcompat.app.AppCompatActivity;

public class App extends AppCompatActivity {

    public static  void fecharApp(AppCompatActivity appCompatActivity){
        appCompatActivity.finish();
    }

    public static void restauraApp(AppCompatActivity appCompatActivity){
        appCompatActivity.recreate();
    }
}
