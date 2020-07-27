package com.example.androidarchitectures.mvvm;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidarchitectures.model.CountriesService;
import com.example.androidarchitectures.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesViewModel extends ViewModel { //ViewModel, klasa odpowiedzialna za UI (poczytaj).

    private final MutableLiveData<List<String>> countries = new MutableLiveData<>();    //to countries jest teraz Observable i dostarcza informacje o Countries.
    private final MutableLiveData<Boolean> countryError = new MutableLiveData<>();      //Ta zmienna dostatcza informację o błędzie (onError)

    private CountriesService countriesServiceMODEL;     //A tu będziemy kontaktować się z naszym MODEL.

    public CountriesViewModel() {
        countriesServiceMODEL = new CountriesService();
        fetchCountries();
    }

    public LiveData<List<String>> getCountries() {  //Klasa observable. W przeciwieństwie do innych uwzlgędnia cykl życia. Aktualizuje tylko aktywnych obserwatorów. Nieaktywni obserwatorzy LiveData nie są powiadamiani o zmianach.
        return countries;   //Zwraca listę MutableLiveData<List<String>>
    }

    public LiveData<Boolean> getCountryError() { //Klasa observable. Zwraca błąd. Opis LiveData powyżej.
        return countryError;
    }


    @SuppressLint("CheckResult")
    private void fetchCountries() {  //Wyciągamy z API kraje. Ewidentnie obserwator RXJava.
        countriesServiceMODEL.getCountries() //Wywoułujemy z MODEL metodę getCountries(). Przypominamy typ metody: Single<List<Country>>. Zwróci nam to Single. To jest Observable
                .subscribeOn(Schedulers.newThread())  //Metoda mówi systemowi to musi być uruchomione w wątku tła.
                .observeOn(AndroidSchedulers.mainThread())   //To jest uruchomione w tle, ale mówimy mu: observeOn. To takie 2 standardowe operacje. Wykonujemy je w wątku tła i obserwujemy w głównym.
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {  //Subskrybujemy. Mamy observer List<Country>. To taki jakby callback?

                    @Override
                    public void onSuccess(List<Country> value) {    //Dodajemy w pętli do tymczasowej listy countryNames wartość pola countryName z klasy Country
                        List<String> countryNames = new ArrayList<>();
                        for (Country country : value) {
                            countryNames.add(country.countryName);
                        }
                        countries.setValue(countryNames);    //Gdy otrzymamy informacje, updatujemy countries. Transferujemy countryNames do tych co nasłuchują countries.
                        countryError.setValue(false);   //Jak się udało to ustawiamy false.
                    }

                    @Override
                    public void onError(Throwable e) {  //Obsłużymy błąd. Jak wystąpi to chcemy żeby pojawił się button z napisem Retry
                        countryError.setValue(true);  //Ktokolwiek słucha - mam błąd.
                    }
                });
    }

    public void refresh() {  //Button refresh, który jeszcze raz robi fetchCountries
        fetchCountries();
    }
}
