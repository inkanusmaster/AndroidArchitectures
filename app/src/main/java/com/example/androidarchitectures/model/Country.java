package com.example.androidarchitectures.model;

//Ze strony https://restcountries.eu/rest/v2/all pobieramy country
//Ale tylko name
//Będzie lista tych obiektów pobierających name

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("name") //Będziemy pobierać name. Serializacja potrzebna do przesyłania. GSON
    public String countryName; //Do tej zmiennej przypisujemy to co pobrane z name
}
