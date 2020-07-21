package com.example.androidarchitectures.mvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.androidarchitectures.R;

// MVC - MODEL VIEW CONTROLLER

// VIEW - to widok czyli to co daje nam tutaj MVCActivity. To się robi jako pierwsze przez nasz sytem Android. I utworzy nam kontroler.
// CONTROLLER - jest tworzony przez VIEW. Jeśli VIEW chce wyświetlić jakąś informację, odwołuje się do kontrolera i to kontroler się tym zajmuje.
// Jeśli VIEW chce wyświetlić listę państw, zwraca się do kontrolera o getCountries(). Kontroler zwraca się do modelu.

// MODEL - mamy zaimplementowany. Widać że on zajmuje się zapytaniem i pobraniem listy państw.

// Model odwołuje się do API. MVC jest zatem jako "local", a API jako "remote" (tak na rysunku).
// API zwraca Modelowi listę Country.
// Model zwraca listę kontrolerowi.
// Kontroler zwraca listę do View i mówi masz, wyświetl to.

// Mamy tu widoczną separację pomiędzy widokiem a kontrolerem.
// Jest tak dlatego żebyśmy w tym przypadku mogli testować View osobno.
// Problem jest taki:
// Widać, że mamy bezpośrednie połączenie pomiędzy View a Controller.
// View wie dokładnie z którym obiektem kontrolera jest połączony, a kontroler to samo. Wie, z którym widokiem jest konkretnie połączony.
// Mamy tutaj zatem coś jak relacja jeden do jednego pomiędzy View a Controller.
// Więc jeśli chcemy zmienić View później w projekcie, musimy to zmienić w tym konkretnym obiekcie View. Nie możemy po prostu oddać tego do innej klasy.
// To jest jedna z wad tego rozwiązania.
// Tak czy inaczej, zróbmy to.

public class MVCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_v_c);
        setTitle("MVC");
    }

    //Tworzymy to po to aby móc uruchamiać aktywności. Intent uruchamia aktywności.
    //Wydaje mi się, że to dlatego tak robimy, ponieważ mamy Aktywności w różnych pakietach.
    public static Intent getIntent(Context context) {
        return new Intent(context, MVCActivity.class);
    }
}