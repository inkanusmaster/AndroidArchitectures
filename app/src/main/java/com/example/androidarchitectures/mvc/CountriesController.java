package com.example.androidarchitectures.mvc;

//Kontroler. Będzie tworzony przez aktywność. Będzie odpytywał API endpoint i zwracał wartości. Będzie również gadał z aktywnością i aktualizował UI
//Czyli to co powinien robić kontroler

import android.annotation.SuppressLint;

import com.example.androidarchitectures.model.CountriesService;
import com.example.androidarchitectures.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesController {

    private MVCActivity mvcActivityVIEW;    //Będziemy tutaj kontaktować się w taki sposób z naszym VIEW (MVCActivity).
    private CountriesService countriesServiceMODEL;     //A tu będziemy kontaktować się z naszym MODEL.

    @SuppressLint("CheckResult")
    private void fetchCountries() {  //Wyciągamy z API kraje. Ewidentnie obserwator RXJava.
        countriesServiceMODEL.getCountries() //Wywoułujemy z MODEL metodę getCountries(). Przypominamy typ metody: Single<List<Country>>. Zwróci nam to Single. To jest Observable
                .subscribeOn(Schedulers.newThread())  //Metoda mówi systemowi to musi być uruchomione w wątku tła.
                .observeOn(AndroidSchedulers.mainThread())   //To jest uruchomione w tle, ale mówimy mu: observeOn. To takie 2 standardowe operacje. Wykonujemy je w wątku tła i obserwujemy w głównym.
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {  //Subskrybujemy. Mamy observer List<Country>

                    @Override
                    public void onSuccess(List<Country> value) {    //Dodajemy w pętli do tymczasowej listy countryNames wartość pola countryName z klasy Country
                        List<String> countryNames = new ArrayList<>();
                        for (Country country : value) {
                            countryNames.add(country.countryName);
                        }
                        mvcActivityVIEW.setCountries(countryNames); //Wywołujemy metodę setCountries naszego VIEW, a tam aktualizacja widoku.
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public CountriesController(MVCActivity mvcActivityVIEW) {   //Konstruktor. Widzimy że przyjmuje VIEW w parametrze przy tworzeniu
        this.mvcActivityVIEW = mvcActivityVIEW;     //Powiązanie VIEW!
        countriesServiceMODEL = new CountriesService();     //Inicjalizacja obiektu MODEL. Przy tworzeniu uruchamiamy jego konstruktor, który przez API gada.
        fetchCountries();   //mamy już linijkę wyżej utworzony obiekt i wywołany konstruktor. Toteż możemy teraz wywołać metodę fetchCountries(), która przez API pobierze swoją metodą getCountries dane.
    }

}
