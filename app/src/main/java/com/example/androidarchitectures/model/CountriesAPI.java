package com.example.androidarchitectures.model;

//Biblioteka retrofit będzie tutaj. Interface do retrofita z tego co zrozumiałem

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

//GET z retrofita. A "all" to ENDPOINT ze ścieżki https://restcountries.eu/rest/v2/all
//Robimy observable. Czyli taki emiter eventów
//Ten który tu zrobimy jest specjalny bo emituje pojedynczy jeden event
//Nazywa się po prostu Single. Typ Single
//I zawiera listę obiektów Country.
//Jest metoda getCountries() bez parametru typu Single<List<country>> Ta metoda getCountries będzie do zaimplementowania w klasie implementującej interfejs.
//Możemy też ze strony inne endpointy ogarnąć, to wtedy więcej metod trzeba dodać.

public interface CountriesAPI {
    @GET("all")
    Single<List<Country>> getCountries();
}
