package com.example.androidarchitectures.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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

public class MVVMActivity extends AppCompatActivity {

    private CountriesViewModel countriesViewModel;

    private List<String> listOfCountries = new ArrayList<>();   //lista z państwami
    private ArrayAdapter<String> arrayAdapter; //arrayadapter do listy. To kojarzysz
    private ListView listView;
    private Button retryButton; //Button do ponownego wczytania krajów
    private ProgressBar progressBar;    //Będzie się kręcić przy wczytywaniu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_v_v_m);
        setTitle("MVVM");

        countriesViewModel = ViewModelProviders.of(this).get(CountriesViewModel.class);    //Nie robimy sami obiektu ViewModel. Mamy do tego bibliotekę. Ta linijka po prostu robi nam obiekt ViewModel.

        retryButton = findViewById(R.id.retryButton);
        progressBar = findViewById(R.id.progressBar);

        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listViewRow, listOfCountries);    //listViewRow to id layoutu row. Nie wiem czemu oba podane. Może być bez tego chyba.
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //implementujemy listener na kliknięcie itemu.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVVMActivity.this, "You clicked " + listOfCountries.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        observeViewModel(); //To metoda, która robi
    }

    private void observeViewModel() {   //Będziemy obserwować te 2 zmienne z naszego ViewModel. Wywołujemy 2 metody. Pierwsza do pobrania krajów, druga do błędów. Podobnie jak w poprzednich architekturach.
        countriesViewModel.getCountries().observe(this, countries -> {    //To je lambda... Wewnątrz tego wywołania mamy każdy update zmiennej countries. To czego potrzebujemy tutaj to zaktualizować interfejs
            if (countries != null) {  //country to zmienna której będziemy tu używać.
                listOfCountries.clear();
                listOfCountries.addAll(countries);
                listView.setVisibility(View.VISIBLE);
                arrayAdapter.notifyDataSetChanged();
            } else {
                listView.setVisibility(View.GONE);
            }
        });

        //Tego też obserwujemy. Jak error to toast i button retry, jak nie ma error to chowamy button.
        countriesViewModel.getCountryError().observe(this, error -> {
            progressBar.setVisibility(View.GONE);   //nie chcemy progress baru jak jest error.
            if (error) {
                Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
                retryButton.setVisibility(View.VISIBLE);
            } else {
                retryButton.setVisibility(View.GONE);   //Robimy to bo jak nam się uda pobrać państwa w CountriesViewModel, to robimy countryError.setValue(FALSE).
            }
        });
    }

    //Przycisk retry. Jak wyskoczy błąd to pojawi się button.
    public void retryButton(View view) {
        countriesViewModel.refresh();
        retryButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    //Tworzymy to po to aby móc uruchamiać aktywności. Intent uruchamia aktywności.
    //Wydaje mi się, że to dlatego tak robimy, ponieważ mamy Aktywności w różnych pakietach.
    public static Intent getIntent(Context context) {
        return new Intent(context, MVVMActivity.class);
    }
}