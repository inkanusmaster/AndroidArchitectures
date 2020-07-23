package com.example.androidarchitectures.mvp;

import android.annotation.SuppressLint;

import com.example.androidarchitectures.model.CountriesService;
import com.example.androidarchitectures.model.Country;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;



public class CountriesPresenter {
    /////////////////Widać tu wyraźnie, że PRESENTER nie ma silnego powiązania z VIEW...////////////
    private View mvpView;    //Prezenter nie wie, do którego VIEW gada. Wiec laczy sie z czymś co implementuje interfejs View.

    private CountriesService countriesServiceMODEL;     //A tu będziemy kontaktować się z naszym MODEL.

    public CountriesPresenter(View mvpView) {   //Konstruktor. Przesyłamy więc View do konstruktora (interfejs)
        this.mvpView = mvpView;     //Powiązanie VIEW! Dzięki czemu w metodzie fetchCountries() przekażemy mu kraje przez wywołanie jej metody setCountries()
        countriesServiceMODEL = new CountriesService();     //Inicjalizacja obiektu MODEL. Przy tworzeniu uruchamiamy jego konstruktor, który przez API gada.
        fetchCountries();   //mamy już linijkę wyżej utworzony obiekt i wywołany konstruktor. Toteż możemy teraz wywołać metodę fetchCountries(), która przez API pobierze swoją metodą getCountries dane.
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
                        mvpView.setCountries(countryNames); //Wywołujemy metodę setCountries naszego VIEW, a tam aktualizacja widoku.
                    }

                    ///////
                    /////// Nie wiemy, która klasa implementuje interfejs View. Wiemy, że metody setCountries() (powyżej) i onError() (poniżej) są dostępne, więc możemy się do nich odwołać.
                    ///////

                    @Override
                    public void onError(Throwable e) {  //Obsłużymy błąd. Jak wystąpi to chcemy żeby pojawił się button z napisem Retry
                        mvpView.onError();    //Metoda error z VIEW
                    }
                });
    }

    public void refresh() {  //Button refresh, który jeszcze raz robi fetchCountries
        fetchCountries();
    }

    //Nie wiemy, z którym VIEW będziemy mieli do czynienia w MVP, więc tworzymy interfejs.
    //Będzie miał dwie metody do implementacji (dodawanie państw z metody onSuccess MVC i onError z MVC).
    //Prezenter mówi: potrzebuję tych 2 metod. Jeśli możesz je wykonać, dogadam się z tobą.
    public interface View {
        void setCountries(List<String> countries);

        void onError();
    }

}
