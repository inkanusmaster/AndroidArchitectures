package com.example.androidarchitectures.mvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidarchitectures.R;

import java.util.ArrayList;
import java.util.List;

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

    private List<String> listOfCountries = new ArrayList<>();   //lista z państwami
    private ArrayAdapter<String> arrayAdapter; //arrayadapter do listy. To kojarzysz
    private ListView listView;

    public void setCountries(List<String> countries) { //metoda dodająca państwa na listę. Czyścimy, dodajemy, updatujemy adapter
        listOfCountries.clear();
        listOfCountries.addAll(countries);  //addAll dodaje wszystkie wartości. W końcu przekazujemy całą listę countries w parametrze.
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_v_c);
        setTitle("MVC");

        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.row_layout, R.id.listViewRow, listOfCountries);    //listViewRow to id layoutu row. Nie wiem czemu oba podane. Może być bez tego cjuba
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //implementujemy listener na kliknięcie itemu.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVCActivity.this, "YOU CLICKED " + listOfCountries.get(i), Toast.LENGTH_SHORT).show();
            }
        });

        //Dodajemy tutaj teraz ręcznie jakieś testowe wartości
        //UWAGA TO JEST TYPOWE DLA APLIKACJI. ALE ZROBIMY TO Z KONTROLEREM LADNIE
        ArrayList<String> tempCountries = new ArrayList<>();
        tempCountries.add("Poland");
        tempCountries.add("England");
        tempCountries.add("USA");
        tempCountries.add("Germany");
        tempCountries.add("Russia");
        tempCountries.add("Slovakia");
        tempCountries.add("Poland");
        tempCountries.add("England");
        tempCountries.add("USA");
        tempCountries.add("Germany");
        tempCountries.add("Russia");
        tempCountries.add("Slovakia");
        tempCountries.add("Poland");
        tempCountries.add("England");
        tempCountries.add("USA");
        tempCountries.add("Germany");
        tempCountries.add("Russia");
        tempCountries.add("Slovakia");
        setCountries(tempCountries); //wywołuemy metodę setCountries i jako parametr podajemy cała listę
    }


    //Tworzymy to po to aby móc uruchamiać aktywności. Intent uruchamia aktywności.
    //Wydaje mi się, że to dlatego tak robimy, ponieważ mamy Aktywności w różnych pakietach.
    public static Intent getIntent(Context context) {
        return new Intent(context, MVCActivity.class);
    }
}