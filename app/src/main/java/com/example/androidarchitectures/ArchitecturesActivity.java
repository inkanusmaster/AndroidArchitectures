package com.example.androidarchitectures;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.androidarchitectures.mvc.MVCActivity;
import com.example.androidarchitectures.mvp.MVPActivity;
import com.example.androidarchitectures.mvvm.MVVMActivity;

public class ArchitecturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_architectures);
    }

    //Wywołujemy metodę getintent z klasy MVCActivity z parametrem this. Takie ciekawe uruchamianie intentów.
    public void onMVC(View view) {
        startActivity(MVCActivity.getIntent(this));
    }

    public void onMVP(View view) {
        startActivity(MVPActivity.getIntent(this));
    }

    public void onMVVM(View view) {
        startActivity(MVVMActivity.getIntent(this));
    }
}