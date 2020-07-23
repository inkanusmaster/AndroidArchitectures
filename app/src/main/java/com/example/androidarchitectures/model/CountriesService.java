package com.example.androidarchitectures.model;

//MODEL. Pobiera informację z backendu
//Ta klasa odpytuje się endpointa i zwraca informację

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//Generalnie chodzi tu o to, że wywołamy URL i zwróci nam listę państw (getCountries z interfejsu CountriesAPI)
//Ta klasa odpytuje endpoint i zwraca listę Country

public class CountriesService {

    public static final String BASE_URL = "https://restcountries.eu/rest/v2/";  //Robimy zmienną BASE_URL zawierającą link, ale bez endpointa
    private CountriesAPI api;   //Prywatna zmienna api typu nasz interfejs CountriesAPI

    public CountriesService() {  //Konstruktor.To wszystko w tym konstruktorze się dzieje!
        Retrofit retrofit = new Retrofit.Builder()  //Robimy Retrofit. Retrofit to taki klient HTTP dla Androida i Javy. Zmienia HTTP api na interfejs Javy
                .baseUrl(BASE_URL)  //Podajemy BASE_URL
                .addConverterFactory(GsonConverterFactory.create()) //To konwertuje JSON ze strony https://restcountries.eu/rest/v2/all do objektu Country. Przyjmuje też parametr z create
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())    //To robi wywoładnie do backendu
                .build();
        api = retrofit.create(CountriesAPI.class);  //To nasze api. Retrofit.create i interfejs podajemy. To "all" z GET chyba się doda na koniec BASE_URL
    }

    //Aby inne obiekty w projekcie mogły odwołać się do tego api, robimy publiczną metodę.
    //Implementujemy metodę getCountries z interfejsu.
    //Metoda zwraca nam api.getCountries
    //Jeśli którakolwiek klasa z naszego projektu odwoła się do metody getCountries, zwróci ona api.getCountries
    public Single<List<Country>> getCountries() {
        return api.getCountries();
    }
}
