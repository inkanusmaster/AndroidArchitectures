package com.example.androidarchitectures.mvvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.androidarchitectures.R;

public class MVVMActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_v_v_m);
        setTitle("MVVM");
    }

    //Tworzymy to po to aby móc uruchamiać aktywności. Intent uruchamia aktywności.
    //Wydaje mi się, że to dlatego tak robimy, ponieważ mamy Aktywności w różnych pakietach.
    public static Intent getIntent(Context context) {
        return new Intent(context, MVVMActivity.class);
    }
}