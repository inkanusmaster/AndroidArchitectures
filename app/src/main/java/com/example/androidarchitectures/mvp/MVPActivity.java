package com.example.androidarchitectures.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.androidarchitectures.R;

import java.util.ArrayList;
import java.util.List;

public class MVPActivity extends AppCompatActivity implements CountriesPresenter.View {
    ///////Ale tutaj widać, że VIEW ma silne powiązanie z PRESENTER -> wskazuje na konkretny. (I to też część problemu z MVP)
    private CountriesPresenter countriesPresenterPRESENTER; //Będziemy się kontaktować z PRESENTER

    private List<String> listOfCountries = new ArrayList<>();   //lista z państwami
    private ArrayAdapter<String> arrayAdapter; //arrayadapter do listy. To kojarzysz
    private ListView listView;
    private Button retryButton; //Button do ponownego wczytania krajów
    private ProgressBar progressBar;    //Będzie się kręcić przy wczytywaniu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_v_p);
        setTitle("MVP");

        //W tym przypadku przy tworzeniu obiektu podanie w parametrze this (tak jak w MVC) przekazuje tę aktywność czyli MVPActivity, a PRESENTER oczekuje na View.
        //Zatem ta klasa - MVPActivity musi zaimplementować interfejs (a zatem i jego metody).
        countriesPresenterPRESENTER = new CountriesPresenter(this); //Zatem teraz do PRESENTER przekazujemy interfejs View. NIE PRZEKAZUJEMY aktywności tylko interfejs View.

        retryButton = findViewById(R.id.retryButton);
        progressBar = findViewById(R.id.progressBar);

        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listViewRow, listOfCountries);    //listViewRow to id layoutu row. Nie wiem czemu oba podane. Może być bez tego cjuba
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //implementujemy listener na kliknięcie itemu.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVPActivity.this, "You clicked " + listOfCountries.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ////Implementujemy metody setCountries() i onError() z interfejsu CountriesPresenter.View

    @Override
    public void setCountries(List<String> countries) {
        listOfCountries.clear();
        listOfCountries.addAll(countries);  //addAll dodaje wszystkie wartości. W końcu przekazujemy całą listę countries w parametrze.
        retryButton.setVisibility(View.GONE);   //ukrywamy button
        progressBar.setVisibility(View.GONE);   //ukrywamy kółko
        listView.setVisibility(View.VISIBLE);   //pokazujemy listę
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();    //extract string resource zrobiliśmy. Na error pokazujemy tylko button.
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    //Przycisk retry. Jak wyskoczy błąd to pojawi się button.
    public void retryButton(View view) {
        countriesPresenterPRESENTER.refresh();    //wywołuje metodę refresh z PRESENTER. Pokazujemy tylko progress bar przy kliknięciu Retry
        retryButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    //Tworzymy to po to aby móc uruchamiać aktywności. Intent uruchamia aktywności.
    //Wydaje mi się, że to dlatego tak robimy, ponieważ mamy Aktywności w różnych pakietach.
    public static Intent getIntent(Context context) {
        return new Intent(context, MVPActivity.class);
    }
}